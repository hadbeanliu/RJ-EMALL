package com.rongji.cms.emall.vo;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;

/**
 * 动态
 * 
 * @author zouhaoning
 * 
 */
public class Dynamic extends Module {

	@Expose public String score;
	@Expose public String comment;
	@Expose public String sunOrder;
	@Expose public String buyInfo;
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSunOrder() {
		return sunOrder;
	}
	public void setSunOrder(String sunOrder) {
		this.sunOrder = sunOrder;
	}
	public String getBuyInfo() {
		return buyInfo;
	}
	public void setBuyInfo(String buyInfo) {
		this.buyInfo = buyInfo;
	}	
}

