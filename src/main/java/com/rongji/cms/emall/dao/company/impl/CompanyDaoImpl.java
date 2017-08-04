package com.rongji.cms.emall.dao.company.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.company.CompanyDao;
import com.rongji.cms.emall.entity.EmlCompany;
import com.rongji.cms.emall.entity.EmlCompany_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class CompanyDaoImpl extends CommonDaoImpl<EmlCompany> implements CompanyDao {

	@Override
	public List<EmlCompany> getCompanyByUserId(final String userId) {
		List<EmlCompany> companiesList = findAll(new Specification<EmlCompany>(){
			@Override
			public Predicate toPredicate(Root<EmlCompany> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlCompany_.userId), userId);
			    query.where(p1);

				return null;
			}
		});
		return companiesList;
	}
	
}
