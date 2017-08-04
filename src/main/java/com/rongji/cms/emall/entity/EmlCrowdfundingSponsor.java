package com.rongji.cms.emall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;
import com.rongji.cms.emall.support.TablePrefix;

@Entity
@Table(name = TablePrefix.NAME + "CROWDFUNDING_SPONSOR")
public class EmlCrowdfundingSponsor implements Sid {	
	
	@Id 
	@Column(name="CROWDFUNDING_SPONSOR_ID")
	private String id;	
	
	@Comment("发起人名称")
	private String sponsorName;
	
	@Comment("发起人简介")
	private String sponsorSummary;
	
	@Comment("头像")
	private String sponsorHead;
	
	@Comment("其它资料")
	private String otherInformation;
	
	@Comment("联系人名称")
	private String contactName;
	
	@Comment("邮箱")
	private String email;
	
	@Comment("电话")
	private String phone;
  
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorSummary() {
		return sponsorSummary;
	}

	public void setSponsorSummary(String sponsorSummary) {
		this.sponsorSummary = sponsorSummary;
	}

	public String getSponsorHead() {
		return sponsorHead;
	}

	public void setSponsorHead(String sponsorHead) {
		this.sponsorHead = sponsorHead;
	}

	public String getOtherInformation() {
		return otherInformation;
	}

	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		EmlCrowdfundingSponsor other = (EmlCrowdfundingSponsor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
	
}
