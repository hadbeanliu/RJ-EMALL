package com.rongji.cms.emall.service.crowdfunding;


import com.rongji.cms.emall.entity.EmlCrowdfunding;
import com.rongji.cms.emall.vo.Crowdfunding;
import com.rongji.rjskeleton.service.CommonService;

/**
 * 众筹 
 * @author zhn
 *
 */
public interface CrowdfundingService extends CommonService<Crowdfunding, EmlCrowdfunding> {

	/**
	 * 是否存在众筹信息
     * @param userId 用户ID
	 * @param crowdfundingId 众筹ID
	 * @return
	 */
	public Boolean getCrowdfunding(final String userId,final String crowdfundingId);
	
}
