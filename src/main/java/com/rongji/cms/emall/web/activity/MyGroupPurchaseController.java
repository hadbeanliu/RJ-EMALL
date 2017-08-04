package com.rongji.cms.emall.web.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.rongji.cms.emall.entity.EmlMyGroupPurchase_;
import com.rongji.cms.emall.service.activity.MyGroupPurchaseService;
import com.rongji.cms.emall.vo.MyGroupPurchase;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/buyer/mygrouppurchase")
public class MyGroupPurchaseController extends CrudAndPageByJsonController<MyGroupPurchase> {
	
	
	@Autowired
	private MyGroupPurchaseService myGroupPurchaseService;

	@Override
	protected CommonService<MyGroupPurchase, ?> getService() {
		return myGroupPurchaseService;
	}

	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("我的团购")
	public String index(Model model) {
       return "/dsww/001/buyer-mygrouppurchase";
	}		
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {	
		PAGE_INFO.addColumn(EmlMyGroupPurchase_.id, false, false, false, false);
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<MyGroupPurchase> getPage(DatatablesMetadata metadata) {
		String userId=getCurrUserId();
		if(userId!=null){
			metadata.addSearch(EmlMyGroupPurchase_.userId, userId);
		}
		return super.getPage(metadata);
	}		
}
