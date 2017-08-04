package com.rongji.cms.emall.web.crowdfunding;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongji.cms.emall.service.crowdfunding.CrowdfundingService;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingSponsorService;
import com.rongji.cms.emall.vo.Crowdfunding;
import com.rongji.cms.emall.vo.CrowdfundingSponsor;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

@Controller
@RequestMapping("/seller/crowdfunding/{crowdfundingId}/sponsor")
public class CrowdfundingSponsorController extends CrudAndPageByJsonController<CrowdfundingSponsor> {
	
	
	@Autowired
	private CrowdfundingSponsorService crowdfundingSponsorService;
	
	@Autowired
	private CrowdfundingService crowdfundingService;


	@Override
	protected CommonService<CrowdfundingSponsor, ?> getService() {
		return crowdfundingSponsorService;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("众筹发起人信息")
	public String index(Model model,RedirectAttributes redirectAttributes) {
		String userId = getCurrUserId();
		if(userId!=null){
			String crowdfundingId = getUriVar("crowdfundingId");
			if(crowdfundingService.getCrowdfunding(userId, crowdfundingId)){
				CrowdfundingSponsor cs=crowdfundingService.getOne(crowdfundingId).getCrowdfundingSponsor();
				if(cs!=null){
					model.addAttribute("id",cs.getId());
				}
				model.addAttribute("crowdfundingSponsor",cs);	
				model.addAttribute("crowdfundingId", crowdfundingId);	
				return "/crowdfunding/sponsor";
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
//		PAGE_INFO.setLength(5);
//		PAGE_INFO.setCheckboxMode(true);
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.id, false, false, false, false);
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.sponsorName, true, true, true);			
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.sponsorHead, false, true, true);	
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.email, true, true, true);
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.phone, true, true, true);	
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.contactName, true, true, true);	
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.sponsorSummary, false, true, true);
//		PAGE_INFO.addColumn(EmlCrowdfundingSponsor_.otherInformation, false, true, true);	
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Transactional
	@RequestMapping("/vo_save_edit")
	@RequestMappingDescription("保存或编辑众筹发起人信息数据")
	protected String doSaveVo(String crowdfundingId,CrowdfundingSponsor vo,@RequestParam MultiValueMap<String, Object> data,RedirectAttributes redirectAttributes) {
		MultipartFile otherInformationFile = vo.getOtherInformationFile();
		vo.setOtherInformation(otherInformationFile.getOriginalFilename());
		MultipartFile sponsorHeadFile = vo.getSponsorHeadFile();
		vo.setSponsorHead(sponsorHeadFile.getOriginalFilename());
		if(vo.getId().equals("")){
			vo.setId(null);
			vo=super.doSaveVo(vo);
			if(vo!=null){		
				Crowdfunding crowdfunding=crowdfundingService.getOne(crowdfundingId);
				if(crowdfunding!=null){
					crowdfunding.setCrowdfundingSponsor(vo);				
					crowdfundingService.save(crowdfunding);
				}else{
					super.voDelete(vo.getId());
				}
			}
		}else{
			super.doUpdateVo(vo, data);
		}
		return redirectTo("/");
	}	
	
}
