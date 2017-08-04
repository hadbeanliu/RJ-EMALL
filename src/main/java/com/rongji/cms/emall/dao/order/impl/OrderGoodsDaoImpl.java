package com.rongji.cms.emall.dao.order.impl;

import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.order.OrderGoodsDao;
import com.rongji.cms.emall.entity.EmlOrderGoods;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class OrderGoodsDaoImpl extends CommonDaoImpl<EmlOrderGoods> implements OrderGoodsDao {

	@Override
	public int deleteByOrderId(String orderId) {
		return executeUpdate("delete from " + getEntityName() + " t where t.order.id = ?0", orderId);
	}
	
}
