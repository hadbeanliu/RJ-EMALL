package com.rongji.cms.emall.dao.examine;

import java.util.List;

import com.rongji.cms.emall.entity.EmlExamine;
import com.rongji.rjskeleton.dao.CommonDao;

public interface ExamineDao extends CommonDao<EmlExamine> {
	/**
	 * 通过用户编号和认证指数获得审核表数据列表
	 * @param userId 用户编号
	 * @param authentications 认证指数
	 * @return
	 */
	public List<EmlExamine> getExamine(String userId,String authentications);

	/**
	 * 通过审核状态和认证指数获得审核表数据
	 * @param userId 用户编号
	 * @param status 审核状态
	 * @param authentications 认证指数
	 * @return
	 */
	public EmlExamine getOne(String userId,String status, String authentications);
}
