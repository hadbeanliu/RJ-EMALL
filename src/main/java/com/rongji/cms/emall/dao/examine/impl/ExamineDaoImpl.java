package com.rongji.cms.emall.dao.examine.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.examine.ExamineDao;
import com.rongji.cms.emall.entity.EmlExamine;
import com.rongji.cms.emall.entity.EmlExamine_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class ExamineDaoImpl extends CommonDaoImpl<EmlExamine> implements ExamineDao {

	public List<EmlExamine> getExamine(final String userId,final String authentications) {
		List<EmlExamine> examinesList = findAll(new Specification<EmlExamine>(){
			@Override
			public Predicate toPredicate(Root<EmlExamine> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlExamine_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlExamine_.authentications), authentications);
			    query.where(p1,p2);

				return null;
			}
		});
		return examinesList;
	}

	@Override
	public EmlExamine getOne(final String userId,final String status,final String authentications) {
		EmlExamine examine = findOne(new Specification<EmlExamine>(){
			@Override
			public Predicate toPredicate(Root<EmlExamine> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlExamine_.status), status);
				Predicate p2 = cb.equal(root.get(EmlExamine_.authentications), authentications);
				Predicate p3 = cb.equal(root.get(EmlExamine_.userId), userId);
			    query.where(p1,p2,p3);

				return null;
			}
		});
		return examine;
	}
}
