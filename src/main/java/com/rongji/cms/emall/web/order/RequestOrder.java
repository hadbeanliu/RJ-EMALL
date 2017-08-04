package com.rongji.cms.emall.web.order;

import java.util.List;


public class RequestOrder {
	
	private String addressId;
	private List<OrderInfo> info;
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public List<OrderInfo> getInfo() {
		return info;
	}
	public void setInfo(List<OrderInfo> info) {
		this.info = info;
	}

}
