package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

/**
 * 订单表
 * 
 * @author rjf
 *
 */
@Entity
@Table(name= TablePrefix.NAME + "ORDER")
public class EmlOrder implements Sid {
	
	/**
	 * 内部数据使用
	 */
	@Id
	@Column(length=16, name="id")
	@Comment("订单ID")
	private String id;
	
	
	/**
	 * 对外使用
	 */
	@Column(length=32)
	@Comment("订单编号")
	private String orderNo;
	
	
	/**
	 * 订单总金额（可能存在多个商品，加密后存储）
	 */
	@Column(length=64)
	@Comment("总金额")
	private Long price;
	
	
//	/**
//	 * 支付服务提供商
//	 */
//	@ManyToOne(optional=true) @JoinColumn(name="PAY_SP_ID")
//	@Comment("支付平台")
//	private EmlPayServiceProvider payServiceProvider;
	
	
	/**
	 * 支付服务提供商编码
	 */
	@Column(length=64)
	@Comment("支付平台")
	private String payCode;
	
	/**
	 * 记录物流的信息，包含运费等信息
	 */
	@OneToOne(optional=true) @JoinColumn(name="LOGISTICS_ID")
	@Comment("物流编号")
	private EmlLogistics logistics;

	@Column
	@Comment("运费")
	private Long logisticsFee = 0L;
	
	@OneToOne(optional=true) @JoinColumn(name="ADDRESS_ID")
	@Comment("收货地址")
	private EmlAddress address;
	
	@Column(length=32)
	@Comment("配送方式")
	private String carryId; // 配送方式
	
	/**
	 * 订单状态，关联数据字典使用
	 */
	@Column(length=2)
	@Comment("状态")
	private String status;
	
	@Column(length=2)
	@Comment("订单类型")
	private String type;
	
	/**
	 * 订单所属的店铺
	 */
	@ManyToOne @JoinColumn(name="STORE_ID")
	@Comment("店铺")
	private EmlStore store;
	
	@Column
	@Comment("买家留言")
	private String remark;
	
	
	/**
	 * 进行下单的人员。通常情况下是购买的用户，不排除特殊情况
	 */
	@Column(nullable=false, length=32)
	@Comment("创建人员")
	private String createUser;
	
	
	/**
	 * 订单创建时间，这时订单编号还未生成
	 */
	@Column(nullable=false)
	@Comment("创建时间")
	private Date createTime;
	
	/**
	 * 在创建订单自后，在付款之前可进行订单调整，并记录修改时间。
	 */
	@Column
	@Comment("修改时间")
	private Date modifyTime;
	
	/**
	 * 用户提交订单的时间，这时创建订单编号
	 */
	@Column
	@Comment("成交时间")
	private Date dealTime;
	
	@Column
	@Comment("支付交易号")
	private String tradeNo;
	
	@Column
	@Comment("支付交易状态")
	private String tradeStatus;
	
	/**
	 * 付款时间。成交后的一段时间内可进行付款，超过规定时间订单将被取消
	 */
	@Column
	@Comment("付款时间")
	private Date paymentTime;
	
	/**
	 * 在商家确认订单时设置
	 */
	@Column(length=32)
	@Comment("确认订单人员")
	private String confirmUser;
	
	/**
	 * 商家确认订单的时间
	 */
	@Column
	@Comment("确认订单时间")
	private Date storeConfirmTime;
	
	/**
	 * 本时间标志着，当前订单的完结。此后可以进行售后等相关事宜。
	 */
	@Column
	@Comment("订单完成时间")
	private Date doneTime;
	
	@Column
	@Comment("点评ID")
	private String rateId;
	
	@Column
	@Comment("点评时间")
	private Date rateTime;
	
	public EmlOrder(){}
	public EmlOrder(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

//	public EmlPayServiceProvider getPayServiceProvider() {
//		return payServiceProvider;
//	}
//
//	public void setPayServiceProvider(EmlPayServiceProvider payServiceProvider) {
//		this.payServiceProvider = payServiceProvider;
//	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public EmlLogistics getLogistics() {
		return logistics;
	}

	public void setLogistics(EmlLogistics logistics) {
		this.logistics = logistics;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EmlStore getStore() {
		return store;
	}
	
	public void setStore(EmlStore store) {
		this.store = store;
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

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public Date getStoreConfirmTime() {
		return storeConfirmTime;
	}

	public void setStoreConfirmTime(Date storeConfirmTime) {
		this.storeConfirmTime = storeConfirmTime;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	public Date getRateTime() {
		return rateTime;
	}
	public void setRateTime(Date rateTime) {
		this.rateTime = rateTime;
	}
	
	public EmlAddress getAddress() {
		return address;
	}
	public void setAddress(EmlAddress address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getCarryId() {
		return carryId;
	}
	public void setCarryId(String carryId) {
		this.carryId = carryId;
	}
	public Long getLogisticsFee() {
		return logisticsFee;
	}
	public void setLogisticsFee(Long logisticsFee) {
		this.logisticsFee = logisticsFee;
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
		EmlOrder other = (EmlOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
