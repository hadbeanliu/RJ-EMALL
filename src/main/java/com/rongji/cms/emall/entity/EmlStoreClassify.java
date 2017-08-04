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
@Table(name = TablePrefix.NAME + "STORE_CLASSIFY")

@Comment("商铺分类表表")
public class EmlStoreClassify implements Sid{
	
	
	@ManyToOne@JoinColumn(name="storeId")
	private EmlStore emlStore;
	@Id
	private String storeClassifyId;
	@Column
	@Comment("分类名称")
	private String storeClassifyName;
	@Column
	@Comment("父id")
	private String pId;
	@Override
	public String getId() {
	
		return storeClassifyId;
	}
	@Override
	public void setId(String id) {
		this.storeClassifyId = id;
		
	}
	public String getStoreId() {
		return storeClassifyId;
	}
	

	public EmlStore getEmlStore() {
		return emlStore;
	}
	public void setEmlStore(EmlStore emlStore) {
		this.emlStore = emlStore;
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
		EmlStoreClassify other = (EmlStoreClassify) obj;
		if (storeClassifyId == null) {
			if (other.storeClassifyId != null)
				return false;
		} else if (!storeClassifyId.equals(other.storeClassifyId))
			return false;
		return true;
	}
	
}
