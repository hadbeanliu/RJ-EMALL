package com.rongji.cms.emall.web.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlRefund_;
import com.rongji.cms.emall.service.refund.RefundService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.Refund;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/seller/refund")
public class SellerRefundController extends CrudAndPageByJsonController<Refund>{
	@Autowired
	private RefundService refundService;
	@Autowired
	private StoreService storeService;
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(10);
		PAGE_INFO.setCheckboxMode(true);		
		PAGE_INFO.addColumn(EmlRefund_.refundId,true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.order,true, true, true);		
		PAGE_INFO.addColumn(EmlRefund_.creatTime, true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.endTime, true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.buyerRefundReason, true, true, true);
		
	}

	@Override
	protected PageEntity<Refund> getPage(DatatablesMetadata metadata) {
		//HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//EmlUser  User = (EmlUser) request.getSession().getAttribute("user");
		
	
	
		Store store = storeService.getStoreByUserId(getCurrUserId());
		if(store!=null){
		return refundService.getStorefund(store.getId(), metadata);
		}else{
			return null;
		}
//		List<String[]> searchs = new ArrayList<String[]>();
//		searchs.add(new String[]{EmlAddress_.userId.getName(), userId});
	
//		metadata.setSearchs(searchs);
	
	}
	
	/**
	 * 入口页面。地址：/order.htm
	 */
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("退款面界面")
	public String index() {
		
		Store store = storeService.getStoreByUserId(getCurrUserId());
		if(store!=null){
		String storeId = store.getStoreId();
		getRequest().setAttribute("storeId", storeId);
		return "/dsww/001/seller-refund";
	}
		return null;}
	

	@RequestMapping("/cheak")
	public Refund sellRefund( Model model) {
	String type=getRequest().getParameter("type");
	String id=getRequest().getParameter("id");
	String handlingSuggestion=getRequest().getParameter("handlingSuggestion");
	String refundSum=getRequest().getParameter("refundSum");
 
		
		return  refundService.sellRefund(id,type,handlingSuggestion,refundSum); // 调用模版 /order/goods.htm
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
