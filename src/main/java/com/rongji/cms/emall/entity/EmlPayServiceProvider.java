package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

/**
 * 支付服务提供商表
 * 
 * @author rjf
 *
 */
@Entity
@Table(name= TablePrefix.NAME + "PAY_SERVICE_PROVIDER")
public class EmlPayServiceProvider implements Sid {
	
	@Id
	@Column
	private String id;
	
	/**
	 * 包含支付服务提供商和具体的服务类型
	 */
	@Column
	@Comment("支付方式")
	private String title;
	
	@Column
	@Comment("支付方式代号")
    private String code;
    
	@Column
    private String url;
	
	/**
	 * 配置支付的服务请求信息
	 */
	@Column
	@Comment("支付配置")
	private String config;
    
	@Column
    private String description;
    
	@Column
    private String logo;
    
	@Column
    private String pluginVersion;
    
	@Column
    private String status;
    
	@Column
    private Date createTime;
    
	@Column
    private Date modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
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

	public String getPluginVersion() {
		return pluginVersion;
	}

	public void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
		EmlPayServiceProvider other = (EmlPayServiceProvider) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
