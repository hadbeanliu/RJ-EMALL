package com.rongji.cms.emall.service.redis;

import com.rongji.rjskeleton.support.id.Sid;

public class RedisVo implements Sid {
	
	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
