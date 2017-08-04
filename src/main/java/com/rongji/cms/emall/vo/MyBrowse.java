package com.rongji.cms.emall.vo;

import java.util.Date;
import java.util.List;

import com.rongji.cms.emall.web.shopping.MyBrowseGoodList;
import com.rongji.rjskeleton.support.id.Sid;

public class MyBrowse implements Sid{
	
	private String id;	
	
//	private Goods goods;
	
	private List<MyBrowseGoodList> goodsList;
	
	private String userId;
	
	private Date createTime; 
	
	private String goodsCategory;
	
	private int perNum;
	
	private String currLast;
	
	public String getCurrLast() {
		return currLast;
	}
	public void setCurrLast(String currLast) {
		this.currLast = currLast;
	}
	public List<MyBrowseGoodList> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<MyBrowseGoodList> goodsList) {
		this.goodsList = goodsList;
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
//	public Goods getGoods() {
//		return goods;
//	}
//	public void setGoods(Goods goods) {
//		this.goods = goods;
//	}
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
		MyBrowse other = (MyBrowse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
