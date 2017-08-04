package com.rongji.cms.emall.service.pay.provider.wxpay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rongji.cms.emall.service.pay.PayInfo;
import com.rongji.cms.emall.service.pay.provider.PayException;
import com.rongji.cms.emall.service.pay.provider.PayProvider;
import com.rongji.cms.emall.service.pay.provider.PayUtil;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;
import com.tencent.common.HttpsRequest;
import com.tencent.common.Signature;
import com.tencent.common.Util;

/**
 * 统一下单 https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_1
 * 申请退款 https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_4&index=6
 *
 * @since 2015-7-16
 * @author rjf
 *
 */
public class WeixinPayProvider implements PayProvider {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	private String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	@Override
	public String getServiceName() {
		return "weixinpay_micropay";
	}

	@Override
	public String getDescription() {
		return "用户扫描商户展示在各种场景的二维码进行支付";
	}

	@Override
	public String getTitle() {
		return "微信支付[扫码支付]";
	}

	@Override
	public String doPay(Payment payment, Order order, Map<String, String> params) {
		
		WeixinPayConfig config = getConfig(payment);
		
//		Map<String, Object> map = new HashMap<String, Object>();
		
		String url = EmallConfig.getEmallUrl();
		
		WeixinUnifiedOrderReq unifiedOrder = new WeixinUnifiedOrderReq();
		
//		map.put("appid", config.getAppId());
//		map.put("mch_id", config.getMchId());
//		map.put("trade_type", "APP"); // JSAPI NATIVE
		unifiedOrder.setAppid(config.getAppId());
		unifiedOrder.setMch_id(config.getMchId());
		unifiedOrder.setTrade_type("NATIVE");
		
//		map.put("fee_type", "CNY");
//		map.put("out_trade_no", order.getOrderNo());
//		map.put("body", order.getOrderBody());
//		map.put("total_fee", String.valueOf(order.getPrice()));
		unifiedOrder.setOut_trade_no(order.getOrderNo());
		unifiedOrder.setBody(order.getOrderBody());
		unifiedOrder.setTotal_fee(order.getPrice().intValue());
		
//		map.put("spbill_create_ip", "218.5.2.35"); // FIXME 需要时正式地址
//		map.put("time_start", sdf.format(new Date()));
//		map.put("notify_url", url+"/pay/provider_weixinpay/notify.htm");
		unifiedOrder.setSpbill_create_ip("218.5.2.248");
		unifiedOrder.setTime_start(sdf.format(new Date()));
		unifiedOrder.setNotify_url(url+"/pay/provider_weixinpay/notify.htm");
		
//		map.put("nonce_str", getNoncestr());
		unifiedOrder.setNonce_str(getNoncestr());
		
		// sign
//		String sign = Signature.getSignByMap(map, config.getKey());
//		map.put("sign", sign);
		unifiedOrder.setSign(getSign(unifiedOrder, config.getKey()));
		
		String result = sendPost(unifiedorderUrl, config, unifiedOrder);
		WeixinUnifiedOrderRes res = (WeixinUnifiedOrderRes)Util.getObjectFromXML(result, WeixinUnifiedOrderRes.class);
		
		if ( ! "SUCCESS".equals(res.getReturn_code()) ) {
			throw new PayException("提交微信支付请求的数据出错，订单号[" + order.getOrderNo() + "]，状态码["+ res.getReturn_code() +"]" + "，错误信息["+ res.getReturn_msg() +"]");
		}
		
		if ( ! "SUCCESS".equals(res.getResult_code()) ) {
			Map<String, Object> pa = getMapFromXml(result);
			throw new PayException("提交微信支付请求的业务处理出错，订单号[" + order.getOrderNo() + "]，错误代码["+ pa.get("err_code") +"]" + "，错误信息["+ pa.get("err_code_des") +"]");
		}
		
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("appid", res.getAppid());
		ret.put("noncestr", res.getNonce_str());
		ret.put("package", "Sign=WXPay");
		ret.put("partnerid", res.getMch_id());
		ret.put("prepayid", res.getPrepay_id());
		ret.put("timestamp", String.valueOf(System.currentTimeMillis()));
		ret.put("sign", res.getSign());
		ret.put("code_url", res.getCode_url());
		ret.put("retcode", "0");
		ret.put("retmsg", "ok");
		
		return new Gson().toJson(ret);
	}

	@Override
	public PayInfo verify(Payment payment, Map<String, String> params) {
		
		String body = params.get("body");
		WeixinUnifiedOrderNotify notify = (WeixinUnifiedOrderNotify)Util.getObjectFromXML(body, WeixinUnifiedOrderNotify.class);
		
//		SAXReader reader = new SAXReader();
//	    Document doc = null;
//		try {
//			doc = reader.read(new ByteArrayInputStream(body.getBytes("utf-8")));
//		} catch (UnsupportedEncodingException e) {
//			throw new PayException("微信支付返回内容，编码格式不正确："+body, e);
//		} catch (DocumentException e) {
//			throw new PayException("微信支付返回内容，XML格式错误："+body, e);
//		}
//
//	    @SuppressWarnings("unchecked")
//	    List<Node> nodeList = doc.selectNodes("//xml/*");
//	    Map<String, Object> pa = new HashMap<String, Object>(nodeList.size());
//	    for (Node node : nodeList) {
//			pa.put(node.getName(), node.getText());
//		}

		Map<String, Object> pa = getMapFromXml(body);
		
		String sign = notify.getSign();
		
		WeixinPayConfig config = getConfig(payment);
		pa.put("sign", "");
//		String newSign = getSign(pa, config.getKey());
		String newSign = Signature.getSignByMap(pa, config.getKey());
		
		if (newSign.equals(sign)) {
			PayInfo payInfo = new PayInfo();
			payInfo.setOrderId(notify.getOut_trade_no());
			payInfo.setTradeNo(notify.getTransaction_id());
			payInfo.setTradeStatus(notify.getResult_code());
			
			return payInfo;
		}
		
		return null;
	}

