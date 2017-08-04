package com.rongji.cms.emall.dao.message.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.message.MessagePersonModelDao;
import com.rongji.cms.emall.entity.EmlMessageModel_;
import com.rongji.cms.emall.entity.EmlMessagePersonModel;
import com.rongji.cms.emall.entity.EmlMessagePersonModel_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class MessagePersonModelDaoImpl extends CommonDaoImpl<EmlMessagePersonModel> implements MessagePersonModelDao {

	@Override
	public List<EmlMessagePersonModel> getMessageModelByReceiverType(final String userId) {
		List<EmlMessagePersonModel> messagePersonModel = findAll(new Specification<EmlMessagePersonModel>(){
			@Override
			public Predicate toPredicate(Root<EmlMessagePersonModel> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlMessagePersonModel_.userId), userId);
				query.where(p1);
				return null;
			}
		});
		return messagePersonModel;
	}

	@Override
	public EmlMessagePersonModel getActiveBySign(final String sign, final String userId) {
		EmlMessagePersonModel messagePersonModel = findOne(new Specification<EmlMessagePersonModel>(){
			@Override
			public Predicate toPredicate(Root<EmlMessagePersonModel> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlMessagePersonModel_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlMessagePersonModel_.messageModel).get(EmlMessageModel_.modelSign), sign);
				query.where(p1,p2);
				return null;
			}
		});
		return messagePersonModel;
	}


}
