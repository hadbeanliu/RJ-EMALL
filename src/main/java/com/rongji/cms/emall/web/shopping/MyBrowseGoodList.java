package com.rongji.cms.emall.web.shopping;

import java.util.Date;

import com.rongji.cms.emall.vo.Goods;

/**
 *  我的浏览商品列表pojo
 * 
 * @author lzm
 * 
 */
public class MyBrowseGoodList {

	private Goods goods;
	private Date browseDate;
	
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Date getBrowseDate() {
		return browseDate;
	}
	public void setBrowseDate(Date browseDate) {
		this.browseDate = browseDate;
	}
	
	public MyBrowseGoodList() {
		
	}
	
}

