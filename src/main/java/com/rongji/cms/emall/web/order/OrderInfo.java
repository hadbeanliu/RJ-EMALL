package com.rongji.cms.emall.web.order;

import java.util.List;

import com.rongji.cms.emall.vo.OrderGoods;

public class OrderInfo {
	
	private String storeId;
	private List<OrderGoods> goods;
	private String remark;
	private String deliveryId;
	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public List<OrderGoods> getGoods() {
		return goods;
	}
	public void setGoods(List<OrderGoods> goods) {
		this.goods = goods;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

}
