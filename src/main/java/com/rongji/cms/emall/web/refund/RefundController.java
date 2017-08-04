package com.rongji.cms.emall.web.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlRefund_;
import com.rongji.cms.emall.service.refund.RefundService;
import com.rongji.cms.emall.vo.Refund;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

@Controller
@RequestMapping("/admin/refund")
public class RefundController extends CrudAndPageByJsonController<Refund>{
	@Autowired
	private RefundService refundService;

	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);		
		PAGE_INFO.addColumn(EmlRefund_.refundId,true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.order,true, true, true);		
		PAGE_INFO.addColumn(EmlRefund_.creatTime, true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.endTime, true, true, true);
		PAGE_INFO.addColumn(EmlRefund_.buyerRefundReason, true, true, true);
		
	}

	
	
	/**
	 * 入口页面。地址：/order.htm
	 */
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("退款面界面")
	public void index() {
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
