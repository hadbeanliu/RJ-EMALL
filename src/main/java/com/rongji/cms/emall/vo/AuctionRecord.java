package com.rongji.cms.emall.vo;


import com.rongji.rjskeleton.support.id.Sid;

public class AuctionRecord implements Sid{
	
	private String id;	
	private Goods goods;
	private String userId;	
	private String shootingTime;
	private int auctionQuantity;
	private int accessQuantity;
	private double currentPrice;
	private float myHighestPrice;
	private String endTime;	
	private MyBond myBond;
	
	
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
	public String getShootingTime() {
		return shootingTime;
	}
	public void setShootingTime(String shootingTime) {
		this.shootingTime = shootingTime;
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public MyBond getMyBond() {
		return myBond;
	}
	public void setMyBond(MyBond myBond) {
		this.myBond = myBond;
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
		AuctionRecord other = (AuctionRecord) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
