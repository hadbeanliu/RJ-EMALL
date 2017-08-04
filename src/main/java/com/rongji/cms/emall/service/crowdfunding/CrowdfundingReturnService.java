package com.rongji.cms.emall.service.crowdfunding;

import java.util.List;

import com.rongji.cms.emall.entity.EmlCrowdfundingReturn;
import com.rongji.cms.emall.vo.CrowdfundingReturn;
import com.rongji.rjskeleton.service.CommonService;

/**
 * 众筹回报
 * @author Administrator
 *
 */
public interface CrowdfundingReturnService extends CommonService<CrowdfundingReturn, EmlCrowdfundingReturn> {

	/**
	 * 获取众筹ID下的所有回报信息
	 * @param crowdfundingId 众筹ID
	 * @return
	 */
	public List<CrowdfundingReturn> getCrowdfundingReturn(String crowdfundingId);
}
