package com.rongji.cms.emall.web.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

@Controller
@RequestMapping("/demo/order_goods/{orderId}")
public class DemoOrderGoodsController extends CrudAndPageByJsonController<OrderGoods> {
	
	
	@Override
	protected OrderGoods doUpdateVo(OrderGoods vo, MultiValueMap<String, Object> data) {
		if (vo.getId() == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		
		return super.voUpdate(vo, data);
	}
	
	@Override
	protected OrderGoods doSaveVo(OrderGoods vo) {
		String orderId = getUriVar("orderId");
		if (vo == null || orderId == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		
		vo.setOrder(new Order(orderId));
		vo.setCreateTime(new Date());
		vo.setGoodsId("AR20110715697893");
		
		return orderGoodsService.save(vo);
	}
	

	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.setIdColumn(EmlOrderGoods_.id);
	}
	
	@Autowired
	private OrderGoodsService orderGoodsService;

	@Override
	protected CommonService<OrderGoods, ?> getService() {
		return orderGoodsService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

}
