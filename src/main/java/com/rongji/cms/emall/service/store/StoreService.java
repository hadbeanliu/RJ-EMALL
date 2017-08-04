package com.rongji.cms.emall.service.store;

import com.rongji.cms.emall.entity.EmlStore;
import com.rongji.cms.emall.vo.Goods;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonService;

public interface StoreService extends CommonService<Store,EmlStore>{
	/**
	 * 根据用户获取对应店铺
	 * 
	 * @param userId    用户id 
	 * 
	 * 
	 * @return 店铺
	 */
	public Store getStoreByUserId(String userId);
	/**
	 * 储存店铺
	 * 
	 * @param Store    店铺对象 
	 * 
	 * 
	 * @return 店铺
	 */
	public Store saveStore(Store vo);
	/**
	 * 更新店铺
	 * 
	 * @param Store    店铺对象 
	 * 
	 * 
	 * @return 店铺
	 */
	Store updateStore(Store vo);
	/**
	 * 根据商品id获取对应商品详情(不含正文)
	 * 
	 * @param goodsId    商品id
	 * 
	 * 
	 * @return 商品
	 */
	public Goods getSimpleGoodsByGoodsId(String goodsId);
	/**
	 * 根据商品id获取对应商品详情(含正文)
	 * 
	 * @param goodsId    商品id
	 * 
	 * 
	 * @return 商品
	 */
	public Goods getGoodsByGoodsId(String goodsId);
	/**
	 * 根据商品id获取对应商铺
	 * 
	 * @param goodsId   商品id
	 * 
	 * 
	 * @return 商铺
	 */
	public Store getStoreByGoodsId(String goodsId);
}
