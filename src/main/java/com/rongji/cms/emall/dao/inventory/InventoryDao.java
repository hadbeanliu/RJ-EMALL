package com.rongji.cms.emall.dao.inventory;

import java.util.List;

import com.rongji.cms.emall.entity.EmlInventory;
import com.rongji.rjskeleton.dao.CommonDao;

public interface InventoryDao extends CommonDao<EmlInventory>{

	EmlInventory getInventoryByskuId(String skuId);

	long getsumByGoodsId(String goodsId);
	List<EmlInventory> getInventoryByGoodsId(String goodsId);

}
