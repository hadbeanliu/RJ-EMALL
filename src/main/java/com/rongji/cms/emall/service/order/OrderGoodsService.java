package com.rongji.cms.emall.service.order;

import java.util.List;

import com.rongji.cms.emall.entity.EmlOrderGoods;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

public interface OrderGoodsService extends CommonService<OrderGoods, EmlOrderGoods> {
	
	/**
	 * 根据订单编号获取订单带条件过滤查询分页
	 * 
	 * @param orderNo 订单编号非空
	 * @param metadata 分页查询信息 非空
	 * @return 如果orderNo或者metadata为{@literal null}，则返回null
	 */
	public PageEntity<OrderGoods> getByDatatables(String orderNo, DatatablesMetadata metadata);
	
	public List<OrderGoods> getGoodsByOrder(String orderId);
	
	public int deleteByOrderId(String orderId);
	
}
