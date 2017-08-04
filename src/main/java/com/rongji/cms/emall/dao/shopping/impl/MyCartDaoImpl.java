package com.rongji.cms.emall.dao.shopping.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.shopping.MyCartDao;
import com.rongji.cms.emall.entity.EmlMyCart;
import com.rongji.cms.emall.entity.EmlMyCart_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class MyCartDaoImpl extends CommonDaoImpl<EmlMyCart> implements MyCartDao {

	public List<EmlMyCart> getMyCartsByUserId(final String userId) {
		List<EmlMyCart> myCartsList =findAll(new Specification<EmlMyCart>() {
			@Override
			public Predicate toPredicate(Root<EmlMyCart> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Predicate p1 = cb.equal(root.get(EmlMyCart_.userId), userId);
				query.where(p1);		
				return null;
			}			
		});
		
		return myCartsList;
	}

	@Override
	public EmlMyCart getMyCartByGoodsId(final String goodsId,final String userId,final String skuId) {
		EmlMyCart myCart =findOne(new Specification<EmlMyCart>() {
			@Override
			public Predicate toPredicate(Root<EmlMyCart> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Predicate p1 = cb.equal(root.get(EmlMyCart_.goodsId), goodsId);
				Predicate p2 = cb.equal(root.get(EmlMyCart_.userId), userId);
				Predicate p3 = cb.equal(root.get(EmlMyCart_.skuId), skuId);
				query.where(p1,p2,p3);		
				return null;
			}			
		});		
		return myCart;
	}


	
}
