package com.rongji.cms.emall.web.crowdfunding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.service.crowdfunding.MyCrowdfundingService;
import com.rongji.cms.emall.entity.EmlMyCrowdfunding_;
import com.rongji.cms.emall.vo.MyCrowdfunding;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/buyer/mycrowdfunding")
public class MyCrowdfundingController extends CrudAndPageByJsonController<MyCrowdfunding> {
		
	@Autowired
	private MyCrowdfundingService mycrowdfundingService;	
	
	@Override
	protected CommonService<MyCrowdfunding, ?> getService() {
		return mycrowdfundingService;
	}

	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("我的众筹")
	public String index(Model model) {
		String userId = getCurrUserId();
		if(userId!=null){
			  return "/dsww/001/buyer-mycrowdfunding";	
		}else{
			return "/";
		}
	}	
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {	
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.setLength(10);
		PAGE_INFO.addColumn(EmlMyCrowdfunding_.id,true, true, true);	
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<MyCrowdfunding> getPage(DatatablesMetadata metadata) {		
		String userId = getCurrUserId();
		if(userId!=null){
			metadata.addSearch(EmlMyCrowdfunding_.userId,userId);
		}
		return super.getPage(metadata);		
	}	
}
