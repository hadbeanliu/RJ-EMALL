package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

/**
 * 商店支付方式表
 * 
 * @author rjf
 *
 */
@Entity
@Table(name= TablePrefix.NAME + "PAYMENT")
public class EmlPayment implements Sid {
	
	@Id
	@Column
	private String id;
	
//	/**
//	 * 支付提供商
//	 */
//	@ManyToOne  @JoinColumn(name="PAY_SERVICE_PROVIDER_ID")
//	private EmlPayServiceProvider payServiceProvider;
	
	@Column(length=64)
	@Comment("支付提供商代号")
	private String payServiceProviderCode;
	
	@Column(length=128)
	@Comment("支付提供商名称")
	private String payServiceProviderTitle;
	
	@Column(length=500)
	@Comment("支付提供商描述")
	private String payServiceProviderDesc;
    
	/**
	 * 支付方式所属商家
	 */
	@ManyToOne @JoinColumn(name="STORE_ID")
	private EmlStore store;
    
	/**
	 * 保存商户接入的支付信息，JSON格式并加密存储
	 */
	@Column
	@Lob @Basic(fetch=FetchType.EAGER)
	@Comment("支付配置信息")
	private String config;
	
	/**
	 * 状态分：启用、禁用
	 */
	@Column
	@Comment("状态")
	private String status;
	
	@Column
	@Comment("创建者")
	private String createUser;
	
	@Column
	@Comment("创建时间")
	private Date createTime;
	
	@Column
	@Comment("修改时间")
	private Date modifyTime;

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

//	public EmlPayServiceProvider getPayServiceProvider() {
//		return payServiceProvider;
//	}
//
//	public void setPayServiceProvider(EmlPayServiceProvider payServiceProvider) {
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

	public EmlStore getStore() {
		return store;
	}

	public void setStore(EmlStore store) {
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
		EmlPayment other = (EmlPayment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
