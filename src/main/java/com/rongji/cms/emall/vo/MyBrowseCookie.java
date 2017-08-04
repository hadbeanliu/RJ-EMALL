package com.rongji.cms.emall.vo;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;

/**
 *  我的浏览cookie
 * 
 * @author zouhaoning
 * 
 */
public class MyBrowseCookie extends Module {

	@Expose public String goodsId;
	@Expose public String userId;
	@Expose public Date createTime;
	@Expose public Date endTime;
	@Expose public int times;
	@Expose public String residenceTime;
	
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
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}	
	public String getResidenceTime() {
		return residenceTime;
	}
	public void setResidenceTime(String residenceTime) {
		this.residenceTime = residenceTime;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}	
	
}

