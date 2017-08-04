package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;


@Entity
@Table(name = TablePrefix.NAME + "STORE")

@Comment("商铺表")
public class EmlStore implements Sid{


	
	@Id
	private String storeId;
	@Column
	@Comment("商铺名称")
	private String storeName;
	@Column
	@Comment("商铺简介")
	private String description;
	@Column
	@Comment("logo")
	private String logo;
	@Column
	@Comment("经营类型")
	private String businessType;
	@Column
	@Comment("地址")
	private String address;
	@Column
	@Comment("商铺介绍")
	private String storeDescription;
	@Column
	@Comment("经营类型")
	private String supply;
	@OneToOne(optional=true) @JoinColumn(name="user_id")
	@Comment("用户信息")
	private EmlUser user;
	@Column
	@Comment("是否拥有实体")
	private String isentity;

	@Column
	@Comment("是否拥有工厂")
	private String	isfactory;
	@Column
	@Comment("主要资源")
	private String	mainSources ;
	/*@Comment("用户部门")
	@ManyToOne @JoinColumn(name="user_id")*/
	//private EoUser user;
	@Column
	@Comment("排序")
	private int sort;
	
	@Column
	@Comment("等级")
	private int star;
	@Column
	@Comment("创建时间")
	private Date creatTime;
	@Column
	@Comment("状态")
	private String status;
	@Column
	@Comment("站点id")
	private String cmsSiteId;
	

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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
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

	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
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
	/*	public EoUser getUser() {
		return user;
	}
	public void setUser(EoUser user) {
		this.user = user;
	}*/
	public int getOrder() {
		return sort;
	}
	public void setOrder(int order) {
		this.sort = order;
	}
	public int getLevel() {
		return star;
	}
	public void setLevel(int level) {
		this.star = level;
	}
	@Override
	public String getId() {
		return storeId;
	}
	@Override
	public void setId(String id) {
		this.storeId = id;
		
	}
	public EmlUser getUser() {
		return user;
	}
	public void setUser(EmlUser user) {
		this.user = user;
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
		EmlStore other = (EmlStore) obj;
		if (storeId == null) {
			if (other.storeId != null)
				return false;
		} else if (!storeId.equals(other.storeId))
			return false;
		return true;
	}
	
	
}
