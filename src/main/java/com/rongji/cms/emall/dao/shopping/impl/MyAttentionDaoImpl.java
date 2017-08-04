package com.rongji.cms.emall.dao.shopping.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.shopping.MyAttentionDao;
import com.rongji.cms.emall.entity.EmlMyAttention;
import com.rongji.cms.emall.entity.EmlMyAttention_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class MyAttentionDaoImpl extends CommonDaoImpl<EmlMyAttention> implements MyAttentionDao {

	@Override
	public EmlMyAttention getMyAttentionByGoodsId(final String userId, final String goodsId) {
		List<EmlMyAttention> myAttentionList =findAll(new Specification<EmlMyAttention>() {
			@Override
			public Predicate toPredicate(Root<EmlMyAttention> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Predicate p1 = cb.equal(root.get(EmlMyAttention_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlMyAttention_.goodsId), goodsId);
				query.where(p1,p2);		
				return null;
			}			
		});
		if(myAttentionList.size() == 1) return myAttentionList.get(0);
		return null;
	}

	@Override
	public EmlMyAttention getMyAttentionByStoreId(final String userId, final String storeId) {
		List<EmlMyAttention> myAttentionList =findAll(new Specification<EmlMyAttention>() {
			@Override
			public Predicate toPredicate(Root<EmlMyAttention> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Predicate p1 = cb.equal(root.get(EmlMyAttention_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlMyAttention_.storeId), storeId);
				query.where(p1,p2);		
				return null;
			}			
		});
		if(myAttentionList.size() == 1) return myAttentionList.get(0);
		return null;
	}
	
}
