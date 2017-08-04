package com.rongji.cms.emall.vo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.id.Sid;

/**
 * 物流
 * 
 * @author rjf
 *
 */
public class Logistics implements Sid {
	
	private String id;
	
	private String orderId;
	
	@NotBlank
	private String logiscompanycode;
	
	@NotBlank
	private String mailno;
	
	private String senddefid;
	
	private String canceldefid;
	
	private String receiver;
	
	private String mobiletel;
	
	private String phone;
	
	private String zipcode;
	
	private String areas;
	
	private String address;
	
	private String distcode;
	
	private String remark;
	
	private Date createtime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLogiscompanycode() {
		return logiscompanycode;
	}

	public void setLogiscompanycode(String logiscompanycode) {
		this.logiscompanycode = logiscompanycode;
	}

	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
	}

	public String getSenddefid() {
		return senddefid;
	}

	public void setSenddefid(String senddefid) {
		this.senddefid = senddefid;
	}

	public String getCanceldefid() {
		return canceldefid;
	}

	public void setCanceldefid(String canceldefid) {
		this.canceldefid = canceldefid;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobiletel() {
		return mobiletel;
	}

	public void setMobiletel(String mobiletel) {
		this.mobiletel = mobiletel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistcode() {
		return distcode;
	}

	public void setDistcode(String distcode) {
		this.distcode = distcode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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
		Logistics other = (Logistics) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
