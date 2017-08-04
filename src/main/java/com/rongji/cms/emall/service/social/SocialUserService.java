package com.rongji.cms.emall.service.social;

import com.rongji.cms.emall.entity.EmlSocial;
import com.rongji.cms.emall.vo.Social;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.service.CommonService;

public interface SocialUserService extends CommonService<Social, EmlSocial>{
	/**
	 * 添加第三方注册用户
	 * @param user 用户对象
	 * @param social 社会化对象
	 * @return
	 */
	public String addSocialUser(User user,Social social);
	
	/**
	 * 第三方登录用户绑定
	 * @param user
	 * @param social
	 * @return
	 */
	public String bindSocialUser(User user,Social social);

}
