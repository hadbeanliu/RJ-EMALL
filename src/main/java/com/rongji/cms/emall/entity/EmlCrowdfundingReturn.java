package com.rongji.cms.emall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;
import com.rongji.cms.emall.support.TablePrefix;

@Entity
@Table(name = TablePrefix.NAME + "CROWDFUNDING_RETURN")
public class EmlCrowdfundingReturn implements Sid {	
	
	@Id 
	@Column(name="CROWDFUNDING_RETURN_ID")
	private String id;
	
	@Comment("众筹表ID")
	private String crowdfundingId;
	
	@Comment("金额")
	private float money;
	
	@Comment("标题")
	private String title;
	
	@Comment("回报详情")
	private String returnDetails;
	
	@Comment("回报图片")
	private String returnPicture;
	
	@Comment("回报数量")
	private int returnNumber;
	
	@Comment("个人限购")
	private int individualPurchase;
	
	@Comment("物流方式")
	private char logisticsWay;
	
	@Comment("发放天数")
	private int releaseDays;
	
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

	public String getReturnPicture() {
		return returnPicture;
	}

	public void setReturnPicture(String returnPicture) {
		this.returnPicture = returnPicture;
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
		EmlCrowdfundingReturn other = (EmlCrowdfundingReturn) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
