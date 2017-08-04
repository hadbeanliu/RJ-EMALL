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
@Table(name= TablePrefix.NAME + "FREIGHT_TEMPLATE")
public class EmlFreightTemplate implements Sid {
	@Id
	@Column(length=16, name="id")
	@Comment("运费模块ID")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="STORE_ID")
	private EmlStore store;
	
	@Column(name="FREIGHT_NAME")
	@Comment("模板名称")
	private String name;
	
	@Column(name="STATUS")
	@Comment("是否启用")
	private String status;
	
	@Column(name="REMARK")
	@Comment("运费说明")
	private String remark;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EmlFreightTemplate(){}
	
	public EmlFreightTemplate(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public EmlStore getStore() {
		return store;
	}

	public void setStore(EmlStore store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		EmlFreightTemplate other = (EmlFreightTemplate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
