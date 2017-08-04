package com.rongji.cms.emall.service.pay.provider.wxpay;

public class WeixinPayConfig {
	
	private String appId; // 移动应用AppID 公众帐号身份的唯一标识。审核通过后，在微信发送的邮件中查看。
//	private String appSecret; // 移动应用AppSecret
	
	private String mchId; // 微信支付商户号
	private String key; // API密钥，参考开户邮件设置（必须配置，登录商户平台自行设置） 设置地址：https://pay.weixin.qq.com/index.php/account/api_cert
	
	private byte[] cert; // 微信商户平台 API证书
	private String certPassword; // 微信商户平台 API证书密码，默认为：微信支付商户号
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public byte[] getCert() {
		return cert;
	}
	public void setCert(byte[] cert) {
		this.cert = cert;
	}
	public String getCertPassword() {
		return certPassword == null || certPassword.trim().isEmpty() ? mchId : certPassword;
	}
	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}
	
//	private String partner; // 财付通商户号
//	private String partnerKey; // 财付通密钥
//	private String paySignKey; // 支付签名密钥
}
