package com.rongji.cms.emall.web.order;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 订单商品管理
 *
 * @since 2015-5-19
 * @author rjf
 *
 */
@Controller
@RequestMapping("/order/{orderId}/goods")
public class OrderGoodsController extends CrudAndPageByJsonController<OrderGoods> {
	

	/**
	 * 入口页面。地址：/order/goods.htm
	 */
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("订单商品界面")
	public String index(Model model) {
		
		String orderId = getUriVar("orderId");
		Order order = orderService.getOne(orderId);
		if (order == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		
		model.addAttribute("orderId", orderId);
		
		return "order/goods";
	}
	
	
	@Override
	protected PageEntity<OrderGoods> getPage(DatatablesMetadata metadata) {
		//metadata.get
		String orderId = getUriVar("orderId");
		Order order = orderService.getOne(orderId);
		if (order == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		
//		String orderNo = order.getOrderNo(); // 测试订单编号
//		return orderGoodsService.getByDatatables(orderNo, metadata);
		
//		metadata.addSearch("orderId", orderNo);
		metadata.addSearch(EmlOrderGoods_.order, orderId);
		
		return super.getPage(metadata);
	}


	@Override
	protected OrderGoods doSaveVo(OrderGoods vo) {
		
		String orderId = getUriVar("orderId");
		vo.setOrder(new Order(orderId));
		vo.setCreateTime(new Date());
		vo.setGoodsId("AR20110715697893");
		
		return super.doSaveVo(vo);
	}

	@Override
	protected OrderGoods doUpdateVo(OrderGoods vo,
			MultiValueMap<String, Object> data) {
		
		String orderId = getUriVar("orderId");
		vo.setOrder(new Order(orderId));
		vo.setModifyTime(new Date());
		
		return super.doUpdateVo(vo, data);
	}


	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlOrderGoods_.id);
	}
	
	@Autowired
	private OrderGoodsService orderGoodsService;
	
	@Autowired
	private OrderService orderService;

	@Override
	protected CommonService<OrderGoods, ?> getService() {
		return orderGoodsService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}
	
}
