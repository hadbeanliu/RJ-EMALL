package com.rongji.cms.emall.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/user/consumerecords")
public class UserConsumeRecordsController extends CrudAndPageByJsonController<Order>{
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("用户消费记录界面")
	public String index(Model model) {	
		String userId = getCurrUserId();
        model.addAttribute("userId", userId);
        return "/dsww/001/user-consume-records";
	}
	
	@Override
	protected PageEntity<Order> getPage(DatatablesMetadata metadata) {
		String userId = getCurrUserId();
		metadata.addSearch(EmlOrder_.status, "4");
		return orderService.getByDatatablesByUser(userId, metadata);
	}

	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected CommonService<Order, ?> getService() {
		return orderService;
	}
	
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.addColumn(EmlOrder_.id, false, false, false, false);	
	}

}
