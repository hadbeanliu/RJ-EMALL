package com.rongji.cms.emall.service.activity.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.activity.MyBondDao;
import com.rongji.cms.emall.entity.EmlMyBond;
import com.rongji.cms.emall.entity.EmlMyBond_;
import com.rongji.cms.emall.service.activity.MyBondService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.MyBond;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class MyBondServiceImpl extends CommonServiceImpl<MyBond, EmlMyBond,MyBondDao> implements MyBondService {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	protected EmlMyBond createEntity() {		
		return new EmlMyBond();
	}
	
	@Override
	protected MyBond createVo() {
		return new MyBond();
	}

	@Override
	public MyBond convertToVo(EmlMyBond entity) {
		MyBond vo=createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true,"goods","order");
		if(entity!=null){	
			vo.setGoods(storeService.getGoodsByGoodsId(entity.getGoodsId()));	
			if(entity.getOrderId()!=null){
				vo.setOrder(orderService.getOne(entity.getOrderId()));
			}
		}			
		return vo;
	}

	@Override
	public EmlMyBond convertToEntity(MyBond vo) {
		EmlMyBond entity=createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true,"goods","order");
		if(vo.getGoods()!=null){
			entity.setGoodsId(vo.getGoods().getGoodsId());
		}
		if(vo.getOrder()!=null){
			entity.setOrderId(vo.getOrder().getId());
		}
		return entity;
	}

	@Override
	public MyBond getMyBond(final String userId, final String goodsId) {
		EmlMyBond myBond = getDao().findOne(new Specification<EmlMyBond>() {
			@Override
			public Predicate toPredicate(Root<EmlMyBond> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				Predicate p1 = cb.equal(root.get(EmlMyBond_.goodsId), goodsId);
				Predicate p2 = cb.equal(root.get(EmlMyBond_.userId), userId);
				
				return cb.and(p1, p2);
			}
		});
		return myBond == null ? null : convertToVo(myBond);
	}
}
