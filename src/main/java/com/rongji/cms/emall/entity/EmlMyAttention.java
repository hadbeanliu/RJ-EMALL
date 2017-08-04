package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name= TablePrefix.NAME + "MY_ATTENTION")
public class EmlMyAttention implements Sid {
	
	@Id
	@Column(name="ATTENTION_ID")
	private String id;
	
	@Comment("关注类型")
	private String attentionType;
	
	@Comment("用户ID")
	private String userId;
	
	@Comment("商品ID")
	private String goodsId;
	
	@Comment("店铺ID")	
	private String storeId;
	
	@Comment("创建时间")
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}	

	public String getAttentionType() {
		return attentionType;
	}

	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		EmlMyAttention other = (EmlMyAttention) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
