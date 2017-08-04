package com.rongji.cms.emall.dao.address.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;


import com.rongji.cms.emall.dao.address.AddressDao;
import com.rongji.cms.emall.entity.EmlAddress;
import com.rongji.cms.emall.entity.EmlAddress_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;


@Repository
public class AddressDaoImpl extends CommonDaoImpl<EmlAddress> implements AddressDao {
	
	public List<EmlAddress> getAddressByUserId(final String userId, final String type){
		List<EmlAddress> list = findAll(new Specification<EmlAddress>() {
			@Override
			public Predicate toPredicate(Root<EmlAddress> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {	
				Predicate p1 = cb.equal(root.get(EmlAddress_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlAddress_.type), type);
				query.where(p1,p2);
				return null;
			}
		});
		return list;
	}
	

}
