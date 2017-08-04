package com.rongji.cms.emall.web.crowdfunding;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongji.cms.emall.service.crowdfunding.CrowdfundingReturnService;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingService;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingSponsorService;
import com.rongji.cms.emall.entity.EmlCrowdfunding_;
import com.rongji.cms.emall.vo.Crowdfunding;
import com.rongji.cms.emall.vo.CrowdfundingReturn;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/seller/user/crowdfunding")
public class CrowdfundingController extends CrudAndPageByJsonController<Crowdfunding> {
	
	
	@Autowired
	private CrowdfundingService crowdfundingService;

	@Autowired
	private CrowdfundingReturnService crowdfundingReturnService;
	
	@Autowired
	private CrowdfundingSponsorService crowdfundingSponsorService;
	
	@Override
	protected CommonService<Crowdfunding, ?> getService() {
		return crowdfundingService;
	}

	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("众筹表页面")
	public String index(Model model) {
		String userId = getCurrUserId();
		if(userId!=null){
			model.addAttribute("userId", userId);
        	return "/crowdfunding/index";
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
		PAGE_INFO.addColumn(EmlCrowdfunding_.id,true, true, true);		
		PAGE_INFO.addColumn(EmlCrowdfunding_.projectName, true, true, true);
		PAGE_INFO.addColumn(EmlCrowdfunding_.targetAmount, true, true, true);
		PAGE_INFO.addColumn(EmlCrowdfunding_.currentTotalFunding, true, true, true);
		PAGE_INFO.addColumn(EmlCrowdfunding_.supportNumber, true, true, true);
		PAGE_INFO.addColumn(EmlCrowdfunding_.raisingDays, true, true, true);
		PAGE_INFO.addColumn(EmlCrowdfunding_.createTime, true, true, true);
		PAGE_INFO.addColumn(EmlCrowdfunding_.crowdfundingStatus, true, true, true);		
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<Crowdfunding> getPage(DatatablesMetadata metadata) {		
//		metadata.getSearchs().add(new String[]{EmlCrowdfunding_.userId.getName(), userId});	
		String userId = getCurrUserId();
		if(userId!=null){
			metadata.addSearch(EmlCrowdfunding_.userId,userId);
		}
		return super.getPage(metadata);		
	}

	@RequestMapping("vo_save")
	@RequestMappingDescription("保存众筹数据")
	protected String doSaveVo(Crowdfunding vo,RedirectAttributes redirectAttributes,Model model) {	
		String userId = getCurrUserId();
		if(userId==null){
			return redirectTo("/user/login.htm");
		}
		MultipartFile file = vo.getProjectPictureFile();
		if(file!=null){
			vo.setProjectPicture(file.getOriginalFilename());
		}
		vo.setUserId(userId);
		Crowdfunding newVo=super.doSaveVo(vo);
	    model.addAttribute("crowdfundingId", newVo.getId());
		return redirectTo("/seller/user/crowdfunding.htm");
	}
	
	@RequestMapping("vo_save_next")
	@RequestMappingDescription("保存众筹数据并跳转到下一步")
	protected String doSaveVoNext(Crowdfunding vo,RedirectAttributes redirectAttributes,Model model) {	
		String userId = getCurrUserId();
		if(userId==null){
			return redirectTo("/user/login.htm");
		}
		MultipartFile file = vo.getProjectPictureFile();
		if(file!=null){
			vo.setProjectPicture(file.getOriginalFilename());
		}
		vo.setUserId(userId);
		Crowdfunding newVo=super.doSaveVo(vo);
	    model.addAttribute("crowdfundingId", newVo.getId());
		return redirectTo("/seller/crowdfunding/"+newVo.getId()+"/return.htm");
	}

	@RequestMapping("vo_update")
	@RequestMappingDescription("更新众筹数据")
	protected String doUpdateVo(Crowdfunding vo, @RequestParam MultiValueMap<String, Object> data,RedirectAttributes redirectAttributes,Model model) {
		String userId = getCurrUserId();
		if(userId==null){
			return redirectTo("/user/login.htm");
		}
		MultipartFile file = vo.getProjectPictureFile();
		if (file != null) {
			vo.setProjectPicture(file.getOriginalFilename());
		}	
		vo.setUserId(userId);
		super.doUpdateVo(vo, data);
		return redirectTo("/seller/user/crowdfunding.htm");
	}
	
	@RequestMapping("vo_update_next")
	@RequestMappingDescription("更新众筹数据并跳转到下一步")
	protected String doUpdateVoNext(Crowdfunding vo, @RequestParam MultiValueMap<String, Object> data,RedirectAttributes redirectAttributes,Model model) {
		String userId = getCurrUserId();
		if(userId==null){
			return redirectTo("/user/login");
		}
		MultipartFile file = vo.getProjectPictureFile();
		if (file != null) {
			vo.setProjectPicture(file.getOriginalFilename());
		}	
		vo.setUserId(userId);
		Crowdfunding newVo=super.doUpdateVo(vo, data);
		return redirectTo("/seller/crowdfunding/"+newVo.getId()+"/return.htm");
	}

	@Transactional
	@Override
	@RequestMapping("/v_del/{voId}")
	@RequestMappingDescription("删除记录")
	public void voDelete(@PathVariable String voId) {
		List<CrowdfundingReturn> crowdfundingReturn=crowdfundingReturnService.getCrowdfundingReturn(voId);
		for (CrowdfundingReturn cr : crowdfundingReturn) {
			crowdfundingReturnService.delete(cr.getId());
		}
		Crowdfunding crowdfunding=crowdfundingService.getOne(voId);
		if(crowdfunding.getCrowdfundingSponsor()!=null){
			crowdfundingSponsorService.delete(crowdfunding.getCrowdfundingSponsor().getId());
		}
		super.voDelete(voId);
	}

	@Transactional
	@Override
	@RequestMapping("/v_dels")
	@RequestMappingDescription("批量删除记录")
	public void voDeletes(@RequestParam String voIds) {
		String[]  ids = voIds.split(",");
		for (String id : ids) {
			List<CrowdfundingReturn> crowdfundingReturn=crowdfundingReturnService.getCrowdfundingReturn(id);
			for (CrowdfundingReturn cr : crowdfundingReturn) {
				crowdfundingReturnService.delete(cr.getId());
			}
			Crowdfunding crowdfunding=crowdfundingService.getOne(id);
			if(crowdfunding.getCrowdfundingSponsor()!=null){
				crowdfundingSponsorService.delete(crowdfunding.getCrowdfundingSponsor().getId());
			}
		}
		super.voDeletes(voIds);
	}
	
	
	
}
