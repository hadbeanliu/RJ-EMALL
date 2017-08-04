package com.rongji.cms.emall.vo;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;

public class EvaluationComment extends Module{

	@Expose private String cmtId;
	@Expose private String userId;
	@Expose private String storeId;	
	@Expose public String comment;//评论内容
	@Expose public String additionalComment;//追加评论内容
	@Expose public String commentTime;//评论时间
	@Expose public String additionalCommentTime;//追加评论时间
	@Expose public String explain;//解释
	@Expose public String explainTime;//解释时间
	@Expose public String additionalExplain;//追加解释
	@Expose public String additionalExplainTime;//追加解释时间
	@Expose public String sunCommentPicture;//评论晒图
	@Expose public String sunAdditionalCommentPicture;//追加评论晒图
	@Expose public String determineReceipt;//商品确定收货时间
	
	public String getCmtId() {
		return cmtId;
	}
	public void setCmtId(String cmtId) {
		this.cmtId = cmtId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDetermineReceipt() {
		return determineReceipt;
	}
	public void setDetermineReceipt(String determineReceipt) {
		this.determineReceipt = determineReceipt;
	}
	public String getSunCommentPicture() {
		return sunCommentPicture;
	}
	public void setSunCommentPicture(String sunCommentPicture) {
		this.sunCommentPicture = sunCommentPicture;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getExplainTime() {
		return explainTime;
	}
	public void setExplainTime(String explainTime) {
		this.explainTime = explainTime;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getAdditionalExplain() {
		return additionalExplain;
	}
	public void setAdditionalExplain(String additionalExplain) {
		this.additionalExplain = additionalExplain;
	}
	public String getAdditionalExplainTime() {
		return additionalExplainTime;
	}
	public void setAdditionalExplainTime(String additionalExplainTime) {
		this.additionalExplainTime = additionalExplainTime;
	}
	public String getAdditionalComment() {
		return additionalComment;
	}
	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}
	public String getAdditionalCommentTime() {
		return additionalCommentTime;
	}
	public void setAdditionalCommentTime(String additionalCommentTime) {
		this.additionalCommentTime = additionalCommentTime;
	}
	public String getSunAdditionalCommentPicture() {
		return sunAdditionalCommentPicture;
	}
	public void setSunAdditionalCommentPicture(String sunAdditionalCommentPicture) {
		this.sunAdditionalCommentPicture = sunAdditionalCommentPicture;
	}
		
}
