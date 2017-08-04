package com.rongji.cms.emall.service.crowdfunding.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.rongji.cms.emall.dao.crowdfunding.CrowdfundingReturnDao;
import com.rongji.cms.emall.entity.EmlCrowdfundingReturn;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingReturnService;
import com.rongji.cms.emall.vo.CrowdfundingReturn;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class CrowdfundingReturnServiceImpl extends CommonServiceImpl<CrowdfundingReturn, EmlCrowdfundingReturn, CrowdfundingReturnDao> implements CrowdfundingReturnService {

	@Override
	protected EmlCrowdfundingReturn createEntity() {
		return new EmlCrowdfundingReturn();
	}

	@Override
	protected CrowdfundingReturn createVo() {
		return new CrowdfundingReturn();
	}

	@Override
	public CrowdfundingReturn convertToVo(EmlCrowdfundingReturn entity) {
		CrowdfundingReturn vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true,"returnPictureFile");		
		return vo;
	}

	@Override
	public EmlCrowdfundingReturn convertToEntity(CrowdfundingReturn vo) {
		EmlCrowdfundingReturn entity=createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true,"returnPictureFile");
		return entity;
	}

	@Override
	public List<CrowdfundingReturn> getCrowdfundingReturn(String crowdfundingId) {
		return convertToVos(getDao().getCrowdfundingReturn(crowdfundingId));		
	}
}
