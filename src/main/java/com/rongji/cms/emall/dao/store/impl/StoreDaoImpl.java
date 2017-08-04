package com.rongji.cms.emall.dao.store.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.store.StoreDao;
import com.rongji.cms.emall.entity.EmlStore;
import com.rongji.cms.emall.entity.EmlStore_;
import com.rongji.cms.emall.entity.EmlUser_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;
@Repository
public class StoreDaoImpl extends CommonDaoImpl<EmlStore> implements StoreDao{

	@Override
	public EmlStore getStoreByUserId(final String userId) {
		EmlStore emlStore = findOne(new Specification<EmlStore>() {
			@Override
			public Predicate toPredicate(Root<EmlStore> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {				
				query.where(cb.equal(root.get(EmlStore_.user).get(EmlUser_.id), userId));
				return null;
			}
		});
		return emlStore;
	}

	@Override
	public EmlStore getStoreByCmsId(final String cmsId) {
	
		EmlStore emlStore = findOne(new Specification<EmlStore>() {
			@Override
			public Predicate toPredicate(Root<EmlStore> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {				
				query.where(cb.equal(root.get(EmlStore_.cmsSiteId), cmsId));
				return null;
			}
		});
		return emlStore;
		
	}

	

}
