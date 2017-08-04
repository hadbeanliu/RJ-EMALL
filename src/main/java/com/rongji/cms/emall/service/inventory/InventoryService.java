package com.rongji.cms.emall.service.inventory;

import java.util.List;

import com.rongji.cms.emall.entity.EmlInventory;
import com.rongji.cms.emall.vo.Inventory;
import com.rongji.cms.webservice.domain.emall.WsGoods;
import com.rongji.rjskeleton.service.CommonService;

public interface InventoryService extends CommonService<Inventory,EmlInventory>{
	/**
	 * 根据搜索id获取库存对象
	 * 
	 * @param skuId 搜索id
	 * 
	 * 
	 * @return Inventory
	 */
	public 	Inventory getInventoryByskuId(String skuId);
	 /**
	  * 根据商品id获取对应的库存列表
	  * @param goodsId 商品id
	  * @return List<Inventory>
	  */
	public 	List<Inventory> getInventoryByGoodsId(String goodsId);
	
	public 	Inventory addGoodsInventory(String json);
	/**
	 *  减少库存
	 * @param skuId
	 * @param num
	 * @return Inventory
	 */
	public 	Inventory sellInventory(String skuId,int num);
	/**
	 *  添加库存
	 * @param skuId
	 * @param num
	 * @return Inventory
	 */
	public 	Inventory addInventory(String skuId,int num);
/**
 *  库存总数
 * @param goodsId
 * @return long
 */
	public 	long sumInventory(String goodsId);
	/**
	 *  获取库存对象
	 * @param skuId
	 * @param goodsId
	 * @return Inventory
	 */
	public Inventory getOneInventory(String skuId,String goodsId);
	/**
	 *  获取规格对应中文名
	 * @param goodIds
	 * @param skuId
	 * @return
	 */
	String getNameByskuId(String goodIds,String skuId);
}
