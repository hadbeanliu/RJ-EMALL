package com.rongji.cms.emall.dao.crowdfunding.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.crowdfunding.CrowdfundingReturnDao;
import com.rongji.cms.emall.entity.EmlCrowdfundingReturn;
import com.rongji.cms.emall.entity.EmlCrowdfundingReturn_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class CrowdfundingReturnDaoImpl extends CommonDaoImpl<EmlCrowdfundingReturn> implements CrowdfundingReturnDao {
	
	public List<EmlCrowdfundingReturn> getCrowdfundingReturn(final String crowdfundingId){
		List<EmlCrowdfundingReturn> list = findAll(new Specification<EmlCrowdfundingReturn>() {
	
			@Override
			public Predicate toPredicate(Root<EmlCrowdfundingReturn> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlCrowdfundingReturn_.crowdfundingId), crowdfundingId);	
				query.where(p1);
				return null;
			}
		});
		return list;
	}	
}
