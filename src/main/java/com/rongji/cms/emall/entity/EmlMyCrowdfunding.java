package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;
import com.rongji.cms.emall.support.TablePrefix;

@Entity
@Table(name = TablePrefix.NAME + "MY_CROWDFUNDING")
public class EmlMyCrowdfunding implements Sid {	
	
	@Id 
	@Column(name="MY_CROWDFUNDING_ID")
	private String id;
	
	@Comment("众筹ID")
	@ManyToOne(optional=true)@JoinColumn(name="CROWDFUNDING_ID")
	private EmlCrowdfunding emlCrowdfunding;
	
	@Comment("众筹回报ID")
	@ManyToOne(optional=true)@JoinColumn(name="CROWDFUNDING_RETURN_ID")
	private EmlCrowdfundingReturn emlCrowdfundingReturn;
	
	@Comment("支持时间")
	private  Date supportTime;
	
	@Comment("用户ID")
	private  String userId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public EmlCrowdfunding getEmlCrowdfunding() {
		return emlCrowdfunding;
	}
	public void setEmlCrowdfunding(EmlCrowdfunding emlCrowdfunding) {
		this.emlCrowdfunding = emlCrowdfunding;
	}		
	public EmlCrowdfundingReturn getEmlCrowdfundingReturn() {
		return emlCrowdfundingReturn;
	}
	public void setEmlCrowdfundingReturn(EmlCrowdfundingReturn emlCrowdfundingReturn) {
		this.emlCrowdfundingReturn = emlCrowdfundingReturn;
	}	
	public Date getSupportTime() {
		return supportTime;
	}
	public void setSupportTime(Date supportTime) {
		this.supportTime = supportTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
		EmlMyCrowdfunding other = (EmlMyCrowdfunding) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
