package com.rongji.cms.emall.web.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 买家订单管理
 *
 * @since 2015-5-19
 * @author rjf
 *
 */
@Controller
@RequestMapping("/buyer/order")
public class BuyerOrderController extends CrudAndPageByJsonController<Order> {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlOrder_.id);
	}
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;

	@Override
	protected CommonService<Order, ?> getService() {
		return orderService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("买家订单管理界面")
	public String index() {
		return "/dsww/001/buyer-order";
	}
	
	
	@Override
	protected PageEntity<Order> getPage(DatatablesMetadata metadata) {
		String userId = getCurrUserId();
//		if (userId == null || userId.trim().isEmpty()) {
//			userId = getRequest().getParameter("userId"); // FIXME 仅用于测试
//		}
		
		User user = userService.getUserById(userId);
		
		if (metadata.getSort() == null) { // 添加默认排序
			metadata.addSort(EmlOrder_.createTime, true);
		}
		
		return orderService.getByDatatablesByUser(user.getId(), metadata);
	}

	@Override
	protected Order doSaveVo(Order vo) {
		throw new EmallException(EmallStatusCode.ERROR_REQUEST_NOT_SUPPORT);
	}
	

	@Override
	protected Order doUpdateVo(Order vo, MultiValueMap<String, Object> data) {
		throw new EmallException(EmallStatusCode.ERROR_REQUEST_NOT_SUPPORT);
	}

}
