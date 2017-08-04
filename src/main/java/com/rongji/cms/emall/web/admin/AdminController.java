package com.rongji.cms.emall.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.BaseController;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("管理后台首页")
	public String index() {
		
		String userId = getCurrUserId();
		User user = userService.getUserById(userId);
		
		if (user == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_USER_NOT_FOUND);
		}
		
		return "/dsww/001/admin-center";
	}
	
}

