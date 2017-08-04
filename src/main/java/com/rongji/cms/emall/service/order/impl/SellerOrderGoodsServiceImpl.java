package com.rongji.cms.emall.service.order.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.order.SellerOrderGoodsDao;
import com.rongji.cms.emall.entity.EmlOrder;
import com.rongji.cms.emall.entity.EmlOrderGoods;
import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.entity.EmlStore_;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.order.SellerOrderGoodsService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class SellerOrderGoodsServiceImpl extends CommonServiceImpl<OrderGoods, EmlOrderGoods, SellerOrderGoodsDao> implements SellerOrderGoodsService{

	@Autowired
	private OrderService orderService;
	
	@Override
	protected EmlOrderGoods createEntity() {
		return new EmlOrderGoods();
	}

	@Override
	protected OrderGoods createVo() {
		return new OrderGoods();
	}
	

	@Override
	public EmlOrderGoods convertToEntity(OrderGoods vo) {
		EmlOrderGoods entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "order");
		EmlOrder order = orderService.convertToEntity(vo.getOrder());
		entity.setOrder(order);
		return entity;
	}

	@Override
	public OrderGoods convertToVo(EmlOrderGoods entity) {
		OrderGoods vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "order");
		Order order = orderService.convertToVo(entity.getOrder());
		vo.setOrder(order);
		return vo;
	}

	@Override
	public PageEntity<OrderGoods> getSoldGoodsByStoreId(final String storeId,
			final DatatablesMetadata metadata) {
		return getByDatatables(metadata, new Specification<EmlOrderGoods>() {
			@Override
			public Predicate toPredicate(Root<EmlOrderGoods> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate orderEq = cb.equal(root.get(EmlOrderGoods_.order).get(EmlOrder_.store).get(EmlStore_.storeId), storeId);
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


}
