package com.rongji.cms.emall.web.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongji.cms.emall.service.pay.PayServiceProviderService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.PayServiceProvider;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.web.controller.CrudAndPageUseServiceByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/pay/providers")
public class PayServiceProviderController extends CrudAndPageUseServiceByJsonController<PayServiceProvider, PayServiceProviderService> {
	
	@Autowired
	private StoreService storeService;
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
//		PAGE_INFO.setLength(20); // 基本上会小于这个数
//		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.setIdColumn("id");
	}

	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<PayServiceProvider> getPage(DatatablesMetadata metadata) {
		String sellerId = getCurrUserId();
		Store store = storeService.getStoreByUserId(sellerId);
		return getService().getByDatatablesByStore(store.getStoreId(), metadata);
	}


}
