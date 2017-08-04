package com.rongji.cms.emall.service.pay.provider.alipay;

import java.util.HashMap;
import java.util.Map;

import com.alipay.util.AlipaySubmit;
import com.rongji.cms.emall.service.pay.provider.PayException;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;

/**
 * 支付宝即时到账交易接口
 *
 * @since 2015-6-26
 * @author rjf
 *
 */
public class AlipayDirectPayProvider extends AlipayProvider {
	
	@Override
	protected String getService() {
		return "create_direct_pay_by_user";
	}

	@Override
	public String getDescription() {
		return "网上交易时，买家的交易资金直接打入卖家支付宝账户，快速回笼交易资金。";
	}

	@Override
	public String getTitle() {
		return "支付宝[即时到账交易]";
	}

	@Override
	public String doPay(Payment payment, Order order, Map<String, String> params) throws PayException {
		
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
		sParaTemp.put("total_fee", order.getPriceAndFee()); // 付款金额 必填
//		sParaTemp.put("total_fee", order.getPriceString()); // 付款金额 必填
		sParaTemp.put("body", order.getOrderBody()); // 订单描述
		sParaTemp.put("show_url", order.getOrderUrl()); // 商品展示地址
		
		if (config.isAntiPhishing()) {
			try {
				sParaTemp.put("anti_phishing_key", AlipaySubmit.query_timestamp(config.getPartner()));
			} catch (Exception e) {
				throw new PayException("获取支付宝防钓鱼时间戳失败", e);
			}
			
			String ip = params.get(EXTERNAL_IP_KEY);
			if (ip == null || ip.trim().isEmpty()) {
				throw new PayException("客户端的IP地址不允许为空， [params]中key["+ EXTERNAL_IP_KEY +"]的值["+ ip +"]不正确");
			}
			// 防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
			sParaTemp.put("exter_invoke_ip", ip); // 客户端的IP地址(用户在创建交易时，该用户当前所使用机器的IP) 非局域网的外网IP地址，如：221.0.0.1
		}
		
		String htm = AlipaySubmit.buildRequest(sParaTemp,"get","确认", config.getKey());
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head>"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");
		sb.append(htm);
		sb.append("</body></html>");
		return sb.toString();
	}


}
