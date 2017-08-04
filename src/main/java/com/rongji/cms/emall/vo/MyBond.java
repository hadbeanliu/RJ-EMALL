package com.rongji.cms.emall.vo;

import java.util.Date;

import com.rongji.rjskeleton.support.id.Sid;

public class MyBond implements Sid{
	public MyBond(){}
	private String id;	
	
	private String userId;
	
	private String submitTime;
	
	private float bondMonery;	

	private Goods goods;
	
    private Date releaseTime;
    
    private Date transferTime;

    private Order order;
    
    private String shootingTime;
    private String endTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public float getBondMonery() {
		return bondMonery;
	}
	public void setBondMonery(float bondMonery) {
		this.bondMonery = bondMonery;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public Date getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getShootingTime() {
		return shootingTime;
	}
	public void setShootingTime(String shootingTime) {
		this.shootingTime = shootingTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
		MyBond other = (MyBond) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
