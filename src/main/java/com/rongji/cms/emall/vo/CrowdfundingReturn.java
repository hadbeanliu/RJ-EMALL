package com.rongji.cms.emall.vo;

import org.springframework.web.multipart.MultipartFile;

import com.rongji.rjskeleton.support.id.Sid;

//众筹分类表
public class CrowdfundingReturn implements Sid{

	private String id;
	private String crowdfundingId;
	private float money;
	private String title;
	private String returnDetails;
	private int returnNumber;
	private int individualPurchase;
	private char logisticsWay;
	private int releaseDays;
	private String returnPicture;
	private MultipartFile returnPictureFile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(String returnDetails) {
		this.returnDetails = returnDetails;
	}

	public int getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(int returnNumber) {
		this.returnNumber = returnNumber;
	}

	public int getIndividualPurchase() {
		return individualPurchase;
	}

	public void setIndividualPurchase(int individualPurchase) {
		this.individualPurchase = individualPurchase;
	}

	public char getLogisticsWay() {
		return logisticsWay;
	}

	public void setLogisticsWay(char logisticsWay) {
		this.logisticsWay = logisticsWay;
	}

	public int getReleaseDays() {
		return releaseDays;
	}

	public void setReleaseDays(int releaseDays) {
		this.releaseDays = releaseDays;
	}

	public String getCrowdfundingId() {
		return crowdfundingId;
	}

	public void setCrowdfundingId(String crowdfundingId) {
		this.crowdfundingId = crowdfundingId;
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
		CrowdfundingReturn other = (CrowdfundingReturn) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getReturnPicture() {
		return returnPicture;
	}

	public void setReturnPicture(String returnPicture) {
		this.returnPicture = returnPicture;
	}

	public MultipartFile getReturnPictureFile() {
		return returnPictureFile;
	}

	public void setReturnPictureFile(MultipartFile returnPictureFile) {
		this.returnPictureFile = returnPictureFile;
	}
}
