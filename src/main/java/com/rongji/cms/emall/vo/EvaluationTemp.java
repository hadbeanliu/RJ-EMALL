package com.rongji.cms.emall.vo;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;
import com.rongji.rjskeleton.support.id.Sid;

public class EvaluationTemp extends Module implements Sid{
	
	@Expose public String storeId;
	@Expose public String comment;
	@Expose public String goodsTitle;
	@Expose public String skuInfo;
	@Expose public String nickName;
	@Expose public String userId;
	@Expose public String sellerName;
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getSkuInfo() {
		return skuInfo;
	}
	public void setSkuInfo(String skuInfo) {
		this.skuInfo = skuInfo;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}	
	
}
