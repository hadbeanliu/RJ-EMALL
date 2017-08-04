package com.rongji.cms.emall.vo;



import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;



public class MesAuditSearchQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	private String auditId;
	private Complaint messages;


	private String receiverId;
	private String isDept;
	private Date receiveTime;
	private String batchesViews;
	private String readStatus;
	private String status;
	private String replyContent;
	private Date peplyTime;
	private String executorId;
	
	public MesAuditSearchQuery(){
		
	}
	
	

	public Complaint getMessages() {
		return messages;
	}



	public void setMessages(Complaint messages) {
		this.messages = messages;
	}



	public String getAuditId() {
		return this.auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	
	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getIsDept() {
		return this.isDept;
	}

	public void setIsDept(String isDept) {
		this.isDept = isDept;
	}

	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getBatchesViews() {
		return this.batchesViews;
	}

	public void setBatchesViews(String batchesViews) {
		this.batchesViews = batchesViews;
	}

	public String getReadStatus() {
		return this.readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getPeplyTime() {
		return this.peplyTime;
	}

	public void setPeplyTime(Date peplyTime) {
		this.peplyTime = peplyTime;
	}

	public String getExecutorId() {
		return this.executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

}
