package com.rongji.cms.emall.dao.shopping;

import java.util.List;

import com.rongji.cms.emall.entity.EmlMyBrowse;
import com.rongji.rjskeleton.dao.CommonDao;

public interface MyBrowseDao extends CommonDao<EmlMyBrowse> {
	/**
	 * 通过商品编号和用户编号获得我的浏览数据
	 * @param goodsId
	 * @param userId
	 * @return MyBrowse对象
	 */
	List<EmlMyBrowse> getMyBrowse(String goodsId,String userId);
	/**
	 * 获得浏览表数据
	 * @param userId 用户编号
	 * @return
	 */
	List<EmlMyBrowse> getMyBrowseByUserId(String userId);
}
