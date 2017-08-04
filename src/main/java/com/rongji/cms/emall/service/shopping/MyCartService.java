package com.rongji.cms.emall.service.shopping;

import java.util.List;

import com.rongji.cms.emall.entity.EmlMyCart;
import com.rongji.cms.emall.vo.MyCart;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;


public interface MyCartService extends CommonService<MyCart, EmlMyCart> {
	
	/**
	 * 根据用户ID获取添加到店铺ID
	 * 
	 * @param userId
	 * @return
	 */
	public List<MyCart> getShopIdsByUserId(String userId);
	
	/**
	 * 获取购物车记录信息
	 * 
	 * @param goodsId 商品ID
	 * @param userId 用户ID
	 * @param inventorySearchId  规格ID
	 * @return
	 */
	public MyCart getMyCart(String goodsId,String userId,String inventorySearchId);
	
	/**
	 * 根据用户ID获取购物车信息带条件过滤查询分页
	 * 
	 * @param userId 用户ID非空
	 * @param storeId 店铺ID非空
	 * @param metadata 分页查询信息 非空
	 * @return 如果userId或者metadata为{@literal null}，则返回null
	 */
	public PageEntity<MyCart> getByDatatables(String userId, DatatablesMetadata metadata);
	
	/**
	 * 删除或更新购物车记录
	 * @param goodsId 商品ID
	 * @param userId 用户ID
	 * @param skuId 规格ID
	 * @param num 加入数量
	 */
	public void deleteOrUpdateMyCart(String goodsId, String userId,String skuId,int num);
	
	/**
	 * 根据购物车ID删除或更新购物车记录
	 * @param cartId
	 * @param num
	 */
	public void deleteOrUpdateMyCartByCartId(String cartId,int num);
}
