package com.rongji.cms.emall.service.logistic.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongji.cms.emall.dao.logistic.LogisticDao;
import com.rongji.cms.emall.entity.EmlLogistics;
import com.rongji.cms.emall.service.inventory.InventoryService;
import com.rongji.cms.emall.service.logistic.LogisticService;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.order.OrderStatus;
import com.rongji.cms.emall.vo.Logistics;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class LogisticServiceImpl extends CommonServiceImpl<Logistics, EmlLogistics, LogisticDao> implements LogisticService{
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderGoodsService orderGoodsService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Override
	@Transactional
	public Logistics sendGoods(String userId,Logistics vo) {
		String orderId=vo.getOrderId();
		vo.setCreatetime(new Date());
		EmlLogistics log = getDao().saveAndFlush(convertToEntity(vo));
		orderService.sendGoods(userId, orderId, convertToVo(log));
		return vo;
//		Order od=orderService.getOne(orderId);
//		if(od.getStatus().equals("2")){
//			vo.setCreatetime(new Date());
//			EmlLogistics log = getDao().saveAndFlush(convertToEntity(vo));
//			od.setStatus(OrderStatus.SEND);
//			od.setLogistics(convertToVo(log));
//			orderService.update(od);
//			return convertToVo(log);
//		}else{
//			return null;
//		}
	}

	@Override
	public Order getOrder(String orderId) {
		List<Logistics> logs=getAll();
		Logistics logistic=new Logistics();
		for(Logistics log : logs){
			if(log.getOrderId().equals(orderId)){
				logistic=log;
			}
		}
		Order order=orderService.getOne(orderId);
		List<OrderGoods> goods=orderGoodsService.getGoodsByOrder(orderId);
		for(OrderGoods gd : goods){
			gd.setSku(inventoryService.getNameByskuId(gd.getGoodsId(), gd.getSkuId()));
		}
		
		order.setLogistics(logistic);
		order.setGoods(goods);
		
		return order;
	}

}
