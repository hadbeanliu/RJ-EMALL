package com.rongji.cms.emall.vo;

import java.util.Date;

import com.rongji.rjskeleton.support.id.Sid;

public class MessageModel implements Sid {
	
    private String id;
    
	
    private String modelSign;
    

	private String title;
    

	private String content;
	

	private Date createTime;
	

	private Date modifyTime;
	
	
	private String createUser;
	

	private String modelType;
	
	
	private String receiverType;
	
	
	private String isDel;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getModelSign() {
		return modelSign;
	}


	public void setModelSign(String modelSign) {
		this.modelSign = modelSign;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	
	public String getModelType() {
		return modelType;
	}


	public void setModelType(String modelType) {
		this.modelType = modelType;
	}


	public String getReceiverType() {
		return receiverType;
	}


	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}


	public String getIsDel() {
		return isDel;
	}


	public void setIsDel(String isDel) {
		this.isDel = isDel;
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
		MessageModel other = (MessageModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
