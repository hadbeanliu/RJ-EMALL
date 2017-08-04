package com.rongji.cms.emall.web.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongji.cms.emall.entity.EmlPayment_;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.Payment;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.web.controller.CrudAndPageUseServiceByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/seller/payment")
public class PaymentUsedController extends CrudAndPageUseServiceByJsonController<Payment, PaymentService> {
	
	@Autowired
	private StoreService storeService;
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
//		PAGE_INFO.setLength(20); // 不做分页
//		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.setIdColumn(EmlPayment_.id);
	}

	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected Payment doSaveVo(Payment vo) {
		
		String userId = getCurrUserId();
		return getService().createPaymentByPayCode(userId, vo);
	}

	@RequestMapping("/create")
	public String createPayment(Payment vo) {
		
		String userId = getCurrUserId();
		getService().createPaymentByPayCode(userId, vo);
		
		return redirectTo("/seller/payment");
	}

	@RequestMapping("/update")
	public String updatePayment(Payment vo) {
		
		getService().updateByFilter(vo);
		
		return redirectTo("/seller/payment");
	}

	@Override
	protected PageEntity<Payment> getPage(DatatablesMetadata metadata) {
		String sellerId = getCurrUserId();
		Store store = storeService.getStoreByUserId(sellerId);
		return getService().getByDatatablesByStore(store.getId(), metadata);
	}
	
}
