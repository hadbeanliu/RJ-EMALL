package com.rongji.cms.emall.web.buyer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.inventory.InventoryService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.order.OrderStatus;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.support.util.MoneyUtil;
import com.rongji.cms.emall.vo.Address;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.BaseController;

@Controller
@RequestMapping("/buyer")
public class BuyerController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("买家首页")
	public String index(Model model) {
		String userId = getCurrUserId();
		User user = userService.getUserById(userId);
		
		if (user == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_USER_NOT_FOUND);
		}
		
		model.addAttribute("user", user);
		
		return "/dsww/001/buyer-center";
	}
	
	@RequestMapping("/order/fast-confirm")
	@RequestMappingDescription("买家快速下单页")
	public String fastConfirmOrder(OrderGoods orderGoods, Model model) {
		
		String userId = getCurrUserId();
		
		List<Order> orders = orderService.createPreOrders(userId, orderGoods);
		Order order = orders.get(0);
		
		orderService.savePreOrder(order);
		
		model.addAttribute("orders", orders);
		model.addAttribute("price", order.getPrice());
		model.addAttribute("fastOrder", true);
		
		return "/dsww/002/buyer-order-fast-confirm";
	}
	
	@RequestMapping("/order/confirm")
	@RequestMappingDescription("买家确认订单页")
	public String confirmOrder(@RequestParam String data, Model model) {
		
		if (data != null && !data.trim().isEmpty()) {
			
			try {
				data = URLDecoder.decode(data, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("订单信息编码错误: " + data, e);
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
			}
			
			OrderGoods[] ogds = null;
			try {
				ogds = new Gson().fromJson(data, new TypeToken<OrderGoods[]>(){}.getType());
			} catch (JsonSyntaxException e1) {
				logger.error("订单信息JSON格式错误: " + data, e1);
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
				
			} catch (JsonParseException e2) {
				logger.error("订单信息JSON数据类型不对应: " + data, e2);
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
			}
			
			if (ogds != null) {
				String userId = getCurrUserId();
				List<Order> orders = orderService.createPreOrders(userId, ogds);
				long price = 0L;
				for (Order order : orders) {
					order.setInCart(true);
					orderService.savePreOrder(order);
					price += order.getPrice();
				}
				
				model.addAttribute("orders", orders);
				model.addAttribute("price", MoneyUtil.longToString(price));
				model.addAttribute("fastOrder", false);
			}
			
		}
		
		return "/dsww/002/buyer-order-confirm";
	}
	
	@RequestMapping("/order/auction")
	@RequestMappingDescription("买家拍下商品（订单）")
	public String orderAuction(@RequestParam String data, Model model) {
		
		String userId = getCurrUserId();
		User user = userService.getUserById(userId);
		
		if (user == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_USER_NOT_FOUND);
		}
		
		if (data == null || data.trim().isEmpty()) {
			logger.error("订单信息不能为空", data);
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
		}
		
		try {
			data = URLDecoder.decode(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("订单信息编码错误: " + data, e);
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
		}
		
		List<OrderInfo> orderInfos = null;
		try {
			orderInfos = new Gson().fromJson(data, new TypeToken<List<OrderInfo>>(){}.getType());
			
		} catch (JsonSyntaxException e1) {
			logger.error("订单信息JSON格式错误: " + data, e1);
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
			
		} catch (JsonParseException e2) {
			logger.error("订单信息JSON数据类型不对应: " + data, e2);
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
		}
		
		if (orderInfos == null || orderInfos.isEmpty()) {
			logger.error("订单信息为空: " + data);
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
		}

		
		List<String> orders = new ArrayList<String>(orderInfos.size());
		for (OrderInfo orderInfo : orderInfos) {
			Order order = orderService.getPreOrder(orderInfo.getPreOrderId());
			if (order == null) {
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_PRE_TIME_OUT);
			}
			if ( ! userId.equals(order.getConfirmUser()) ) {
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_NOT_BELONG_USER);
			}
			
			Address address = addressService.getOne(orderInfo.getAddressId());
			order.setAddress(address);
			order.setRemark(orderInfo.getRemark());
			order.setCarryId(orderInfo.getCarryId());
			
			Order norder = orderService.addOrder(userId, order);
			orders.add(norder.getId());
		}
		
		if (orders.size() > 1) {
			logger.error("仅支持一个订单的支付");
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
		}
		
		return redirectTo("/pay/order/" + orders.get(0));
	}
	
	@RequestMapping("/order/confirm-goods")
	@RequestMappingDescription("买家确认收货界面")
	public String confirmGooods(@RequestParam String orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		
		if ( ! OrderStatus.SEND.equals(order.getStatus()) ) {
			logger.error("订单[{}]状态[{}]不正确，买家货不能进行确认收", order.getOrderNo(), order.getStatusDesc());
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
		}
		
		model.addAttribute("order", order);
	
//		return "/dsww/002/buyer-confirm-goods";
		return "/dsww/001/confirm-page";
	}
	
	@RequestMapping("/order/goods-received")
	@RequestMappingDescription("买家确认收货操作")
	public String gooodsReceived(@RequestParam String orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		
		if ( ! OrderStatus.SEND.equals(order.getStatus()) ) {
			logger.error("订单[{}]状态[{}]不正确，买家货不能进行确认收", order.getOrderNo(), order.getStatusDesc());
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
		}
		
		order.setStatus(OrderStatus.SUCCESS);
		order.setDoneTime(new Date());
		orderService.updateByFilter(order, "status", "doneTime");
		// 可能需要通知支付方
		
		model.addAttribute("order", order);
	
		return redirectTo("/buyer/order");
	}
	
}
