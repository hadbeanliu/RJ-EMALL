package com.rongji.cms.emall.web.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 卖家订单管理
 *
 * @since 2015-5-19
 * @author rjf
 *
 */
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController extends CrudAndPageByJsonController<Order> {
	
	@Autowired
	private StoreService storeService;
	
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
	@RequestMappingDescription("卖家订单管理界面")
	public String index() {
		return "/dsww/001/seller-order"; // 跳转到/tpl/dsww/001/seller-order.html页面
	}
	
	@RequestMapping("/edit-price")
	@RequestMappingDescription("卖家修改价格")
	public void editPrice(@RequestParam String orderId, @RequestParam long cutPrice, @RequestParam long logisticsFee, Model model) {
		
		orderService.editPrice(getCurrUserId(), orderId, cutPrice, logisticsFee);
	
		model.addAttribute("returnCode", true);
	}
	
	
	@Override
	protected PageEntity<Order> getPage(DatatablesMetadata metadata) {
		String sellerId = getCurrUserId();
		Store store = storeService.getStoreByUserId(sellerId);
		
		return orderService.getByDatatablesByStore(store.getId(), metadata);
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
