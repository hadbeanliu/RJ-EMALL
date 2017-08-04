package com.rongji.cms.emall.dao.shopping.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.shopping.MyBrowseDao;
import com.rongji.cms.emall.entity.EmlMyBrowse;
import com.rongji.cms.emall.entity.EmlMyBrowse_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class MyBrowseDaoImpl extends CommonDaoImpl<EmlMyBrowse> implements MyBrowseDao {

	@Override
	public List<EmlMyBrowse> getMyBrowse(final String goodsId, final String userId) {
		List<EmlMyBrowse> myBrowsesList =findAll(new Specification<EmlMyBrowse>() {
			@Override
			public Predicate toPredicate(Root<EmlMyBrowse> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Predicate p1 = cb.equal(root.get(EmlMyBrowse_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlMyBrowse_.goodsId), goodsId);
				query.where(p1,p2);		
				return null;
			}			
		});
		return myBrowsesList;
	}

	@Override
	public List<EmlMyBrowse> getMyBrowseByUserId(final String userId) {
		List<EmlMyBrowse> myBrowsesList =findAll(new Specification<EmlMyBrowse>() {
			@Override
			public Predicate toPredicate(Root<EmlMyBrowse> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Predicate p1 = cb.equal(root.get(EmlMyBrowse_.userId), userId);
				query.where(p1);		
				return null;
			}			
		});
		return myBrowsesList;
	}
	
}
