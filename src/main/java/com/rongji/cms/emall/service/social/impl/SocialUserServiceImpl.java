package com.rongji.cms.emall.service.social.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.social.SocialDao;
import com.rongji.cms.emall.entity.EmlSocial;
import com.rongji.cms.emall.entity.EmlUser;
import com.rongji.cms.emall.service.social.SocialUserService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Social;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class SocialUserServiceImpl extends CommonServiceImpl<Social, EmlSocial, SocialDao> implements SocialUserService{

	@Autowired
	private UserService userService;
	
	@Override
	public Social convertToVo(EmlSocial entity) {
		Social vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity,vo,true,"user");
		User user = userService.convertToVo(entity.getUser());
		vo.setUser(user);
		return vo;
	}

	@Override
	public EmlSocial convertToEntity(Social vo) {
		EmlSocial entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "user");
		EmlUser user = userService.convertToEntity(vo.getUser());
		entity.setUser(user);
		return entity;
	}

	@Override
	public String addSocialUser(User user, Social social) {
		user = userService.addNoActiveUser(user);
		social.setUser(user);
		super.save(social);
		return null;
	}

	@Override
	public String bindSocialUser(User user, Social social) {
		return null;
	}
		

}
