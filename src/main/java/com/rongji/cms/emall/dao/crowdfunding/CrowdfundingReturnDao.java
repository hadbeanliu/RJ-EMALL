package com.rongji.cms.emall.dao.crowdfunding;

import java.util.List;

import com.rongji.cms.emall.entity.EmlCrowdfundingReturn;
import com.rongji.rjskeleton.dao.CommonDao;

public interface CrowdfundingReturnDao extends CommonDao<EmlCrowdfundingReturn> {
     
	public List<EmlCrowdfundingReturn> getCrowdfundingReturn(final String crowdfundingId);
	
}