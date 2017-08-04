package com.rongji.cms.emall.vo;

import org.springframework.web.multipart.MultipartFile;

import com.rongji.rjskeleton.support.id.Sid;

//众筹发起人信息
public class CrowdfundingSponsor implements Sid{

	private String id;	
	private String sponsorName;
	private String sponsorSummary;
	private String sponsorHead;
	private MultipartFile sponsorHeadFile;
	private String otherInformation;
	private MultipartFile otherInformationFile;
	private String contactName;
	private String email;
	private String phone;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public MultipartFile getSponsorHeadFile() {
		return sponsorHeadFile;
	}
	public void setSponsorHeadFile(MultipartFile sponsorHeadFile) {
		this.sponsorHeadFile = sponsorHeadFile;
	}
	public MultipartFile getOtherInformationFile() {
		return otherInformationFile;
	}
	public void setOtherInformationFile(MultipartFile otherInformationFile) {
		this.otherInformationFile = otherInformationFile;
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
		CrowdfundingSponsor other = (CrowdfundingSponsor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
