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
@Table(name = TablePrefix.NAME + "message_model")
public class EmlMessageModel implements Sid {
	
	@Id
	@Column(name="model_id")
	private String id;
	
	@Column(nullable=false, name="model_sign")
	@Comment("模板标识")
	private String modelSign;
	
	@Column(nullable=false, length=200)
	@Comment("模板标题")
	private String title;

	@Column(nullable=false, length=4000)
	@Comment("模板内容")
	private String content;
	
	@Column(name="create_time")
	@Comment("创建时间")
	private Date createTime;
	
	@Column(name="modify_time")
	@Comment("修改时间")
	private Date modifyTime;
	
	@Column(nullable=false, length=16, name="create_user")
	@Comment("创建人员")
	private String createUser;
	
	@Column(nullable=false, length=1, name="model_type")
	@Comment("模板类型 0短信，1邮件，2站内信")
	private String modelType;
	
	@Column(nullable=false, length=1, name="reciver_type")
	@Comment("接收人类型 0所有人，1买家，2卖家")
	private String receiverType;
	
	@Column(length=1, name="is_del")
	@Comment("是否删除")
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
		EmlMessageModel other = (EmlMessageModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
