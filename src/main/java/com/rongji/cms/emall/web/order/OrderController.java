package com.rongji.cms.emall.web.order;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

/**
 * 订单管理
 *
 * @since 2015-5-19
 * @author rjf
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController extends CrudAndPageByJsonController<Order> {
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.addColumn(EmlOrder_.id, true, true, true);
		PAGE_INFO.addColumn(EmlOrder_.createTime, false, false, false);
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
	
	
	/**
	 * 入口页面。地址：/order.htm
	 */
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("订单查看界面")
	public String index() {
		return "/order"; // 跳转到/order.htm
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@RequestMappingDescription("用户创建订单")
	public String createOrder(@RequestParam String data, Model model) {
		try {
			data = URLDecoder.decode(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		RequestOrder orderInfo = new Gson().fromJson(data, RequestOrder.class);
		List<Order> orders = new ArrayList<Order>();
		for (OrderInfo orInfo : orderInfo.getInfo()) {
			orders.add( orderService.addOrder(getCurrUserId(), orInfo.getGoods()) );
		}

		return redirectTo("/pay/order/"+orders.get(0).getId());
	}
	
//	/**
//	 * 用于创建购物车中商铺不属于同一个商家时的使用
//	 * <p>
//	 * 商品不属于同一个商家时需要拆分订单成多个
//	 * 
//	 * @param data
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/multi_create", method=RequestMethod.POST)
//	@RequestMappingDescription("用户创建批量订单")
//	public String createMultiOrder(@RequestParam String data, Model model) {
//		try {
//			data = URLDecoder.decode(data, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		RequestOrder orderInfo = new Gson().fromJson(data, RequestOrder.class);
//		List<Order> orders = new ArrayList<Order>();
//		for (OrderInfo orInfo : orderInfo.getInfo()) {
//			orders.add( orderService.addOrder(userId, orInfo.getGoods()) );
//		}
//		
//		return redirectTo("/pay/order/"+orders.get(0).getId());
//	}
	
	/**
	 * 通过购物车，进行订单确认
	 * 
	 * @param data
	 * @param model
	 * @return
	 */
	@RequestMapping("/confirm_order")
	@RequestMappingDescription("确认订单信息")
	public String confirmOrder(@RequestParam String data, Model model) {
		
		return "/order/";
	}
	
	@RequestMapping(value="/buy_now", method=RequestMethod.POST)
	@RequestMappingDescription("快速下订单")
	public String buyNow(@RequestParam String data, Model model) {
		
		try {
			data = URLDecoder.decode(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		OrderGoods orderInfo = new Gson().fromJson(data, OrderGoods.class);
		
		List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();
		orderGoods.add(orderInfo);
		
		model.addAttribute("orderGoods", orderGoods);
		
		return "/order/settleAccounts";
	}
	
	
	@Override
	protected Order doSaveVo(Order vo) {
		vo.setCreateTime(new Date());
		vo.setCreateUser(getCurrUserId());
		
		return super.doSaveVo(vo);
	}
	

	@Override
	protected Order doUpdateVo(Order vo, MultiValueMap<String, Object> data) {
		if (vo.getId() == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		
		return super.doUpdateVo(vo, data);
	}
	
	
	
	@RequestMapping("/{orderId}")
	public String goods(@PathVariable String orderId, Model model) {
		
		Order order = orderService.getOne(orderId);
		if (order == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		
		model.addAttribute("orderId", orderId);
		
		return "order/goods"; // 调用模版 /order/goods.htm
	}

}
