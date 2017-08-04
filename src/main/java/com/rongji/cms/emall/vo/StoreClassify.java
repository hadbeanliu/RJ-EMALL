package com.rongji.cms.emall.vo;

import javax.persistence.Column;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

public class StoreClassify implements Sid{

	private Store store;
	@NotBlank
	private String storeClassifyId;
	@NotBlank
	private String storeClassifyName;
	@NotBlank
	private String pId;
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getStoreClassifyId() {
		return storeClassifyId;
	}
	public void setStoreClassifyId(String storeClassifyId) {
		this.storeClassifyId = storeClassifyId;
	}
	public String getStoreClassifyName() {
		return storeClassifyName;
	}
	public void setStoreClassifyName(String storeClassifyName) {
		this.storeClassifyName = storeClassifyName;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((storeClassifyId == null) ? 0 : storeClassifyId.hashCode());
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
		StoreClassify other = (StoreClassify) obj;
		if (storeClassifyId == null) {
			if (other.storeClassifyId != null)
				return false;
		} else if (!storeClassifyId.equals(other.storeClassifyId))
			return false;
		return true;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.storeClassifyId;
	}
	@Override
	public void setId(String id) {
		this.storeClassifyId=id;
		
	}
	
}
