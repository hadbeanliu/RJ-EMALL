package com.rongji.cms.emall.service.examine;

import java.util.List;

import com.rongji.cms.emall.entity.EmlExamine;
import com.rongji.cms.emall.vo.Examine;
import com.rongji.rjskeleton.service.CommonService;

public interface ExamineService extends CommonService<Examine, EmlExamine> {
	/**
	 * 根据参数获得审核表数据
	 * @param userId 用户编号
	 * @param authentications 认证指数
	 * @return
	 */
	public List<EmlExamine> getExamine(String userId,String authentications);
	
	/**
	 * 根据参数获得审核表数据
	 * @param userId 用户编号
	 * @param status 审核状态
	 * @param authentications 认证指数
	 * @return
	 */
	public EmlExamine getOne(String userId,String status,String authentications);
}
