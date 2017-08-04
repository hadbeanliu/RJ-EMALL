package com.rongji.cms.emall.vo;

import java.util.Date;

import com.rongji.rjskeleton.support.id.Sid;

//我的众筹表
public class MyCrowdfunding implements Sid{

	private String id;
	private Crowdfunding crowdfunding;
	private CrowdfundingReturn crowdfundingReturn;
	private Date supportTime;
	private String userId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Crowdfunding getCrowdfunding() {
		return crowdfunding;
	}
	public void setCrowdfunding(Crowdfunding crowdfunding) {
		this.crowdfunding = crowdfunding;
	}
	public CrowdfundingReturn getCrowdfundingReturn() {
		return crowdfundingReturn;
	}
	public void setCrowdfundingReturn(CrowdfundingReturn crowdfundingReturn) {
		this.crowdfundingReturn = crowdfundingReturn;
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
		MyCrowdfunding other = (MyCrowdfunding) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
