package com.rongji.cms.emall.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.rongji.rjskeleton.support.id.Sid;

/**
 * 商店支付方式
 * 
 * @author rjf
 *
 */
public class Payment implements Sid {
	
	private String id;
//	private PayServiceProvider payServiceProvider;
	
	/**
	 * 提供支付服务商代号
	 */
	private String payServiceProviderCode;
	
	private String payServiceProviderTitle;
	
	private String payServiceProviderDesc;
    
	/**
	 * 支付方式所属商家
	 */
	private Store store;
    
	/**
	 * 保存商户接入的支付信息，JSON格式并加密存储
	 */
	private String config;
	
	/**
	 * 状态分：启用、禁用
	 */
	private String status;
	
	private String createUser;
	
	private Date createTime;
	
	private Date modifyTime;
	
	
	/**
	 * 商户证书
	 */
	private MultipartFile certFile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayServiceProviderCode() {
		return payServiceProviderCode;
	}

	public void setPayServiceProviderCode(String payServiceProviderCode) {
		this.payServiceProviderCode = payServiceProviderCode;
	}

//	public PayServiceProvider getPayServiceProvider() {
//		return payServiceProvider;
//	}
//
//	public void setPayServiceProvider(PayServiceProvider payServiceProvider) {
//		this.payServiceProvider = payServiceProvider;
//	}

	public String getPayServiceProviderTitle() {
		return payServiceProviderTitle;
	}

	public void setPayServiceProviderTitle(String payServiceProviderTitle) {
		this.payServiceProviderTitle = payServiceProviderTitle;
	}

	public String getPayServiceProviderDesc() {
		return payServiceProviderDesc;
	}

	public void setPayServiceProviderDesc(String payServiceProviderDesc) {
		this.payServiceProviderDesc = payServiceProviderDesc;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public MultipartFile getCertFile() {
		return certFile;
	}

	public void setCertFile(MultipartFile certFile) {
		this.certFile = certFile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
