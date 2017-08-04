package com.rongji.cms.emall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name= TablePrefix.NAME + "AUCTION_RECORD")
public class EmlAuctionRecord implements Sid {
	
	@Id
	@Column(name="AUCTION_RECORD_ID")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Comment("商品ID")
	private String goodsId;		
	
	@Comment("用户ID")
	private String userId;
	
	@Comment("开拍时间")
	private String shootingTime;
	
	@Comment("竞拍数量")
	private int auctionQuantity;
	
	@Comment("获得数量")
	private int accessQuantity;
	
	@Comment("当前价格")
	private double currentPrice;
	
	@Comment("我的最高出价")
	private float myHighestPrice;
	
	@Comment("结束时间")
	private String endTime;	

	@Comment("我的保证金ID")
	private String bondId;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAuctionQuantity() {
		return auctionQuantity;
	}

	public void setAuctionQuantity(int auctionQuantity) {
		this.auctionQuantity = auctionQuantity;
	}

	public int getAccessQuantity() {
		return accessQuantity;
	}

	public void setAccessQuantity(int accessQuantity) {
		this.accessQuantity = accessQuantity;
	}
	
	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public float getMyHighestPrice() {
		return myHighestPrice;
	}

	public void setMyHighestPrice(float myHighestPrice) {
		this.myHighestPrice = myHighestPrice;
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

	public String getBondId() {
		return bondId;
	}

	public void setBondId(String bondId) {
		this.bondId = bondId;
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
		EmlAuctionRecord other = (EmlAuctionRecord) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}
