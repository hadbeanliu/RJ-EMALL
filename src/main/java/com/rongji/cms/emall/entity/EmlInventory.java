package com.rongji.cms.emall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name = TablePrefix.NAME + "INVENTORY")

@Comment("库存表")
public class EmlInventory implements Sid{
	@Id
	private String inventoryId;
	@Column
	@Comment("搜索id")
	private String skuId;
	@Column
	@Comment("库存数")
	private int inventory;
	@Column
	@Comment("商品id")
	private String goodsId;
	@Column
	@Comment("冗余")
	private String  extJson;
	@Column
	@Comment("价格")
	private String  goodsPrice;
	@Column
	@Comment("中文名")
	private String  cName;
	
	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	@Override
	public String getId() {
		
		return this.inventoryId;
	}

	@Override
	public void setId(String id) {

		this.inventoryId=id;
		
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}



	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getExtJson() {
		return extJson;
	}

	public void setExtJson(String extJson) {
		this.extJson = extJson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((inventoryId == null) ? 0 : inventoryId.hashCode());
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
		EmlInventory other = (EmlInventory) obj;
		if (inventoryId == null) {
			if (other.inventoryId != null)
				return false;
		} else if (!inventoryId.equals(other.inventoryId))
			return false;
		return true;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	

}
