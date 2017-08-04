package com.rongji.cms.emall.web.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlMyCart_;
import com.rongji.cms.emall.vo.MyCart;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;
import com.rongji.cms.emall.service.shopping.MyCartService;

@Controller
@RequestMapping("/buyer/mycart/{storeId}")
public class MyCartShopController extends CrudAndPageByJsonController<MyCart> {
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("我的购物车店铺分类")
	public String index(Model model) {
	    return "";
	}
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {		
		PAGE_INFO.setIdColumn(EmlMyCart_.id);
	}
	
	@Autowired
	private MyCartService myCartService;

	@Override
	protected CommonService<MyCart, ?> getService() {
		return myCartService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<MyCart> getPage(DatatablesMetadata metadata) {
		String userId = getCurrUserId();
		if(userId!=null){
			String storeId=getUriVar("storeId");
			metadata.addSearch(EmlMyCart_.storeId,storeId);	
		}
		return myCartService.getByDatatables(userId, metadata);
	}
}
