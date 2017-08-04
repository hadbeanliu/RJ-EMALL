package com.rongji.cms.emall.vo;

import com.rongji.cms.webservice.domain.emall.WsGoods;
import com.rongji.rjskeleton.support.id.Sid;

public class Goods extends WsGoods implements Sid {
	private String id;
	
	public String getId() {
		return getGoodsId();
	}
	
	public void setId(String id) {
		setGoodsId(id);
	}
	
}
