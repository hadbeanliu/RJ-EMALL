package com.rongji.cms.emall.web.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.service.order.SellerOrderGoodsService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 卖家已出售商品
 *
 * @since 2015-6-4
 * @author lxy
 *
 */
@Controller
@RequestMapping("/order/goods/seller/{storeId}")
public class SellerOrderGoodsController extends CrudAndPageByJsonController<OrderGoods>{
	
	@Autowired
	private SellerOrderGoodsService sellerOrderGoodsService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("卖家已出售商品界面")
	public String index(Model model){
		String storeId = getUriVar("storeId");
		model.addAttribute("storeId", storeId);
		return "order/soldgoods";
	}
	
	@Override
	protected CommonService<OrderGoods, ?> getService() {
		return sellerOrderGoodsService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}
	
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.setIdColumn(EmlOrderGoods_.id);
	}
	
	@Override
	protected PageEntity<OrderGoods> getPage(DatatablesMetadata metadata) {
		String storeId = getUriVar("storeId");
		OrderGoods orderGoods = new OrderGoods();
		Order order = new Order();
		Store store = new Store();
		store.setId(storeId);
		order.setStore(store);
		orderGoods.setOrder(order);
		return sellerOrderGoodsService.getSoldGoodsByStoreId(storeId, metadata);
	}
}
