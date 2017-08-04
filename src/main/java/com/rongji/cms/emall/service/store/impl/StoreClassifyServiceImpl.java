package com.rongji.cms.emall.service.store.impl;

import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.store.StoreClassifyDao;
import com.rongji.cms.emall.entity.EmlStoreClassify;
import com.rongji.cms.emall.service.store.StoreClassifyService;
import com.rongji.cms.emall.vo.StoreClassify;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class StoreClassifyServiceImpl extends CommonServiceImpl<StoreClassify,EmlStoreClassify,StoreClassifyDao> implements StoreClassifyService{

	@Override
	protected StoreClassify createVo() {
		
		return new StoreClassify();
	}

	@Override
	protected EmlStoreClassify createEntity() {
	
		return new EmlStoreClassify();
	}

}
