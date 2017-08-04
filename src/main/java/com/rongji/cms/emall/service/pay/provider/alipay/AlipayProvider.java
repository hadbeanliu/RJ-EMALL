package com.rongji.cms.emall.service.pay.provider.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rongji.cms.emall.service.pay.PayInfo;
import com.rongji.cms.emall.service.pay.provider.PayException;
import com.rongji.cms.emall.service.pay.provider.PayProvider;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;
import com.rongji.dfish.cache.meta.LongMeta;

public abstract class AlipayProvider implements PayProvider {
	
	private SimpleDateFormat refundDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat batchDate = new SimpleDateFormat("yyyyMMdd");
	private String batchDay = batchDate.format(new Date());
//	private final AtomicInteger batchIndex = new AtomicInteger(1000);
	private final Object batchIndexLock = new Object();
	private LongMeta bactchIndex; 

	@Override
	public String getServiceName() {
		return "alipay_" + getService();
	}

	@Override
	public PayInfo verify(Payment payment, Map<String, String> params) {
		
		AlipayConfig config = getConfig(payment);
		if ( AlipayNotify.verify(params, config.getPartner(), getVerifyKey(config)) ) {
			PayInfo payInfo = new PayInfo();
			payInfo.setOrderId(params.get("out_trade_no"));
			payInfo.setTradeNo(params.get("trade_no"));
			payInfo.setTradeStatus(params.get("trade_status"));
			payInfo.setRefundId(params.get("batch_no"));
			
			return payInfo;
		}
		
		return null;
	}
	
	@Override
	public String convertConfig(Payment payment) {
		
		if (payment.getConfig() == null || payment.getConfig().trim().isEmpty()) {
			throw new PayException("支付配置信息不能为空");
		}
		
		AlipayConfig config = getConfig(payment);
		
		
		if (payment.getCertFile() != null && !payment.getCertFile().isEmpty()) {
			InputStream certFile = null;
			String cert = null;
			
//		Scanner scanner = null;
//		try {
//			scanner = new Scanner(certFile.getInputStream()).useDelimiter("\\A");
//		} catch (IOException e) {
//			throw new PayException("支付配置信息证书读取失败: " + certFile.getOriginalFilename(), e);
//		}
//		
//		try {
//			cert = scanner != null && scanner.hasNext() ? scanner.next() : "";
//		} catch (Exception e) {
//			throw new PayException("支付配置信息证书读取失败: " + certFile.getOriginalFilename(), e);
//		} finally {
//			scanner.close();
//		}
			
			try {
				certFile = payment.getCertFile().getInputStream();
				InputStreamReader is=new InputStreamReader(certFile);
				BufferedReader br=new BufferedReader(is);
				String read = null;
				StringBuilder sb = new StringBuilder();
				while((read = br.readLine()) != null) {
					sb.append(read);
				}
				cert = sb.toString();
			} catch (IOException e) {
				throw new PayException("支付配置信息证书读取失败: " + payment.getCertFile().getOriginalFilename(), e);
			} finally {
				if (certFile != null) {
					try {
						certFile.close();
					} catch (IOException e) {
						throw new PayException("支付配置信息证书文件关闭失败: " + payment.getCertFile().getOriginalFilename(), e);
					}
				}
			}
			
			if (cert == null || cert.trim().isEmpty()) {
				throw new PayException("支付配置信息证书PKCS8格式的私钥不允许为空");
			}
			if (cert.startsWith("-----BEGIN PRIVATE KEY-----") && cert.endsWith("-----END PRIVATE KEY-----")) {
				config.setCert(cert.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", ""));
			} else {
				throw new PayException("支付配置信息证书PKCS8格式的私钥内容不正确");
			}
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
		AlipayConfig config = getConfig(payment);
		
		String url = EmallConfig.getEmallUrl();
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", config.getPartner());
        sParaTemp.put("_input_charset", "utf-8");
        sParaTemp.put("notify_url", url+"/pay/provider_alipay/refund_notify.htm");
        sParaTemp.put("seller_email", config.getSellerEmail());
		sParaTemp.put("refund_date", refundDate.format(new Date()));
//		sParaTemp.put("batch_no", order.getRefund().getId());
		sParaTemp.put("batch_no", getBatchNo());
		sParaTemp.put("batch_num", "1");
		if (remark.indexOf('^') >= 0 || remark.indexOf('|') >= 0 || remark.indexOf('$') >= 0
				 || remark.indexOf('#') >= 0) {
			throw new PayException("退款原因包含特殊字符“^”、“|”、“$”、“#” ： " + remark);
		}
		if (remark.length() > 256) {
			throw new PayException("退款原因长度超过最大限制256" + remark);
		}
		sParaTemp.put("detail_data", order.getTradeNo() + '^' + Order.getMoneyString(money) + '^' + remark);

		String htm = AlipaySubmit.buildRequest(sParaTemp,"get","确认", config.getKey());
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head>"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");
		sb.append(htm);
		sb.append("</body></html>");
		return sb.toString();
	}

	protected AlipayConfig getConfig(Payment payment) {
		try {
			return new Gson().fromJson(payment.getConfig(), AlipayConfig.class);
		} catch (JsonSyntaxException e) {
			throw new PayException("支付宝支付配置信息格式不正确", e);
		}
	}
	
	protected String getVerifyKey(AlipayConfig config) {
		return config.getKey();
	}
	
	protected abstract String getService();

	private String getBatchNo() {
		String nd = batchDate.format(new Date());
//		if ( ! nd.equals(batchDay) ) {
//			synchronized (batchIndexLock) {
//				batchIndex.getAndSet(1000);
//				batchDay = nd;
//			}
//		}
//		return batchDay + batchIndex.incrementAndGet();
		
		if (! nd.equals(batchDay)) {
			batchDay = nd;
			bactchIndex = null;
		}
		if ( bactchIndex == null ) {
			synchronized (batchIndexLock) {
				if (bactchIndex == null) {
					bactchIndex = new LongMeta("pay_ali_batchIndex_" + batchDay);
					if (bactchIndex.get() == 0) {
						bactchIndex.expire(24 * 60 * 60);
						bactchIndex.set("10000000");
					}
				}
			}
		}
		
		Long plus = bactchIndex.plus(1);
//		System.out.println(plus);
		return batchDay + plus;
	}
	
}
