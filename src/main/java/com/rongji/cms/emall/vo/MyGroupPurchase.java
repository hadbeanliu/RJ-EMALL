package com.rongji.cms.emall.vo;

import java.util.Date;

import com.rongji.rjskeleton.support.id.Sid;

public class MyGroupPurchase implements Sid {
	
	private String id;
	private String goodId;
	private String goodsTitle;
	private String userId;
	private int purchaseGoodsNumber;
	private float purchaseGoodsMonery;
	private char purchaseGoodsState;
	private Date joinTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPurchaseGoodsNumber() {
		return purchaseGoodsNumber;
	}

	public void setPurchaseGoodsNumber(int purchaseGoodsNumber) {
		this.purchaseGoodsNumber = purchaseGoodsNumber;
	}

	public float getPurchaseGoodsMonery() {
		return purchaseGoodsMonery;
	}

	public void setPurchaseGoodsMonery(float purchaseGoodsMonery) {
		this.purchaseGoodsMonery = purchaseGoodsMonery;
	}

	public char getPurchaseGoodsState() {
		return purchaseGoodsState;
	}

	public void setPurchaseGoodsState(char purchaseGoodsState) {
		this.purchaseGoodsState = purchaseGoodsState;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
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
		MyGroupPurchase other = (MyGroupPurchase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
