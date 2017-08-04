package com.rongji.cms.emall.entity;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name = TablePrefix.NAME + "CROWDFUNDING")
public class EmlCrowdfunding implements Sid {	
	
	@Id
	@Column(name="CROWDFUNDING_ID")
	private String id;
	
    @Comment("店铺ID") 
	private String storeId;
    
    @Comment("用户ID") 
	private String userId;
    
    @Comment("发起人信息ID")
    @OneToOne(optional=true)@JoinColumn(name="SPONSOR_INFORMATION_ID") 
    private EmlCrowdfundingSponsor crowdfundingSponsor;    
    
    @Comment("发布分类")
	private char postCategories;
    
    @Comment("众筹名称")
	private String projectName;
    
    @Comment("最低金额")
	private float targetAmount;
    
    @Comment("项目图片")
	private String projectPicture;
    
    @Comment("定金比例")
	private int depositRatioOf;
    
    @Comment("创建时间")
	private Date createTime;
    
    @Comment("众筹开始时间")
	private Date startTime;
    
    @Comment("众筹结束时间")
	private Date endTime;
    
    @Comment("众筹概述")
	private String projectOutline;
    
    @Comment("众筹介绍")
	private String projectDescription;
    
    @Comment("筹集天数")
	private int raisingDays;
    
    @Comment("目前累计资金")
	private float currentTotalFunding;
    
    @Comment("支持人数")
	private int supportNumber;
    
    @Comment("达成率")
	private float achievementRate;
    
    @Comment("众筹状态")//（0:准备中 1:众筹中 2:已完成）
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

	public EmlCrowdfundingSponsor getCrowdfundingSponsor() {
		return crowdfundingSponsor;
	}

	public void setCrowdfundingSponsor(EmlCrowdfundingSponsor crowdfundingSponsor) {
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

	public void setEndTime(Timestamp endTime) {
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
		EmlCrowdfunding other = (EmlCrowdfunding) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
