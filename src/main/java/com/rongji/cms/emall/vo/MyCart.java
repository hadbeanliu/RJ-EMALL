package com.rongji.cms.emall.vo;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.id.Sid;

public class MyCart implements Sid {
	
	private String id;
	
	@NotBlank
	private Goods goods;
	
	@NotBlank
	private Store store;
	
	@NotBlank @Length(min=16,max=16)
	private String userId;
	
	@NotBlank @Length(min=1)
	private int buyNumber;
	
	private Date createTime;	
	
	@NotBlank
	private String skuId;
	
	@NotBlank
	private String skuInfo;
	
	private Inventory sku;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getSkuInfo() {
		return skuInfo;
	}

	public void setSkuInfo(String skuInfo) {
		this.skuInfo = skuInfo;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Inventory getSku() {
		return sku;
	}

	public void setSku(Inventory sku) {
		this.sku = sku;
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
		MyCart other = (MyCart) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
