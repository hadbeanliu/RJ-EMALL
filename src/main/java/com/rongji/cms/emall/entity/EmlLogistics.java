package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

/**
 * 物流表
 * 
 * @author rjf
 *
 */
@Entity
@Table(name= TablePrefix.NAME + "LOGISTICS")
public class EmlLogistics implements Sid {
	
	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="ORDER_ID")
	@Comment("订单编号")
	private String orderId;
	
	@Column(name="LOGIS_COMPANY_CODE")
	@Comment("物流公司代码")
	private String logiscompanycode;
	
	@Column(name="MAIL_NO")
	@Comment("运单号码")
	private String mailno;
	
	@Column(name="SEND_ADDRESS_ID")
	@Comment("发货地址编号")
	private String senddefid;
	
	@Column(name="CANCEL_ADDRESS_ID")
	@Comment("退货地址编号")
	private String canceldefid;
	
	@Column(name="RECEIVER_NAME")
	@Comment("收货人姓名")
	private String receiver;
	
	@Column(name="RECEIVER_MOBILE_PHONE")
	@Comment("收货人手机号码")
	private String mobiletel;
	
	@Column(name="RECEIVER_TELEPHONE")
	@Comment("收货人固定电话")
	private String phone;
	
	@Column(name="RECEIVER_ZIP_CODE")
	@Comment("收货邮编")
	private String zipcode;
	
	@Column(name="RECEIVER_AREAS")
	@Comment("收货所在地")
	private String areas;
	
	@Column(name="RECEIVER_ADDRESS")
	@Comment("收货详细地址")
	private String address;
	
	@Column(name="RECEIVER_AREAS_CODE")
	@Comment("收货所在地区县区号")
	private String distcode;
	
	@Column(name="REMARK")
	@Comment("备注")
	private String remark;
	
	@Column(name="CREATE_TIME")
	@Comment("发货完成时间")
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
		EmlLogistics other = (EmlLogistics) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
