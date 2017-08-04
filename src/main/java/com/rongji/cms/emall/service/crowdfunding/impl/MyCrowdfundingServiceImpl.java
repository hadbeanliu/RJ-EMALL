package com.rongji.cms.emall.service.crowdfunding.impl;

import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.crowdfunding.MyCrowdfundingDao;
import com.rongji.cms.emall.entity.EmlMyCrowdfunding;
import com.rongji.cms.emall.service.crowdfunding.MyCrowdfundingService;
import com.rongji.cms.emall.vo.MyCrowdfunding;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class MyCrowdfundingServiceImpl extends CommonServiceImpl<MyCrowdfunding, EmlMyCrowdfunding, MyCrowdfundingDao> implements MyCrowdfundingService {

	@Override
	protected EmlMyCrowdfunding createEntity() {
		return new EmlMyCrowdfunding();
	}

	@Override
	protected MyCrowdfunding createVo() {
		return new MyCrowdfunding();
	}

}
