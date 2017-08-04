package com.rongji.cms.emall.vo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.id.Sid;

public class FreightTemplate implements Sid {
	private String id;
	private Store store;
	
	@NotBlank
	private String name;
	@NotBlank
	private String status;
	private String remark;
	private List<CarryMode> carryMode;
	
	public FreightTemplate(){}
	public FreightTemplate(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarryMode> getCarryMode() {
		return carryMode;
	}
	public void setCarryMode(List<CarryMode> carryMode) {
		this.carryMode = carryMode;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
		FreightTemplate other = (FreightTemplate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
