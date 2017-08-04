package com.rongji.cms.emall.dao.shopping;

import java.util.List;

import com.rongji.cms.emall.entity.EmlMyCart;
import com.rongji.rjskeleton.dao.CommonDao;

public interface MyCartDao extends CommonDao<EmlMyCart> {
	/**
	 * 根据用户ID获取添加到店铺ID
	 * 
	 * @param userId
	 * @return
	 */
	public List<EmlMyCart> getMyCartsByUserId(String userId);
	/**
	 * 获取购物车记录信息
	 * @param goodsId
	 * @param userId
	 * @param inventorySearchId
	 * @return
	 */
	public EmlMyCart getMyCartByGoodsId(String goodsId,String userId,String inventorySearchId);
}
