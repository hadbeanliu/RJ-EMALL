package com.rongji.cms.emall.vo;

import java.util.Calendar;
import java.util.Date;

import com.rongji.cms.emall.entity.EmlOrder;
import com.rongji.rjskeleton.support.id.Sid;

public class Refund implements Sid{
	private String refundId;
	private Order order;
	private String buyerRefundReason;
	private String handlingSuggestion;
	private double refundSum;
	private String refundStatus;
	private String orderStatus;
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	

	private Date creatTime;
	private Date endTime;
	private String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Refund() {
		this.creatTime=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(creatTime);
		c.add(Calendar.DAY_OF_YEAR, 5);
		this.endTime=c.getTime();
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return refundId;
	}

	@Override
	public void setId(String id) {
		this.refundId=id;
		
	}
	
public static void main(String[] args) {
	Date creatTime=new Date();
	Calendar c = Calendar.getInstance();
	c.setTime(creatTime);
	c.add(Calendar.DAY_OF_YEAR, 5);
	System.out.println(c.getTime());
}
}
