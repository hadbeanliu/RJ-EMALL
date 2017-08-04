package com.rongji.cms.emall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name= TablePrefix.NAME + "CARRY_MODE")
public class EmlCarryMode implements Sid {
	@Id
	@Column(length=16, name="id")
	@Comment("运费方式ID")
	private String id;
	
	@Column(name="AREAS")
	@Comment("运送地区")
	private String areas;
	
	@Column(name="AREAS_CODES")
	@Comment("地区区号")
	private String areascodes;
	
	@Column(name="SERVICE_TYPE")
	@Comment("运送方式")
	private String serviceType;
	
	@Column(name="FEE")
	@Comment("首费")
	private String fee;
	
	@Column(name="ADDED_FEE")
	@Comment("续费")
	private String addedFee;
	
	@Column(name="IS_DEFAULT")
	@Comment("默认")
	private String isDefault;
	
	@ManyToOne
	@JoinColumn(name="TEMP_ID")
	private EmlFreightTemplate emlFreightTemplate;
	
	public EmlCarryMode(){}
	public EmlCarryMode(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public String getAreascodes() {
		return areascodes;
	}
	public void setAreascodes(String areascodes) {
		this.areascodes = areascodes;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getAddedFee() {
		return addedFee;
	}
	public void setAddedFee(String addedFee) {
		this.addedFee = addedFee;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public EmlFreightTemplate getEmlFreightTemplate() {
		return emlFreightTemplate;
	}
	public void setEmlFreightTemplate(EmlFreightTemplate emlFreightTemplate) {
		this.emlFreightTemplate = emlFreightTemplate;
	}
	
}
