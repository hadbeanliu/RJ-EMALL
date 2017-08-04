package com.rongji.cms.emall.dao.user.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.user.UserDao;
import com.rongji.cms.emall.entity.EmlUser;
import com.rongji.cms.emall.entity.EmlUser_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;

@Repository
public class UserDaoImpl extends CommonDaoImpl<EmlUser> implements UserDao{
	
	public List<EmlUser> getUserLogin(final String loginName,final String password){
		List<EmlUser> list = findAll(new Specification<EmlUser>() {
			@Override
			public Predicate toPredicate(Root<EmlUser> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				//query.where(cb.equal(root.get(EoUser_.loginName), new EoUser(loginName)));
				Predicate p1 = cb.equal(root.get(EmlUser_.loginName), loginName);
				Predicate p2 = cb.equal(root.get(EmlUser_.password), password);
				query.where(p1,p2);
				return null;
			}
		});
		return list;
	}

	
	public EmlUser getUserByLoginName(final String loginName) {
		EmlUser user = findOne(new Specification<EmlUser>() {
			@Override
			public Predicate toPredicate(Root<EmlUser> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				//query.where(cb.equal(root.get(EoUser_.loginName), new EoUser(loginName)));
				Predicate p1 = cb.equal(root.get(EmlUser_.loginName), loginName);
				query.where(p1);
				return null;
			}
		});
		return user;
	}
	
	public List<EmlUser> getUserByType(final String userType) {
		List<EmlUser> userList = findAll(new Specification<EmlUser>() {
			@Override
			public Predicate toPredicate(Root<EmlUser> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				if(userType=="1"){
					Predicate p1 = cb.equal(root.get(EmlUser_.userType), "1");
					Predicate p2 = cb.equal(root.get(EmlUser_.userType), "2");
					Predicate p3 = cb.or(p1,p2);
					query.where(p3);
				}else{
					Predicate p1 = cb.equal(root.get(EmlUser_.userType), userType);
					query.where(p1);
				}
				//query.where(cb.equal(root.get(EoUser_.loginName), new EoUser(loginName)));

				return null;
			}
		});
		return userList;
	}


	@Override
	public List<EmlUser> getPhone(final String mobiletel) {
		    List<EmlUser> userList = findAll(new Specification<EmlUser>() {
			@Override
			public Predicate toPredicate(Root<EmlUser> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
					Predicate p1 = cb.equal(root.get(EmlUser_.mobiletel), mobiletel);
					query.where(p1);
				//query.where(cb.equal(root.get(EoUser_.loginName), new EoUser(loginName)));
				return null;
			}
		});
		return userList;
	}


}
