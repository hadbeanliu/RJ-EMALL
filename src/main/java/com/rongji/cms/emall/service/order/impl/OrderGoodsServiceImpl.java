package com.rongji.cms.emall.service.order.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.order.OrderGoodsDao;
import com.rongji.cms.emall.entity.EmlOrder;
import com.rongji.cms.emall.entity.EmlOrderGoods;
import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class OrderGoodsServiceImpl extends CommonServiceImpl<OrderGoods, EmlOrderGoods, OrderGoodsDao> implements OrderGoodsService {
	
	@Autowired
	private OrderService orderService;

	@Override
	protected OrderGoods createVo() {
		return new OrderGoods();
	}

	@Override
	protected EmlOrderGoods createEntity() {
		return new EmlOrderGoods();
	}

	@Override
	public OrderGoods convertToVo(EmlOrderGoods entity) {
		OrderGoods vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "order");
		
		Order order = orderService.convertToVo(entity.getOrder());
//		Order order = convertToVo(orderService, entity.getOrder());
		
		vo.setOrder(order);
		return vo;
	}
	
	@Override
	public EmlOrderGoods convertToEntity(OrderGoods vo) {
		EmlOrderGoods entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "order");
		EmlOrder order = orderService.convertToEntity(vo.getOrder());
//		EmlOrder order = new EmlOrder(vo.getOrder().getId());
		entity.setOrder(order);
		return entity;
	}

	@Override
	public PageEntity<OrderGoods> getByDatatables(final String orderNo,
			final DatatablesMetadata metadata) {
		
		return getByDatatables(metadata, new Specification<EmlOrderGoods>() {
			@Override
			public Predicate toPredicate(Root<EmlOrderGoods> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				
				// 多表查询方法 1
//				Join<EmlOrderGoods, EmlOrder> orderJoin = root.join(EmlOrderGoods_.order);
//				Predicate orderEq = cb.equal(orderJoin.get(EmlOrder_.orderNo), orderNo);
				
				// 多表查询方法 2
				Predicate orderEq = cb.equal(root.get(EmlOrderGoods_.order).get(EmlOrder_.orderNo), orderNo);
				
				// 对应的sql语句
				/*
				select emlordergo0_ as col_0_0_
				  from eml_order_goods emlordergo0_
				 inner join eml_order emlorder1_
				    on emlordergo0_.order_id = emlorder1_.id
				 where emlorder1_.order_no = ?
				 */
				
				
				Predicate[] where = createDatatablesQuery(metadata, root, query, cb);
				if (where != null && where.length > 0) {
					Predicate[] w = new Predicate[where.length+1];
					w[0] = orderEq;
					for (int i = 0; i < where.length; i++) {
						w[i+1] = where[i];
					}
					query.where(w);
					
				} else {
					query.where(orderEq);
				}
				
				return null;
			}
		});
		
	}

	@Override
	public List<OrderGoods> getGoodsByOrder(final String orderId) {
		List<EmlOrderGoods> goods = getDao().findAll(new Specification<EmlOrderGoods>() {
			@Override
			public Predicate toPredicate(Root<EmlOrderGoods> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(EmlOrderGoods_.order).get(EmlOrder_.id), orderId);
			}
		});
		return convertToVos(goods);
	}

	@Transactional
	@Override
	public int deleteByOrderId(String orderId) {
		if (orderId == null || orderId.trim().isEmpty()) {
			return 0;
		}
		return getDao().deleteByOrderId(orderId);
	}
	
}
