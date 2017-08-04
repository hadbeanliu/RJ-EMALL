package com.rongji.cms.emall.web.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import com.alibaba.druid.util.StringUtils;
import com.rongji.cms.emall.entity.EmlRefund_;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.refund.RefundService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Refund;
import com.rongji.cms.emall.vo.ResultModel;
import com.rongji.cms.emall.vo.Store;

import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.support.mvc.RequestUtil;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/buyer/refund")
public class BuyerRefundController extends CrudAndPageByJsonController<Refund>{
	@Autowired
	private RefundService refundService;
	@Autowired
	private OrderService orderService;
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(10);
		PAGE_INFO.setCheckboxMode(true);	
		
		PAGE_INFO.setIdColumn(EmlRefund_.refundId);
		PAGE_INFO.addColumn(EmlRefund_.refundId,true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.order,true, true, true);		
		PAGE_INFO.addColumn(EmlRefund_.creatTime, true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.endTime, true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.endTime, true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.buyerRefundReason, true, true, true);
		//PAGE_INFO.addColumn(EmlRefund_., true, true, true);
		
	}

	@Override
	protected PageEntity<Refund> getPage(DatatablesMetadata metadata) {
		//HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//EmlUser  User = (EmlUser) request.getSession().getAttribute("user");
		
		String userId =getCurrUserId();
		return refundService.getUserfund(userId, metadata);

	
	}
	
	/**
	 * 入口页面。地址：/order.htm
	 */
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("退款面界面")
	public String index() {
		String userId =getCurrUserId();
		getRequest().setAttribute("userId", userId);
		
		return "/dsww/001/buyer-refund";
	}
	
	@RequestMapping("/toService")
	public String toService() {
		String refundId = getRequest().getParameter("refundId");
		getRequest().setAttribute("refundId", refundId);
		String userId =getCurrUserId();
		getRequest().setAttribute("userId", userId);
		return "/dsww/001/buyer-serviceForm";
	}
	@RequestMapping("/toRefund")
	public String toRefund() {
		String refundId = getRequest().getParameter("refundId");
		String orderId = getRequest().getParameter("orderId");
		if(!(StringUtils.isEmpty(refundId))&&(StringUtils.isEmpty(orderId))){
			Refund refund = refundService.getOne(refundId);
			getRequest().setAttribute("refund",refund);	
			orderId=refund.getOrder().getId();
		}
		
		getRequest().setAttribute("orderId", orderId);
		Order order = orderService.getOrder(orderId);
		getRequest().setAttribute("order", order);
		return "/dsww/001/buyer-center-apply-refund-1";
	}
	@RequestMapping("/details")
	public String details() {
		String orderId = getRequest().getParameter("orderId");
		getRequest().setAttribute("orderId", orderId);
		Order order = orderService.getOrder(orderId);
	
		List<Refund> refunds = refundService.getfundByOrderId(orderId);
		Refund refund = refunds.get(0);
		getRequest().setAttribute("order", order);
		getRequest().setAttribute("refund", refunds.get(0));
		if("2".equals(refund.getStatus())||"1".equals(refund.getStatus())){
		
			return "/dsww/001/buyer-center-apply-refund-3";
		}else{
		
			return "/dsww/001/buyer-center-apply-refund-2";
		}
		
		
	}
	@RequestMapping("/del")
	public String del() {
		String refundId = getRequest().getParameter("refundId");
		Refund refund = refundService.getOne(refundId);
		Order order = refund.getOrder();
		order.setStatus(refund.getOrderStatus());
		orderService.save(order);
		refundService.delete(refund);
		return redirectTo("/buyer/refund");
	}
	@RequestMapping("/save")
	public void save(Model model,Refund vo) {
		Map<String, String> map=new HashMap<String, String>();
		String orderId = getRequest().getParameter("orderId");
		String refundId = getRequest().getParameter("refundId");
		String refundReason = getRequest().getParameter("buyerRefundReason");		
		String refundStatus = getRequest().getParameter("refundStatus");
		if(StringUtils.isEmpty(refundId)){
		ResultModel result=	refundService.addrefund(orderId,refundReason,refundStatus);
		map.put("ratcode", result.getRatcode());
		map.put("result",result.getResult());
		model.addAllAttributes(map);

		}else{
			vo.setOrder(orderService.getOrder(orderId));
			vo.setOrderStatus(refundService.getOne(refundId).getOrderStatus());
			refundService.update(vo);
			map.put("ratcode","0");
			model.addAllAttributes(map);
		}
		///return redirectTo("/refund/user/"+getUriVar("userId")+"");
	
		
	}
	/**
	 * 申请客服介入接口
	 */
	@RequestMapping("/complaint")
	public Refund complaint(Model model) {

		String refundId = getRequest().getParameter("refundId");
		return refundService.changeStatus(refundId, "3");
		
		
		
	}
	/**
	 * 客服处理意见回调
	 */
	@RequestMapping("/handling")
	public void handling(Model model) {

		String refundId = getRequest().getParameter("refundId");
		String handlingSuggestion = getRequest().getParameter("handlingSuggestion");
		String status = getRequest().getParameter("status");	
		refundService.refundHandlingService(refundId, status, handlingSuggestion);
		
		
		
	}
	@Override
	protected PageInfo getPageInfo() {
		
		return PAGE_INFO;
	}

	@Override
	protected CommonService<Refund, ?> getService() {

		return refundService;
	}
}