	@Override
	public String convertConfig(Payment payment) {
		
		WeixinPayConfig config = getConfig(payment);
		
		byte[] cert = PayUtil.getCertByte(payment);
		if (cert != null && cert.length > 0) {
			config.setCert(cert);
		}
		
		return new Gson().toJson(config);
	}

	@Override
	public boolean isMobile() {
		return false;
	}

	@Override
	public String doRefund(Payment payment, Order order, long money,
			String remark, Map<String, String> params) throws PayException {
		
		WeixinPayConfig config = getConfig(payment);
		
		WeixinOrderRefundReq refundReq = new WeixinOrderRefundReq();
		refundReq.setAppid(config.getAppId());
		refundReq.setMch_id(config.getMchId());
		refundReq.setNonce_str(getNoncestr());
		refundReq.setOp_user_id(config.getMchId()); // 操作员帐号, 默认为商户号
//		refundReq.setOut_refund_no(String.valueOf(System.currentTimeMillis())); // 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		refundReq.setOut_refund_no(order.getRefund().getId()); // 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		refundReq.setOut_trade_no(order.getOrderNo());
		refundReq.setRefund_fee(Long.valueOf(money).intValue());
		refundReq.setTotal_fee(refundReq.getRefund_fee()); // 测试金额
//		refundReq.setTotal_fee(order.getPrice().intValue());
		refundReq.setTransaction_id(order.getTradeNo());
		
		refundReq.setSign(getSign(refundReq, config.getKey()));
		
		String result = sendPost(refundUrl, config, refundReq);
		WeixinOrderRefundRes res = (WeixinOrderRefundRes)Util.getObjectFromXML(result, WeixinOrderRefundRes.class);
		
		Map<String, Object> pa = getMapFromXml(result);
		
		String sign = res.getSign();
		
		pa.put("sign", "");
		String newSign = Signature.getSignByMap(pa, config.getKey());
		if (newSign.equals(sign)) {
			
			if ( ! "SUCCESS".equals(res.getReturn_code()) ) {
				throw new PayException("提交微信支付请求的数据出错，订单号[" + order.getOrderNo() + "]，状态码["+ res.getReturn_code() +"]" + "，错误信息["+ res.getReturn_msg() +"]");
			}
			
			if ( ! "SUCCESS".equals(res.getResult_code()) ) {
				throw new PayException("提交微信退款申请请求的业务处理出错，订单号[" + order.getOrderNo() + "]，错误代码["+ pa.get("err_code") +"]" + "，错误信息["+ pa.get("err_code_des") +"]");
			}
			
			String url = EmallConfig.getEmallUrl();
			return "<html><head><script>location.href=\""+ url +"/buyer/refund/details.htm?orderId="+ order.getId() +"\";</script></head><body></body></html>";
		}
		
		return "FAIL";
	}

	protected String sendPost(String url, WeixinPayConfig config, Object data) {
		try {
			HttpsRequest httpsRequest = new HttpsRequest(config.getCert(), config.getCertPassword());
			return httpsRequest.sendPost(url, data);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected WeixinPayConfig getConfig(Payment payment) {
		try {
			return new Gson().fromJson(payment.getConfig(), WeixinPayConfig.class);
		} catch (JsonSyntaxException e) {
			throw new PayException("微信支付配置信息格式不正确", e);
		}
	}
	
	protected String getNoncestr() {
		int len = 32;
		char[] nc = new char[len];
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			int index = random.nextInt(chars.length);
			nc[i] = chars[index];
		}
		return new String(nc);
	}

	
	protected String getSign(Object o, String key) {
		try {
			return Signature.getSign(o, key);
		} catch (IllegalAccessException e) {
			throw new PayException("微信支付配置信息签名生成失败", e);
		}
	}
	
	protected Map<String, Object> getMapFromXml(String xml) {
		SAXReader reader = new SAXReader();
	    Document doc = null;
		try {
			doc = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			throw new PayException("微信支付返回内容，编码格式不正确："+xml, e);
		} catch (DocumentException e) {
			throw new PayException("微信支付返回内容，XML格式错误："+xml, e);
		}

	    @SuppressWarnings("unchecked")
	    List<Node> nodeList = doc.selectNodes("//xml/*");
	    Map<String, Object> pa = new HashMap<String, Object>(nodeList.size());
	    for (Node node : nodeList) {
			pa.put(node.getName(), node.getText());
		}
	    
	    return pa;
	}

}
