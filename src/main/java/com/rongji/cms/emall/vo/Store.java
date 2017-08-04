package com.rongji.cms.emall.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.id.Sid;



public class Store implements Sid{

	private String storeId;
	
	@NotBlank
	@Size(max=10)
	private String storeName;
	@NotBlank
	@Size(max=100)
	private String description;

	@NotBlank
	private String logo;
	@NotBlank
	private String businessType;
	@NotBlank
	private String address;
	@Size(max=200)
	private String storeDescription;

	private String supply;
	@NotBlank
	private User user;
	private String isentity;
	@NotBlank
	private String	isfactory;
	private int sort;
	private int star;
	private Date creatTime;
	private String status;
	
	private String cmsSiteId;
	
	private String	mainSources ;
	
	public String getMainSources() {
		return mainSources;
	}
	public void setMainSources(String mainSources) {
		this.mainSources = mainSources;
	}
	public String getCmsSiteId() {
		return cmsSiteId;
	}
	public void setCmsSiteId(String cmsSiteId) {
		this.cmsSiteId = cmsSiteId;
	}


	//private EoUser user;
	private int order;
	public Store() {
		this.order=0;
		this.star=0;
		this.sort=0;
		this.status="0";
		this.creatTime=new Date();
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private int level;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}



	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStoreDescription() {
		return storeDescription;
	}

	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}



/*	public EoUser getUser() {
		return user;
	}

	public void setUser(EoUser user) {
		this.user = user;
	}*/

	public String getSupply() {
		return supply;
	}
	public void setSupply(String supply) {
		this.supply = supply;
	}
	public String getIsentity() {
		return isentity;
	}
	public void setIsentity(String isentity) {
		this.isentity = isentity;
	}
	public String getIsfactory() {
		return isfactory;
	}
	public void setIsfactory(String isfactory) {
		this.isfactory = isfactory;
	}
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.storeId;
	}

	@Override
	public void setId(String id) {
		this.storeId=id;
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
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
		Store other = (Store) obj;
		if (storeId == null) {
			if (other.storeId != null)
				return false;
		} else if (!storeId.equals(other.storeId))
			return false;
		return true;
	}
	
}
