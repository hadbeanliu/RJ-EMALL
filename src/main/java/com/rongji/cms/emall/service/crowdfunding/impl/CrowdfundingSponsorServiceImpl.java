package com.rongji.cms.emall.service.crowdfunding.impl;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.crowdfunding.CrowdfundingSponsorDao;
import com.rongji.cms.emall.entity.EmlCrowdfundingSponsor;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingSponsorService;
import com.rongji.cms.emall.vo.CrowdfundingSponsor;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class CrowdfundingSponsorServiceImpl extends CommonServiceImpl<CrowdfundingSponsor, EmlCrowdfundingSponsor, CrowdfundingSponsorDao> implements CrowdfundingSponsorService {

	@Override
	protected CrowdfundingSponsor createVo() {
		return new CrowdfundingSponsor();
	}

	@Override
	protected EmlCrowdfundingSponsor createEntity() {
		return new EmlCrowdfundingSponsor();
	}

	@Override
	public CrowdfundingSponsor convertToVo(EmlCrowdfundingSponsor entity) {	
		CrowdfundingSponsor vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "sponsorHeadFile","otherInformationFile");
		return vo;
	}

	@Override
	public EmlCrowdfundingSponsor convertToEntity(CrowdfundingSponsor vo) {		
		EmlCrowdfundingSponsor entity=createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "sponsorHeadFile","otherInformationFile");
		return entity;
	}
}
