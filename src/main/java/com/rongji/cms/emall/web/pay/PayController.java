package com.rongji.cms.emall.web.pay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.order.OrderStatus;
import com.rongji.cms.emall.service.pay.PayInfo;
import com.rongji.cms.emall.service.pay.PayService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.pay.provider.alipay.AlipayMobileSecurityPayProvider;
import com.rongji.cms.emall.service.pay.provider.wxpay.WeixinPayProvider;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.support.barcode.MatrixToImageWriter;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.support.mvc.RequestUtil;
import com.rongji.rjskeleton.web.controller.BaseController;

@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PayService payService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("商家支付方式配置")
	public String index(){
		return "/pay/index";
	}
	
	@RequestMapping("/order/{orderId}")
	@RequestMappingDescription("用户订单支付界面")
	public String payOrder(@PathVariable String orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		List<Payment> payments = paymentService.getWebPaymentsByStore(order.getStore().getId());
//		List<Payment> payments = paymentService.getPaymentsByStore(order.getStore().getId());
		
		model.addAttribute("order", order);
		model.addAttribute("payments", payments);
		
		return "/dsww/002/buyer-pay";
	}
	
	@RequestMapping("/order_mobile/payments")
	@RequestMappingDescription("用户移动端订单支付接口")
	@ResponseBody
	public List<Payment> payMobileOrder(@RequestParam String orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		List<Payment> payments = paymentService.getMobilePaymentsByStore(order.getStore().getId());
		
		return payments;
	}
	
	@RequestMapping("/order_mobile_alipay")
	@RequestMappingDescription("用户移动端订单支付宝支付接口")
	@ResponseBody
	public String payMobileAliOrder(@RequestParam String orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		
		Map<String, String> params = new HashMap<String, String>();
		return payService.doPay(order, new AlipayMobileSecurityPayProvider().getServiceName(), params);
		
	}
	
	@RequestMapping("/order_mobile_weixinpay")
	@RequestMappingDescription("用户移动端订单微信支付接口")
	@ResponseBody
	public String payMobileWeixinOrder(@RequestParam String orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		
		Map<String, String> params = new HashMap<String, String>();
		return payService.doPay(order, new WeixinPayProvider().getServiceName(), params);
		
	}
	
	@RequestMapping("/order_web_weixinpay_scan")
	@RequestMappingDescription("用户WEB端订单微信扫码支付接口")
	public String payWebWeixinScanOrder(@RequestParam String orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		
		Map<String, String> params = new HashMap<String, String>();
		String json = payService.doPay(order, new WeixinPayProvider().getServiceName(), params);
		if (json != null && !json.trim().isEmpty()) {
			Map<String, String> payInfo = new Gson().fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			String codeUrl = payInfo.get("code_url");
			if (codeUrl != null) {
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
				Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				BitMatrix bitMatrix = null;
				try {
					bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 400, 400, hints);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//				ServletOutputStream out = response.getOutputStream();
				try {
					MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				model.addAttribute("imgData", "data:image/png;base64," + Base64.encodeBase64String(baos.toByteArray()));
				model.addAttribute("orderId", order.getId());
				model.addAttribute("order", order);

				return "/dsww/002/buyer-pay-weixin-scan";
			}
		}
		

		return "";
	}
	
	@RequestMapping("/dopay")
	@RequestMappingDescription("用户进行支付")
	@ResponseBody
	public String doPay(@RequestParam String orderId, @RequestParam String payCode,
			Model model) {
		
		Order order = orderService.getOrder(orderId);
		Map<String, String> params = new HashMap<String, String>();
		String ret = payService.doPay(order, payCode, params);
		return ret;
		
	}
	
	@RequestMapping("/dorefund")
	@RequestMappingDescription("卖家进行订单退款")
	@ResponseBody
	public String doRefund(@RequestParam String refundId, @RequestParam String money, 
			@RequestParam(required=false) String remark, Model model) {
		
		Map<String, String> params = new HashMap<String, String>();
		String ret = payService.doRefund(refundId, money, remark, params);
		return ret;
		
	}
	
	
	@RequestMapping("/provider_alipay/refund_notify")
	@RequestMappingDescription("接收支付系统的退款通知, 异步调用")
	@ResponseBody
	public String providerNotifyAlipayRefund(HttpServletRequest request) {
		
		Map<String, String> params = getAlipayParams(request.getParameterMap(), false);
		
		String tradeNo = null;
		long money = 0l;
		
		if ("1".equals(params.get("success_num"))) {
			String ret = params.get("result_details");
			if (ret.indexOf('#') < 0) {
				String[] info = ret.split("\\^"); 
				if (info.length == 3 && "SUCCESS".equals(info[2])) {
					tradeNo = info[0];
					try {
						money = Double.valueOf( Double.valueOf(info[1]) * 100).longValue();
					} catch (Exception e) {
						tradeNo = null;
					}
				}
			}
//		} else if ("0".equals(params.get("success_num"))) { // 已退款的
//			String ret = params.get("result_details");
//			if (ret.indexOf('#') < 0) {
//				String[] info = ret.split("\\^"); 
//				if (info.length == 3 && "TRADE_HAS_CLOSED".equals(info[2])) {
//					tradeNo = info[0];
//					try {
//						money = Double.valueOf( Double.valueOf(info[1]) * 100).longValue();
//					} catch (Exception e) {
//						tradeNo = null;
//					}
//				}
//			}
		}
		
		if (tradeNo != null && !tradeNo.isEmpty()) {
			Order order = orderService.getOrderByTradeNo(tradeNo);
			PayInfo payInfo = payService.verify(order, params);
			if (payInfo != null && payInfo.getRefundId() != null
					&& order.getRefund() != null) {
				orderService.refundSuccess(order.getRefund().getId(), money);
				return "success"; // 支付宝需要返回
			}
		}
		
		return "false"; // 支付宝需要返回
	}

	@RequestMapping("/provider_weixinpay/notify")
	@RequestMappingDescription("接收支付系统的通知, 异步调用")
	@ResponseBody
	public String providerNotifyWeixinPay(@RequestBody String body, HttpServletRequest request) {
		
		if (body == null || body.indexOf("<return_code><![CDATA[SUCCESS]]></return_code>") < 0) { // 判断是否为出错信息请求
			logger.error("微信支付出错（通知信息）：[{}]", body);
			return "";
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("body", body);
		
		int start = body.indexOf("<out_trade_no><![CDATA[");
		int end = body.indexOf("]]></out_trade_no>");
		
		if (start > 0 && end > start) {
			String orderNo = body.substring(start + 23, end);
//			Order order = orderService.getOrder(orderId);
//			PayInfo payInfo = payService.verify(order, params);
//			if (payInfo != null) {
//				if ("SUCCESS".equals(payInfo.getTradeStatus())) {
//					order.setStatus(OrderStatus.PAID);
//					order.setPaymentTime(new Date());
//				}
//				order.setTradeNo(payInfo.getTradeNo());
//				order.setTradeStatus(payInfo.getTradeStatus());
//				orderService.update(order);
//				logger.info("订单[{}]支付通知，状态：[{}]", order.getId(), payInfo.getTradeStatus());
//				
//				return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
//			}
			
			String url = doPayNotify(orderNo, params);
			if (url != null) {
				return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			}
		}
		
		
		return "false"; // 支付宝需要返回
	}
	
	@RequestMapping("/provider_alipay/notify")
	@RequestMappingDescription("接收支付系统的通知, 异步调用")
	@ResponseBody
	public String providerNotifyAlipay(HttpServletRequest request) {
		
		Map<String, String> params = getAlipayParams(request.getParameterMap(), false);
		
		String orderId = params.get("out_trade_no");
//		Order order = orderService.getOrder(orderId);
//		PayInfo payInfo = payService.verify(order, params);
//		if (payInfo != null) {
//			if ("WAIT_SELLER_SEND_GOODS".equals(payInfo.getTradeStatus()) // 纯担保交易接口
//					|| "TRADE_SUCCESS".equals(payInfo.getTradeStatus())) { // 即时到账交易接口
//				order.setStatus(OrderStatus.PAID);
//				order.setPaymentTime(new Date());
//			}
//			order.setTradeStatus(payInfo.getTradeStatus());
//			orderService.update(order);
//			logger.info("订单[{}]支付通知，状态：[{}]", order.getId(), payInfo.getTradeStatus());
//			
//			return "success"; // 支付宝需要返回
//		}
		
		String url = doPayNotify(orderId, params);
		if (url != null) {
			return "success"; // 支付宝需要返回
		}
		
		return "false"; // 支付宝需要返回
	}
	
	@RequestMapping("/provider_alipay/return")
	@RequestMappingDescription("接收支付系统的通知, 同步调用")
	public String providerReturn(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		Map<String, String> params = getAlipayParams(request.getParameterMap(), false);
		
		String orderId = params.get("out_trade_no");
//		Order order = orderService.getOrder(orderId);
//		PayInfo payInfo = payService.verify(order, params);
//		if (payInfo != null) {
//			order.setTradeNo(payInfo.getTradeNo());
//			order.setTradeStatus(payInfo.getTradeStatus());
//			if ("WAIT_SELLER_SEND_GOODS".equals(payInfo.getTradeStatus()) // 纯担保交易接口
//					|| "TRADE_SUCCESS".equals(payInfo.getTradeStatus())) { // 即时到账交易接口
//				order.setStatus(OrderStatus.PAID);
//			}
//			order.setPaymentTime(new Date());
//			orderService.update(order);
//			logger.info("订单[{}]支付成功，状态：[{}]", order.getId(), payInfo.getTradeStatus());
//			
//			redirectAttributes.addFlashAttribute("message", "支付成功");
//			redirectAttributes.addFlashAttribute("paySuccess", true);
//			redirectAttributes.addFlashAttribute("orderUrl", request.getContextPath() + "/buyer/order.htm");
//			return redirectTo("/pay/info");
//		}
		
		String url = doPayNotify(orderId, params);
		if (url != null) {
//			return redirectTo(url);
			return RequestUtil.REDIRECT_URL_PREFIX + url;
		}
		
		throw new EmallException(EmallStatusCode.ERROR_PAY_VERIFY);
	}
	
	@RequestMapping("/check_success")
	@RequestMappingDescription("检查是否支付成功")
	@ResponseBody
	public String checkPaySuccess(@RequestParam String orderId) {
		Order order = orderService.getOne(orderId);
		if (order != null) {
			if (OrderStatus.PAID.equals(order.getStatus())) {
				return "true";
			}
		}
		return "false"; // 模版
	}
	
	@RequestMapping("/info")
	@RequestMappingDescription("支付结果信息，具体支付操作后调用")
	public String payInfo(@RequestParam(required=false) String orderId, Model model) {
		
		String path = "/dsww/002/buyer-pay-info"; // 模版地址
		
		if ( model.containsAttribute("order") ) {
			return path;
		}
		
		if (orderId != null && !orderId.trim().isEmpty()) {
			Order order = orderService.getOrder(orderId);
			if (order != null) {
				if (OrderStatus.NOT_PAID.equals(order.getStatus())) {
					return redirectTo("/pay/order/" + orderId);
					
				} else {
//				if (OrderStatus.PAID.equals(order.getStatus())) {
					model.addAttribute("order", order);
					model.addAttribute("message", "支付成功");
					model.addAttribute("paySuccess", true);
					model.addAttribute("orderUrl", getRequest().getContextPath() + "/buyer/order.htm");
					
					return path;
				}
			}
		}
		
		throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_NOT_FOUND);
	}
	
	private String doPayNotify(String orderNo, Map<String, String> params) {
		Order order = payService.doPayNotifyInfo(orderNo, params);
		if (order != null) {
//			if (redirectAttributes != null) {
//				redirectAttributes.addFlashAttribute("order", order);
//				redirectAttributes.addFlashAttribute("message", "支付成功");
//				redirectAttributes.addFlashAttribute("paySuccess", true);
//				redirectAttributes.addFlashAttribute("orderUrl", getRequest().getContextPath() + "/buyer/order.htm");
//			}
			return "/pay/info.htm?orderId=" + order.getId();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, String> getAlipayParams(Map requestParams, boolean encode) {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			if (values.length == 1) {
				valueStr = values[0];
			} else {
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
			}
			
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			if (encode) {
				try {
					valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			params.put(name, valueStr);
		}
		
		return params;
	}
	
}
