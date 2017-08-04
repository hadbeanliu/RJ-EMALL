package com.rongji.cms.emall.vo;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;

public class AuctionRecordList extends Module{
	
	@Expose public Double price;
	@Expose public String userId;	
	@Expose public String createTime;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}	
}
