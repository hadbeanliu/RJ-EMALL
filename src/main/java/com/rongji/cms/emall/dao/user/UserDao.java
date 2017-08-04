package com.rongji.cms.emall.dao.user;

import java.util.List;

import com.rongji.rjskeleton.dao.CommonDao;
import com.rongji.cms.emall.entity.EmlUser;

public interface UserDao extends CommonDao<EmlUser> {
	
	public List<EmlUser> getUserLogin(final String loginName,final String password);
	
	public EmlUser getUserByLoginName(final String loginName);
	
	public List<EmlUser> getUserByType(final String userType);
	
	public List<EmlUser> getPhone(final String mobiletel);
	
}
