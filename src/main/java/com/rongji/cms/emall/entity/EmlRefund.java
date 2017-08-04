package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;
@Entity
@Table(name = TablePrefix.NAME + "Refund")

@Comment("退款表")
public class EmlRefund implements Sid{

	@Id
	private String refundId;

	@Comment("订单信息")
	@ManyToOne(optional=true) @JoinColumn(name="order_id")	
	private EmlOrder order;
	@Column
	@Comment("买家退款理由")
	private String buyerRefundReason;
	@Column
	@Comment("卖家拒绝退款理由")
	private String handlingSuggestion;
	@Column
	@Comment("退款方式")
	private String refundStatus;
	@Column
	@Comment("退款金额")
	private double refundSum;
	@Column
	@Comment("创建时间")
	private Date creatTime;
	@Column
	@Comment("截止时间")
	private Date endTime;
	@Column
	@Comment("状态")
	private String status;
	@Column
	@Comment("原始状态")
	private String orderStatus;
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getBuyerRefundReason() {
		return buyerRefundReason;
	}
	public void setBuyerRefundReason(String buyerRefundReason) {
		this.buyerRefundReason = buyerRefundReason;
	}
	public String getHandlingSuggestion() {
		return handlingSuggestion;
	}
	public void setHandlingSuggestion(String handlingSuggestion) {
		this.handlingSuggestion = handlingSuggestion;
	}

	public double getRefundSum() {
		return refundSum;
	}
	public void setRefundSum(double refundSum) {
		this.refundSum = refundSum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getId() {
		return refundId;
	}
	@Override
	public void setId(String id) {
		this.refundId = id;		
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((refundId == null) ? 0 : refundId.hashCode());
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
		EmlRefund other = (EmlRefund) obj;
		if (refundId == null) {
			if (other.refundId != null)
				return false;
		} else if (!refundId.equals(other.refundId))
			return false;
		return true;
	}
	public EmlOrder getOrder() {
		return order;
	}
	public void setOrder(EmlOrder order) {
		this.order = order;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}	
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
}
