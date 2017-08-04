package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name= TablePrefix.NAME + "MY_GROUP_PURCHASE")
public class EmlMyGroupPurchase implements Sid {
	
	@Id
	@Comment("我的团购ID")
	@Column(length=16,name="GROUP_PURCHASE_ID")
	private String id;

	@Comment("商品ID")
	private String goodsId;
	
	@Comment("商品名")
	private String goodsTitle;
	
	@Comment("用户ID")
	private String userId;
	
	@Comment("团购数量")
	private int purchaseGoodsNumber;
	
	@Comment("团购金额")
	private float purchaseGoodsMonery;
	
	@Comment("团购状态")
	private char purchaseGoodsState;
	
	@Comment("参团时间")
	private Date joinTime;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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
		EmlMyGroupPurchase other = (EmlMyGroupPurchase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
