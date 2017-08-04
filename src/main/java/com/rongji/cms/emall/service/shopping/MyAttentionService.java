package com.rongji.cms.emall.service.shopping;

import com.rongji.cms.emall.entity.EmlMyAttention;
import com.rongji.cms.emall.vo.MyAttention;
import com.rongji.rjskeleton.service.CommonService;


public interface MyAttentionService extends CommonService<MyAttention, EmlMyAttention> {
	/**
	 * 通过用户和商品编号获取我的关注表
	 * @param userId 用户编号
	 * @param goodsId 商品编号
	 * @return
	 */
	EmlMyAttention getMyAttentionByGoodsId(String userId, String goodsId);
	/**
	 * 通过用户和店铺编号获取我的关注表
	 * @param userId 用户编号
	 * @param store 店铺编号
	 * @return
	 */
	EmlMyAttention getMyAttentionByStoreId(String userId, String storeId);

}
