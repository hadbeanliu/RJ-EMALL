package com.rongji.cms.emall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name = TablePrefix.NAME + "message_personmodel")
public class EmlMessagePersonModel implements Sid{
	
	@Id
	@Column(name="PERSONMODEL_ID")
	private String id;
	
	@ManyToOne  @JoinColumn(name="MODEL_ID")
	private EmlMessageModel messageModel;
	
	@Comment("用户ID")
	private String userId;
	
	@Comment("短信是否启用")
	@Column(name="SMS_STATUS")
	private String smsStatus;
	
	@Comment("邮件是否启用")
	@Column(name="EMAIL_STATUS")
	private String emailStatus;
	
	@Comment("站内信是否启用")
	@Column(name="INMSG_STATUS")
	private String inMsgStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmlMessageModel getMessageModel() {
		return messageModel;
	}

	public void setMessageModel(EmlMessageModel messageModel) {
		this.messageModel = messageModel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getInMsgStatus() {
		return inMsgStatus;
	}

	public void setInMsgStatus(String inMsgStatus) {
		this.inMsgStatus = inMsgStatus;
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
		EmlMessagePersonModel other = (EmlMessagePersonModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
