package com.rongji.cms.emall.service.pay.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.service.message.MessageModelSign;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.order.OrderStatus;
import com.rongji.cms.emall.service.pay.PayInfo;
import com.rongji.cms.emall.service.pay.PayService;
import com.rongji.cms.emall.service.pay.PayServiceProviderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.pay.provider.PayException;
import com.rongji.cms.emall.service.pay.provider.PayProvider;
import com.rongji.cms.emall.service.pay.provider.wxpay.WeixinPayProvider;
import com.rongji.cms.emall.service.refund.RefundService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;
import com.rongji.cms.emall.vo.Refund;
import com.rongji.cms.emall.vo.User;

@Service
public class PayServiceImpl implements PayService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
	
	@Autowired
	private PayServiceProviderService payServiceProviderService;
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RefundService refundService;
	
	@Autowired
	private OrderGoodsService orderGoodsService;
	
	@Autowired
	private MessageModelService messageModelService;
	
	@Autowired
	private UserService userService;


	@Override
	public String doPay(Order order, String payCode, Map<String, String> params) {
		
		order.setPayCode(payCode);
		orderService.update(order);
		
		Payment payment = getPaymentByOrder(order);
		PayProvider provider = getPayProvider(order);
		
//		long p = 0L;
//		if (order.getGoods() != null) {
//			for (OrderGoods goods : order.getGoods()) {
//				p += goods.getPurchaseQuantity();
//			}
//		}
//		order.setPrice(p); // FIXME 测试环境使用1分钱进行支付测试
//		order.setLogisticsFee(0L); // FIXME 测试环境使用1分钱进行支付测试 
		
		return provider.doPay(payment, order, params);
	}

	@Transactional
	@Override
	public String doRefund(String refundId, String money, String remark, Map<String, String> params) {
		
		Refund refund = refundService.getOne(refundId);
		Order order = orderService.getOrder(refund.getOrder().getId());
		Payment payment = getPaymentByOrder(order);
		PayProvider provider = getPayProvider(order);

		long m = 0;
		try {
			m = Double.valueOf(Double.valueOf(money) * 100).longValue();
		} catch (Exception e) {
			throw new PayException( "退款编号["+ refundId + "]退款金额错误：" + money);
		}
//		m = 1;
		
		if (m > order.getPrice()) {
			throw new PayException( "退款["+ refundId + "]中的退款金额["+ m +"]超过最大值["+ order.getPrice() +"]");
		}
		
		String ret = provider.doRefund(payment, order, m, remark == null || remark.trim().isEmpty() ? "协商退款" : remark, params);
		if (provider instanceof WeixinPayProvider && !"FAIL".equals(ret)) { // 微信支付配置
			orderService.refundSuccess(refundId, m);
		}
		return ret;
	}

	@Override
	public PayInfo verify(Order order, Map<String, String> params) {
		Payment payment = getPaymentByOrder(order);
		PayProvider provider = getPayProvider(order);
		return provider.verify(payment, params);
	}

	@Override
	public Order doPayNotifyInfo(String orderNo, Map<String, String> params) {
		
//		Order order = orderService.getOrder(orderNo);
		Order order = orderService.getOrderByNo(orderNo);
		if (order == null) {
			order = orderService.getOrder(orderNo);
		}
		
		Payment payment = getPaymentByOrder(order);
		PayProvider provider = getPayProvider(order);
		PayInfo payInfo = provider.verify(payment, params);
		if (payInfo != null) {
			if ("WAIT_SELLER_SEND_GOODS".equals(payInfo.getTradeStatus()) // 支付宝 纯担保交易接口
					|| "TRADE_SUCCESS".equals(payInfo.getTradeStatus()) // 支付宝 即时到账交易接口
					|| "SUCCESS".equals(payInfo.getTradeStatus())) { // 微信支付
				if ( OrderStatus.NOT_PAID.equals(order.getStatus()) ) {
					order.setStatus(OrderStatus.PAID);
					order.setPaymentTime(new Date());
					
					// 发送消息
					Map<String, String> data = new HashMap<String, String>();
					data.put("order.sellerName", order.getStore().getUser().getNickName());
					data.put("order.orderNo", order.getOrderNo());
					User user = userService.getUserById(order.getCreateUser());
					data.put("order.buyerName", user.getNickName());
					try {
						messageModelService.sendMessge(MessageModelSign.BUYER_ORDER_PAID, order.getStore().getUser().getId(), data, order.getCreateUser());
					} catch (Exception e) {
						logger.info("消息["+ MessageModelSign.BUYER_ORDER_PAID +"]发送失败", e);
					}
				}
			}
			order.setTradeNo(payInfo.getTradeNo());
			order.setTradeStatus(payInfo.getTradeStatus());
			logger.info("订单[{}]支付通知，状态：[{}]", order.getOrderNo(), payInfo.getTradeStatus());
			return orderService.update(order);
		}

		return null;
	}

	private void checkOrder(Order order) {
		if (order == null) {
			logger.error("订单不能为空");
			throw new EmallException(EmallStatusCode.ERROR_PAY_CREATE);
		}

		if (order.getStore() == null) {
			logger.error("订单[{}]商家不能为空", order.getOrderNo());
			throw new EmallException(EmallStatusCode.ERROR_PAY_CREATE);
		}
		
		if (order.getGoods() == null || order.getGoods().isEmpty()) {
			logger.error("订单[{}]中不存在商品", order.getOrderNo());
			throw new EmallException(EmallStatusCode.ERROR_PAY_CREATE);
		}
	}
	
	private PayProvider getPayProvider(Order order) {
		PayProvider provider = payServiceProviderService.getProvider(order.getPayCode());
		if (provider == null) {
			logger.error("订单[{}]的支付提供商不存在[{}]", order.getOrderNo(), order.getPayCode());
			throw new EmallException(EmallStatusCode.ERROR_PAY_CREATE);
		}
		return provider;
	}
	
	private Payment getPaymentByOrder(Order order) {
		checkOrder(order);
		Payment payment = paymentService.getByOrder(order);
		if (payment == null) {
			logger.error("商店[{}]订单[{}]的支付方式[{}]不存在", order.getStore().getId(), order.getOrderNo(), order.getPayCode());
			throw new EmallException(EmallStatusCode.ERROR_PAY_CREATE);
		}
		return payment;
	}

}
