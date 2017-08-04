package com.rongji.cms.emall.dao.message.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.message.MessageModelDao;
import com.rongji.cms.emall.entity.EmlMessageModel;
import com.rongji.cms.emall.entity.EmlMessageModel_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class MessageModelDaoImpl extends CommonDaoImpl<EmlMessageModel> implements MessageModelDao{

	@Override
	public EmlMessageModel getMessageModelBySign(final String sign) {
		EmlMessageModel messagemodel = findOne(new Specification<EmlMessageModel>(){
			@Override
			public Predicate toPredicate(Root<EmlMessageModel> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlMessageModel_.modelSign), sign);
				query.where(p1);
				return null;
			}
		});
		return messagemodel;
	}

	@Override
	public List<EmlMessageModel> getMessageModelByRecevierType(final String recevierType) {
		List<EmlMessageModel> messagemodel = findAll(new Specification<EmlMessageModel>(){
			@Override
			public Predicate toPredicate(Root<EmlMessageModel> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlMessageModel_.receiverType), recevierType);
				query.where(p1);
				return null;
			}
		});
		return messagemodel;
	}

}
