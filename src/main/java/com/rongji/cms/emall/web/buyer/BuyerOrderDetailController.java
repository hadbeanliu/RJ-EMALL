package com.rongji.cms.emall.web.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.logistic.LogisticService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

/**
 * 买家订单管理
 *
 * @since 2015-7-28
 * @author zxu
 *
 */
@Controller
@RequestMapping("/buyer/order/detail")
public class BuyerOrderDetailController extends CrudAndPageByJsonController<Order> {
	
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
	
	@Autowired
	private LogisticService logisticService;

	@Override
	protected CommonService<Order, ?> getService() {
		return orderService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("买家订单详情页")
	public String index(@RequestParam("orderId")  String orderId,Model model) {
		Order order=logisticService.getOrder(orderId);
		model.addAttribute("order", order);
		return "/dsww/001/detail-page";
	}

	@RequestMapping("/confirm/{orderId}")
	@RequestMappingDescription("已购买商品列表页")
	public <T> String confirmGoods(@PathVariable String orderId){
		String userId=getCurrUserId();
		Order order=orderService.getOrder(orderId);
		if(order.getCreateUser().equals(userId)){
			orderService.confirmGoods(userId, orderId);
			return "success";
		}
		return "error";
	}
	
}
