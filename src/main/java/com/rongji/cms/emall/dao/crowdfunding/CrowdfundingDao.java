package com.rongji.cms.emall.dao.crowdfunding;


import com.rongji.cms.emall.entity.EmlCrowdfunding;
import com.rongji.rjskeleton.dao.CommonDao;

public interface CrowdfundingDao extends CommonDao<EmlCrowdfunding> {
	
	public EmlCrowdfunding getCrowdfunding(final String userId,final String crowdfundingId);
	
}
