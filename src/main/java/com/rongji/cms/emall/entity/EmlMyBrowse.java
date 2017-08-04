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
@Table(name= TablePrefix.NAME + "MY_BROWSE")
public class EmlMyBrowse implements Sid {
	
	@Id
	@Comment("浏览ID")
	@Column(name="BROWSE_ID")
	private String id;
    
	@Comment("商品ID")
	private String goodsId;
	
	@Comment("用户ID")
	private String userId; 
	
	@Comment("创建时间")
	private Date createTime;
	
	@Comment("商品分类")
	private String goodsCategory;

	@Comment("每天浏览的宝贝数量")
	private int perNum;
	
	@Comment("判断是否为当前页最后一项")
	private String currLast;
	
	public String getCurrLast() {
		return currLast;
	}
	public void setCurrLast(String currLast) {
		this.currLast = currLast;
	}
	
	public int getPerNum() {
		return perNum;
	}

	public void setPerNum(int perNum) {
		this.perNum = perNum;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		EmlMyBrowse other = (EmlMyBrowse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
