package com.rongji.cms.emall.dao.order;

import com.rongji.cms.emall.entity.EmlOrderGoods;
import com.rongji.rjskeleton.dao.CommonDao;

public interface OrderGoodsDao extends CommonDao<EmlOrderGoods> {

	public int deleteByOrderId(String orderId);
	
}
