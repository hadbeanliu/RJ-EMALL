package com.rongji.cms.emall.web.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.inventory.InventoryService;
import com.rongji.cms.emall.service.logistic.LogisticService;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Address;
import com.rongji.cms.emall.vo.Logistics;
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
 * 卖家订单发货管理
 *
 * @since 2015-7-7
 * @author zhangxu
 *
 */
@Controller
@RequestMapping("/seller/order/delivery")
public class OrderDeliveryController extends CrudAndPageByJsonController<Order> {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private InventoryService inventoryService;
	
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
	private LogisticService logisticService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private OrderGoodsService orderGoodsService; 
	
	@Autowired
	private AddressService addressService;

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
		return "/dsww/001/seller-order-delivery"; // 跳转到/tpl/dsww/001/seller-order-delivery.html页面
	}
	
	@RequestMapping("/{orderId}")
	@RequestMappingDescription("订单发货管理界面")
	public String delivery(@PathVariable String orderId,Model model){
		String userId = getCurrUserId();
		List<Address> selleraddresses=addressService.getAddressByUserId(userId ,"0");
		Address sad=new Address();
		for(Address ad:selleraddresses){
			if("1".equals(ad.getIsDefault())){
				sad=ad;
			}
		}
		List<OrderGoods> goods=orderGoodsService.getGoodsByOrder(orderId);
		for(OrderGoods gd : goods){
			gd.setSku(inventoryService.getNameByskuId(gd.getGoodsId(), gd.getSkuId()));
		}
		Order order = orderService.getOne(orderId);
		order.setGoods(goods);
		model.addAttribute("selleraddress",sad);
		model.addAttribute("order",order);
		return "/dsww/001/seller-center-delivery"; // 跳转到/tpl/dsww/001/seller-center-delivery.html页面
	}
	
	@RequestMapping("/address/{addressId}")
	@ResponseBody
	public Address address(@PathVariable String addressId) {
		return addressService.getOne(addressId); 
	}
	
	@RequestMapping("/saveLogistics")
	@ResponseBody
	public Logistics saveLogistic(Logistics vo ,Model model){
		String userId = getCurrUserId();
		return logisticService.sendGoods(userId,vo);
	}
	
	@RequestMapping("/result/{voId}")
	public String result(@PathVariable String voId,Model model){
		Order order=logisticService.getOrder(voId);

		Address sendadd = addressService.getOne(order.getLogistics().getSenddefid());
		Address canceladd = addressService.getOne(order.getLogistics().getCanceldefid());
		
		model.addAttribute("sendadd", sendadd);
		model.addAttribute("canceladd", canceladd);
		model.addAttribute("order", order);
		return "/dsww/001/seller-center-deliverysuccess";
	}
	
	@RequestMapping("/updatelog")
	@ResponseBody
	public Logistics updatelog(Logistics vo){
		Logistics log=logisticService.getOne(vo.getId());
		log.setMailno(vo.getMailno());
		return logisticService.update(log);
	}
	
	@RequestMapping("/getaddress")
	public List<Address> getaddress(){
		String userId = getCurrUserId();
		List<Address> selleraddresses=addressService.getAddressByUserId(userId ,"0");
		return selleraddresses;
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
