package com.rongji.cms.emall.web.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Store;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.BaseController;


@Controller
@RequestMapping("/seller")
public class SellerController extends BaseController {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("卖家首页")
	public String index(Model model) {
		
		String userId = getCurrUserId();
		User user = userService.getUserById(userId);
		
		if (user == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_USER_NOT_FOUND);
		}
		
		Store store = storeService.getStoreByUserId(user.getId());
		if (store == null) {
			return redirectTo("/seller/store/add");
		}
		
		model.addAttribute("user", user);
		model.addAttribute("store", store);
		
		return "/dsww/001/seller-center";
	}
	
	@RequestMapping("/payment")
	@RequestMappingDescription("卖家支付方式配置")
	public String payment(){
		return "/dsww/001/seller-payment";
	}

}

