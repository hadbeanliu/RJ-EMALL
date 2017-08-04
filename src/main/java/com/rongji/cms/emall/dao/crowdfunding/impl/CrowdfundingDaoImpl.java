package com.rongji.cms.emall.dao.crowdfunding.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.crowdfunding.CrowdfundingDao;
import com.rongji.cms.emall.entity.EmlCrowdfunding;
import com.rongji.cms.emall.entity.EmlCrowdfunding_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class CrowdfundingDaoImpl extends CommonDaoImpl<EmlCrowdfunding> implements CrowdfundingDao {

	@Override
	public EmlCrowdfunding getCrowdfunding(final String userId,	final String crowdfundingId) {
		EmlCrowdfunding crowdfunding = findOne(new Specification<EmlCrowdfunding>() {
			@Override
			public Predicate toPredicate(Root<EmlCrowdfunding> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Predicate p1 = cb.equal(root.get(EmlCrowdfunding_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlCrowdfunding_.id), crowdfundingId);
				query.where(p1,p2);
				return null;
			}
		});
		return crowdfunding;
	}

}
