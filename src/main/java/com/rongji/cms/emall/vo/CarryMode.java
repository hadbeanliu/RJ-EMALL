package com.rongji.cms.emall.vo;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.id.Sid;

public class CarryMode implements Sid {
	private String id;
	private String areas;
	private String areascodes;
	private String serviceType;
	@NotBlank
	@Digits(integer=6,fraction=2)
	private String fee;
	@NotBlank
	@Digits(integer=6,fraction=2)
	private String addedFee;
	private String isDefault;
	private FreightTemplate freightTemplate;
	
	public CarryMode(){}
	public CarryMode(String id) {
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
	public FreightTemplate getFreightTemplate() {
		return freightTemplate;
	}
	public void setFreightTemplate(FreightTemplate freightTemplate) {
		this.freightTemplate = freightTemplate;
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
		CarryMode other = (CarryMode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
