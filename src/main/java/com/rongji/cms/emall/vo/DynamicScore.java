package com.rongji.cms.emall.vo;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;

/**
 * 动态评分
 * 
 * @author zouhaoning
 * 
 */
public class DynamicScore extends Module {


	@Expose public int description;//描述
	@Expose public int goods;//商品
	@Expose public int service;//服务
	@Expose public int logistics;//物流
	@Expose public String createTime;
	
	public int getDescription() {
		return description;
	}
	public void setDescription(int description) {
		this.description = description;
	}
	public int getGoods() {
		return goods;
	}
	public void setGoods(int goods) {
		this.goods = goods;
	}
	public int getService() {
		return service;
	}
	public void setService(int service) {
		this.service = service;
	}
	public int getLogistics() {
		return logistics;
	}
	public void setLogistics(int logistics) {
		this.logistics = logistics;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
