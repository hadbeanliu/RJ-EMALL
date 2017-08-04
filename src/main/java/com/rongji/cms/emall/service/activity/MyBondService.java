package com.rongji.cms.emall.service.activity;

import com.rongji.cms.emall.entity.EmlMyBond;
import com.rongji.cms.emall.vo.MyBond;
import com.rongji.rjskeleton.service.CommonService;

public interface MyBondService extends CommonService<MyBond, EmlMyBond> {

	/**
	 * 获取保证金记录
	 * @param userId 用户ID
	 * @param goodsId 商品ID
	 * @return
	 */
	public MyBond getMyBond(String userId,String goodsId);
}
