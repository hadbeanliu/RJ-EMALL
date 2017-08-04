package com.rongji.cms.emall.web.crowdfunding;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongji.cms.emall.entity.EmlCrowdfundingReturn_;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingReturnService;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingService;
import com.rongji.cms.emall.vo.CrowdfundingReturn;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/seller/crowdfunding/{crowdfundingId}/return")
public class CrowdfundingReturnController extends CrudAndPageByJsonController<CrowdfundingReturn> {
	
	
	@Autowired
	private CrowdfundingReturnService crowdfundingReturnService;
	
	@Autowired
	private CrowdfundingService crowdfundingService;

	@Override
	protected CommonService<CrowdfundingReturn, ?> getService() {
		return crowdfundingReturnService;
	}	
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("CRUD和分页Demo界面")
	public String index(Model model) {
		String userId = getCurrUserId();
		if(userId!=null){
			String crowdfundingId = getUriVar("crowdfundingId");			
			if(crowdfundingService.getCrowdfunding(userId, crowdfundingId)){
				model.addAttribute("crowdfundingId", crowdfundingId);		
				return "/crowdfunding/return";
			}else{
				return redirectTo("/404");		
			}
		}else{
			return redirectTo("/user/login.htm");
		}
	}

	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.addColumn(EmlCrowdfundingReturn_.id, false, false, false, false);
		PAGE_INFO.addColumn(EmlCrowdfundingReturn_.money, true, true, true);
		PAGE_INFO.addColumn(EmlCrowdfundingReturn_.title, true, true, true);		
		PAGE_INFO.addColumn(EmlCrowdfundingReturn_.returnNumber, false, true, true);
		PAGE_INFO.addColumn(EmlCrowdfundingReturn_.logisticsWay, true, true, true);
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@RequestMapping("vo_save_next")
	@RequestMappingDescription("保存众筹回报数据并跳转到下一步")
	protected String doSaveVoToNext(CrowdfundingReturn vo,RedirectAttributes redirectAttributes,Model model) {
		MultipartFile file = vo.getReturnPictureFile();
		if(file!=null){
			vo.setReturnPicture(file.getOriginalFilename());
		}
		CrowdfundingReturn newVo=super.doSaveVo(vo);
		model.addAttribute("crowdfundingId", newVo.getCrowdfundingId());
		return redirectTo("/seller/crowdfunding/"+newVo.getCrowdfundingId()+"/sponsor.htm");
	}

	@RequestMapping("vo_save")
	@RequestMappingDescription("保存众筹回报数据")
	protected String doSaveVo(CrowdfundingReturn vo,RedirectAttributes redirectAttributes,Model model) {
		MultipartFile file = vo.getReturnPictureFile();
		if(file!=null){
			vo.setReturnPicture(file.getOriginalFilename());
		}
		CrowdfundingReturn newVo=super.doSaveVo(vo);
		return redirectTo("/seller/crowdfunding/"+newVo.getCrowdfundingId()+"/return.htm");
	}

	@Override
	protected PageEntity<CrowdfundingReturn> getPage(DatatablesMetadata metadata) {
		String crowdfundingId = getUriVar("crowdfundingId");
		metadata.addSearch(EmlCrowdfundingReturn_.crowdfundingId,crowdfundingId);
		return super.getPage(metadata);
	}

	@RequestMapping("vo_update")
	@RequestMappingDescription("更新众筹回报数据")
	protected String doUpdateVo(CrowdfundingReturn vo,@RequestParam MultiValueMap<String, Object> data,RedirectAttributes redirectAttributes) {	

		CrowdfundingReturn newVo=super.doUpdateVo(vo, data);
		return redirectTo("/seller/crowdfunding/"+newVo.getCrowdfundingId()+"/return.htm");
	}
	
	@RequestMapping("vo_update_next")
	@RequestMappingDescription("更新众筹回报数据并跳转到下一步")
	protected String doUpdateVoNext(CrowdfundingReturn vo,@RequestParam MultiValueMap<String, Object> data,RedirectAttributes redirectAttributes) {	

		CrowdfundingReturn newVo=super.doUpdateVo(vo, data);
		return redirectTo("/seller/crowdfunding/"+newVo.getCrowdfundingId()+"/sponsor.htm");
	}
}
