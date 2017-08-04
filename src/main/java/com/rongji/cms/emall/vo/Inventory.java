package com.rongji.cms.emall.vo;

import com.rongji.rjskeleton.support.id.Sid;

public class Inventory implements Sid{
	private String inventoryId;
	private String skuId;
	private int inventory;
	private String goodsId;
	private String  extJson;
	private String  goodsPrice;
	private String  cName;
	
	
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
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
	public String getId() {
	
		return this.inventoryId;
	}
	@Override
	public void setId(String id) {
	
		
		this.inventoryId=id;
	}
	
}
