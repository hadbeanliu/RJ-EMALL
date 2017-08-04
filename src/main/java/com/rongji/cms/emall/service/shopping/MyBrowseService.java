package com.rongji.cms.emall.service.shopping;

import java.util.List;

import com.rongji.cms.emall.entity.EmlMyBrowse;
import com.rongji.cms.emall.vo.MyBrowse;
import com.rongji.rjskeleton.service.CommonService;

public interface MyBrowseService extends CommonService<MyBrowse, EmlMyBrowse> {
//	/**
//	 * 保存浏览的商品信息到我的浏览表
//	 * @param userId
//	 * @param goodsId
//	 * @param goodsCategory
//	 */
//	void saveMyBrowse(String userId, String goodsId, String goodsCategory);
	/**
	 * 获得浏览表数据
	 * @param userId 用户编号
	 * @return
	 */
	List<MyBrowse> getMyBrowseByUserId(String userId);

	List<EmlMyBrowse> getMyBrowse(String goodsId, String userId);
}
