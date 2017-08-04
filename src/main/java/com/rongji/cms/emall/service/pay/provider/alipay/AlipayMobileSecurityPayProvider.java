package com.rongji.cms.emall.service.pay.provider.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.alipay.sign.SignUtils;
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
public class AlipayMobileSecurityPayProvider extends AlipayProvider {
	
	@Override
	protected String getService() {
		return "mobile.securitypay.pay";
	}

	@Override
	public String getDescription() {
		return "移动支付是一种程序式的支付方式，在手机、掌上电脑等无线设备的应用程序内，买家可通过支付宝进行付款购买特定服务或商品，资金即时到账";
	}

	@Override
	public String getTitle() {
		return "支付宝[移动支付]";
	}

	@Override
	public boolean isMobile() {
		return true;
	}

	@Override
	public String doPay(Payment payment, Order order, Map<String, String> params) throws PayException {
		
		AlipayConfig config = getConfig(payment);
		
		String paramStr = getOrderInfo(order, config);
		
		// 对订单做RSA 签名
		String sign = SignUtils.sign(paramStr, config.getCert());
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return paramStr + "&sign=\"" + sign + "\"&sign_type=\"" + SignUtils.ALGORITHM + "\"";
	}

	@Override
	protected String getVerifyKey(AlipayConfig config) {
		return config.getPublicKey();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(Order order, AlipayConfig config) {
		
		String url = EmallConfig.getEmallUrl();
		
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + config.getPartner() + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + config.getSellerEmail() + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + order.getOrderNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + order.getOrderTitle() + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + order.getOrderBody() + "\"";

		// 商品金额
//		orderInfo += "&total_fee=" + "\"" + order.getPriceString() + "\"";
		orderInfo += "&total_fee=" + "\"" + order.getPriceAndFee() + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + url + "/pay/provider_alipay/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"" + url + "/pay/provider_alipay/return.htm\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}


}
