package com.rongji.cms.emall.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.rongji.rjskeleton.support.id.Sid;

//众筹表
public class Crowdfunding implements Sid{

	private String id;
	private String storeId;
	private String userId;
	private CrowdfundingSponsor crowdfundingSponsor;	

	private char postCategories;
	private String projectName;
	private float targetAmount;
	private MultipartFile projectPictureFile;
	private String projectPicture;
	private int depositRatioOf;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private String projectOutline;
	private String projectDescription;
	private int raisingDays;
	private float currentTotalFunding;
	private int supportNumber;
	private float achievementRate;
	private char crowdfundingStatus;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public CrowdfundingSponsor getCrowdfundingSponsor() {
		return crowdfundingSponsor;
	}
	public void setCrowdfundingSponsor(CrowdfundingSponsor crowdfundingSponsor) {
		this.crowdfundingSponsor = crowdfundingSponsor;
	}
	public char getPostCategories() {
		return postCategories;
	}
	public void setPostCategories(char postCategories) {
		this.postCategories = postCategories;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public float getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(float targetAmount) {
		this.targetAmount = targetAmount;
	}
	public String getProjectPicture() {
		return projectPicture;
	}
	public void setProjectPicture(String projectPicture) {
		this.projectPicture = projectPicture;
	}
	public int getDepositRatioOf() {
		return depositRatioOf;
	}
	public void setDepositRatioOf(int depositRatioOf) {
		this.depositRatioOf = depositRatioOf;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getProjectOutline() {
		return projectOutline;
	}
	public void setProjectOutline(String projectOutline) {
		this.projectOutline = projectOutline;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public int getRaisingDays() {
		return raisingDays;
	}
	public void setRaisingDays(int raisingDays) {
		this.raisingDays = raisingDays;
	}
	public float getCurrentTotalFunding() {
		return currentTotalFunding;
	}
	public void setCurrentTotalFunding(float currentTotalFunding) {
		this.currentTotalFunding = currentTotalFunding;
	}
	public int getSupportNumber() {
		return supportNumber;
	}
	public void setSupportNumber(int supportNumber) {
		this.supportNumber = supportNumber;
	}
	public float getAchievementRate() {
		return achievementRate;
	}
	public void setAchievementRate(float achievementRate) {
		this.achievementRate = achievementRate;
	}
	public char getCrowdfundingStatus() {
		return crowdfundingStatus;
	}
	public void setCrowdfundingStatus(char crowdfundingStatus) {
		this.crowdfundingStatus = crowdfundingStatus;
	}
	public MultipartFile getProjectPictureFile() {
		return projectPictureFile;
	}
	public void setProjectPictureFile(MultipartFile projectPictureFile) {
		this.projectPictureFile = projectPictureFile;
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
		Crowdfunding other = (Crowdfunding) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
