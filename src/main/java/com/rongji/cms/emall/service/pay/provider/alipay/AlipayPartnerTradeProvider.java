package com.rongji.cms.emall.service.pay.provider.alipay;

import java.util.HashMap;
import java.util.Map;

import com.alipay.util.AlipaySubmit;
import com.rongji.cms.emall.service.pay.provider.PayException;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;

/**
 * 支付宝纯担保交易接口
 *
 * @since 2015-6-26
 * @author rjf
 *
 */
public class AlipayPartnerTradeProvider extends AlipayProvider {
	
	@Override
	protected String getService() {
		return "create_partner_trade_by_buyer";
	}

	@Override
	public String getDescription() {
		return "淘宝买家最熟悉的付款方式：买家先将交易资金存入支付宝并通知卖家发货，买家确认收货后资金自动进入卖家支付宝账户，完成交易。";
	}

	@Override
	public String getTitle() {
		return "支付宝[纯担保交易]";
	}

	@Override
	public String doPay(Payment payment, Order order, Map<String, String> params) throws PayException {
		
		if (order.getAddress() == null) {
			throw new PayException("用户地址为空");
		}
		
		AlipayConfig config = getConfig(payment);
		
		String url = EmallConfig.getEmallUrl();
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", getService());
        sParaTemp.put("partner", config.getPartner());
        sParaTemp.put("seller_email", config.getSellerEmail());
        sParaTemp.put("_input_charset", "utf-8");
//        sParaTemp.put("_input_charset", RjskeletonConfig.getCharset());
		sParaTemp.put("payment_type", "1");
		sParaTemp.put("notify_url", url+"/pay/provider_alipay/notify.htm");
		sParaTemp.put("return_url", url+"/pay/provider_alipay/return.htm");
		sParaTemp.put("out_trade_no", order.getOrderNo());
		sParaTemp.put("subject", order.getOrderTitle());
		sParaTemp.put("price", order.getPriceString());
		sParaTemp.put("quantity", "1"); // 必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
		sParaTemp.put("logistics_fee", order.getLogisticsFeeString()); // 必填，即运费
//		sParaTemp.put("logistics_fee", "0.00"); // 必填，即运费
		sParaTemp.put("logistics_type", "EXPRESS"); // 必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
		sParaTemp.put("logistics_payment", "SELLER_PAY"); // 必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
		sParaTemp.put("body", order.getOrderBody()); // 订单描述
		sParaTemp.put("show_url", order.getOrderUrl()); // 商品展示地址
		sParaTemp.put("receive_name", order.getAddress().getReceiver()); //收货人姓名
		sParaTemp.put("receive_address", order.getAddress().getAreas() + order.getAddress().getAddress()); //收货人地址
		sParaTemp.put("receive_zip", order.getAddress().getZipcode()); //收货人邮编
		if (order.getAddress().getPhone() != null) {
			sParaTemp.put("receive_phone", order.getAddress().getPhone()); //收货人电话号码 如：0571-88158090
		}
		sParaTemp.put("receive_mobile", order.getAddress().getMobiletel());//收货人手机号码 如：13312341234
		
		String htm = AlipaySubmit.buildRequest(sParaTemp,"get","确认", config.getKey());
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head>"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");
		sb.append(htm);
		sb.append("</body></html>");
		return sb.toString();
	}


}
