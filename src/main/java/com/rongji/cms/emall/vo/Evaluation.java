package com.rongji.cms.emall.vo;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;
import com.rongji.rjskeleton.support.id.Sid;

public class Evaluation extends Module implements Sid{
	
	@Expose public EvaluationComment comment;
	@Expose public String goodsTitle;
	@Expose public String skuInfo;
	@Expose public Boolean isExplain;
	@Expose public Boolean isAdditionalExplain;
	@Expose public Boolean isAdditionalComment;
	@Expose public String nickName;
	@Expose public String sellerName;	
	
	public EvaluationComment getComment() {
		return comment;
	}
	public void setComment(EvaluationComment comment) {
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
	public Boolean getIsExplain() {
		return isExplain;
	}
	public void setIsExplain(Boolean isExplain) {
		this.isExplain = isExplain;
	}
	public Boolean getIsAdditionalExplain() {
		return isAdditionalExplain;
	}
	public void setIsAdditionalExplain(Boolean isAdditionalExplain) {
		this.isAdditionalExplain = isAdditionalExplain;
	}
	public Boolean getIsAdditionalComment() {
		return isAdditionalComment;
	}
	public void setIsAdditionalComment(Boolean isAdditionalComment) {
		this.isAdditionalComment = isAdditionalComment;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
}
