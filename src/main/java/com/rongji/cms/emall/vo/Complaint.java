package com.rongji.cms.emall.vo;

import java.util.Date;

import com.rongji.rjskeleton.support.id.Sid;

public class Complaint implements Sid{
	private String msgId;
	private String msgTitle;
	private String msgContent;
	private String reportedDept;
	private Date createDate;
//	private String categoryId;
	private String author;
	private String contactInfo;
	private String msgEmail;
	private String msgIp;
	private String isDisplay;
	private String status;
	private String attachUrl;
	private String verCode;
	private String isOpen;
	private String isRecommend;
	private String formJson;
	private Date endDate;
	private String bindCatelog;
	private String pubStatus;
	private Date pubDate;
	private String replyDept;
	private String isDelete;//留言删除状态 update by zyl
	private String objId;//关联平台对象
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getReportedDept() {
		return reportedDept;
	}
	public void setReportedDept(String reportedDept) {
		this.reportedDept = reportedDept;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getMsgEmail() {
		return msgEmail;
	}
	public void setMsgEmail(String msgEmail) {
		this.msgEmail = msgEmail;
	}
	public String getMsgIp() {
		return msgIp;
	}
	public void setMsgIp(String msgIp) {
		this.msgIp = msgIp;
	}
	public String getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAttachUrl() {
		return attachUrl;
	}
	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}
	public String getVerCode() {
		return verCode;
	}
	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getFormJson() {
		return formJson;
	}
	public void setFormJson(String formJson) {
		this.formJson = formJson;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getBindCatelog() {
		return bindCatelog;
	}
	public void setBindCatelog(String bindCatelog) {
		this.bindCatelog = bindCatelog;
	}
	public String getPubStatus() {
		return pubStatus;
	}
	public void setPubStatus(String pubStatus) {
		this.pubStatus = pubStatus;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getReplyDept() {
		return replyDept;
	}
	public void setReplyDept(String replyDept) {
		this.replyDept = replyDept;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return msgId;
	}
	@Override
	public void setId(String id) {
		this.msgId = id;
		
	}
	

}
