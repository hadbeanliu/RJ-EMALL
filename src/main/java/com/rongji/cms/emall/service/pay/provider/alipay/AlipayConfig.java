package com.rongji.cms.emall.service.pay.provider.alipay;

public class AlipayConfig {
	
	private String partner; // 合作身份者ID，以2088开头由16位纯数字组成的字符串
	private String sellerEmail; // 收款支付宝账号
	private String key; // 商户的私钥
	private String publicKey; // 支付宝的公钥
	private boolean isAntiPhishing; // 是否已申请开通防钓鱼时间戳验证
	
	private String cert; // 商户证书
	
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getCert() {
		return cert;
	}
	public void setCert(String cert) {
		this.cert = cert;
	}
	/**
	 * 是否已申请开通防钓鱼时间戳验证
	 * @return 已开通返回{@literal true}，否则返回{@literal false}
	 */
	public boolean isAntiPhishing() {
		return isAntiPhishing;
	}
	public void setAntiPhishing(boolean isAntiPhishing) {
		this.isAntiPhishing = isAntiPhishing;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	

}
