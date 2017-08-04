package com.rongji.cms.emall.web.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlComplaint_;
import com.rongji.cms.emall.service.complaint.ComplaintService;
import com.rongji.cms.emall.service.refund.RefundService;
import com.rongji.cms.emall.vo.Complaint;
import com.rongji.cms.emall.vo.MesAuditSearchQuery;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageUseServiceByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;
@Controller
@RequestMapping("/buyer/complaint")
public class ComplaintController extends CrudAndPageUseServiceByJsonController<Complaint, ComplaintService> {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private RefundService refundService;
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setCheckboxMode(false);
		PAGE_INFO.setIdColumn(EmlComplaint_.id);
	}
	@Override
	protected PageInfo getPageInfo() {
		
		return PAGE_INFO;
	}
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("投诉管理")
	public String index(Model model) {
		String userId = getCurrUserId();
		if (userId != null) {
//			if (true) {				
				return "/dsww/001/buyer-complaint";
//			} else {
//				return "/";
//			}
		} else {
			return "/login";
		}		
	}	

	
	@RequestMapping("/add")
	@RequestMappingDescription("添加投诉")
	public String add(Model model) {
		String userId = getCurrUserId();
		String orderId =  getRequest().getParameter("orderId");
		if (userId != null) {
//			if (true) {		
			String refundId = refundService.getfundByOrderId(orderId).get(0).getId();
			getRequest().setAttribute("orderId", orderId);
			getRequest().setAttribute("userId", userId);
			getRequest().setAttribute("refundId",refundId );
			
				return "/dsww/001/buyer-complain-form";
//			} else {
//				return "/";
//			}
		} else {
			return "/login";
		}		
	}
	@RequestMapping("/detail")
	@RequestMappingDescription("投诉处理情况")
	public String detail(Model model) {
		String id =  getRequest().getParameter("id");
		MesAuditSearchQuery complaint = complaintService.getdetailById(id);
		getRequest().setAttribute("complaint", complaint);
		
		return "/dsww/001/buyer-center-messageboard";
		
	}
	@Override
	protected PageEntity<Complaint> getPage(DatatablesMetadata metadata) {
		metadata.addInSearch("userId", getCurrUserId());
		return super.getPage(metadata);
	}
	
}
