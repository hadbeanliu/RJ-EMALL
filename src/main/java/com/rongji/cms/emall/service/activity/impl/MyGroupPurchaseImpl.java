package com.rongji.cms.emall.service.activity.impl;


import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.activity.MyGroupPurchaseDao;
import com.rongji.cms.emall.entity.EmlMyGroupPurchase;
import com.rongji.cms.emall.service.activity.MyGroupPurchaseService;
import com.rongji.cms.emall.vo.MyGroupPurchase;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class MyGroupPurchaseImpl extends CommonServiceImpl<MyGroupPurchase, EmlMyGroupPurchase, MyGroupPurchaseDao> implements MyGroupPurchaseService {

	@Override
	protected EmlMyGroupPurchase createEntity() {
		return new EmlMyGroupPurchase();
	}

	@Override
	protected MyGroupPurchase createVo() {
		return new MyGroupPurchase();
	}

}
