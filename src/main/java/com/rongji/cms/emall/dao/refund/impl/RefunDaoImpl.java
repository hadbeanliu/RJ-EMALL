package com.rongji.cms.emall.dao.refund.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.refund.RefundDao;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.entity.EmlRefund;
import com.rongji.cms.emall.entity.EmlRefund_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;
@Repository
public class RefunDaoImpl extends CommonDaoImpl<EmlRefund> implements RefundDao{

	@Override
	public List<EmlRefund> getfundByOrderId(final String orderId) {
		
			List<EmlRefund> list = findAll(new Specification<EmlRefund>() {
				@Override
				public Predicate toPredicate(Root<EmlRefund> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {				
					query.where(cb.equal(root.get(EmlRefund_.order).get(EmlOrder_.id), orderId));
					return null;
				}
			});
			return list;
		
	}

}
