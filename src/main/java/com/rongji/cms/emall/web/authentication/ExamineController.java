package com.rongji.cms.emall.web.authentication;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlExamine_;
import com.rongji.cms.emall.service.company.CompanyService;
import com.rongji.cms.emall.service.examine.ExamineService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Company;
import com.rongji.cms.emall.vo.Examine;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.support.valid.FormFieldValidDefine;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 认证管理 2015-6-9
 * 
 * @author lzm
 * 
 */
@Controller
@RequestMapping("admin/authentication/examine")
public class ExamineController extends CrudAndPageByJsonController<Examine> {


	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlExamine_.id);
		//PAGE_INFO.addColumn(EmlExamine_.id);
		PAGE_INFO.addColumn(EmlExamine_.companyId);
		PAGE_INFO.addColumn(EmlExamine_.authentications);
		PAGE_INFO.addColumn(EmlExamine_.userId);
		PAGE_INFO.addColumn(EmlExamine_.startTime);
		PAGE_INFO.addColumn(EmlExamine_.endTime);
		PAGE_INFO.addColumn(EmlExamine_.status);
		PAGE_INFO.addColumn(EmlExamine_.results);
	}

	@RequestMapping("/del/{Id}")
	@Transactional
	public String delete(Model model) {
    	String Id = getUriVar("Id");
    	if(Id != null){
        	examineService.delete(Id);
    	}
        return "/dsww/001/admin-authentication-examine-info";
	}
	
	@Override
	protected Examine doSaveVo(Examine vo) {
		vo = examineService.getOne(vo.getId());
		vo.setStartTime(new Date());
		vo.setStatus("0");
		//vo.setUserId(getCurrUserId());
		vo.setResults("");
		vo.setEndTime(null);
		return super.doSaveVo(vo);
	}

	@Override
	protected PageEntity<Examine> getPage(DatatablesMetadata metadata) {
		if(userService.isAdmin(getCurrUserId())) {//管理员只能查询到未审核(0)的数据
			if("".equals(metadata.getSearch())){
				
				metadata.addSearch(EmlExamine_.status.getName(), "0");
			}
			return super.getPage(metadata);
		}
		metadata.addSearch(EmlExamine_.userId.getName(), "null");
		return super.getPage(metadata);
		
	}

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ExamineService examineService;
	
	@Autowired
	private UserService userService;

	@Override
	protected CommonService<Examine, ?> getService() {
		return examineService;
	}
	
	@RequestMapping("/info")
	@RequestMappingDescription("审核信息界面")
	public String info(Model model) {
		return "/dsww/001/admin-authentication-examine-info";
	}
	
	@RequestMapping("/lookCompany")
	@RequestMappingDescription("审查企业信息")
	public String lookCompany(Examine vo,Model model) {
		if(!userService.isAdmin(getCurrUserId())) {
			return redirectTo("/user/loginout");
		}
		vo = examineService.getOne(vo.getId());
		if(vo == null){
			model.addAttribute("message", "已经没有这条审核数据了！请确认数据是否已删除或联系开发商协助。");
			return "/dsww/001/buyer-seller-authentication-message";
		}//有可能是被删除掉了
		if(vo.getCompanyId() == null)
			try {
				throw new Exception("公司编号companyId为空了！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		model.addAttribute("company", companyService.getOne(vo.getCompanyId()));
		model.addAttribute("id", vo.getId());
		
		return "/dsww/001/admin-authentication-examine-lookCompany";
	}
	
	@RequestMapping("/lookExpert")
	@RequestMappingDescription("审核专家信息")
	public String lookExpert(Examine vo,Model model) {
		if(!userService.isAdmin(getCurrUserId())) {
			return redirectTo("/user/loginout");
		}
		vo = examineService.getOne(vo.getId());
		User user = userService.getOne(vo.getUserId());
		model.addAttribute("user", user);
		model.addAttribute("id", vo.getId());
		
		return "/dsww/001/admin-authentication-examine-lookExpert";
	}
	
	@RequestMapping("/lookService")
	@RequestMappingDescription("审核服务认证信息")
	public String lookService(Examine vo,Model model) {
		if(!userService.isAdmin(getCurrUserId())) {
			return redirectTo("/user/loginout");
		}
		vo = examineService.getOne(vo.getId());
		model.addAttribute("company", companyService.getOne(vo.getCompanyId()));
		model.addAttribute("id", vo.getId());
		return "/dsww/001/admin-authentication-examine-lookService";
	}
	
	@RequestMapping("/lookPerson")
	@RequestMappingDescription("审核个人身份信息")
	public String lookPerson(Examine vo,Model model) {
		if(!userService.isAdmin(getCurrUserId())) {
			return redirectTo("/user/loginout");
		}
		vo = examineService.getOne(vo.getId());
		User user = userService.getOne(vo.getUserId());
		model.addAttribute("user", user);
		model.addAttribute("id", vo.getId());
		
		return "/dsww/001/admin-authentication-examine-lookPerson";
	}
	
	@RequestMapping("/save_examine_expert")
	@RequestMappingDescription("审核人保存专家认证审核数据")
	public Examine doSaveExpert(Examine vo) {
		Examine vos = getSettedVo(vo);
		//在管理员审核通过时，设置相关表的认证标识为1，这里只规定管理员对未审核的数据进行审核，审核后不能在进行审核
		if(vos.getResults().contains("通过")){
			User users = userService.getOne(vos.getUserId());
			users.setIsExpertauthentication("1");
			//进行了专家认证，个人认证也就认证了
			users.setIsPersonauthentication("1");
			userService.update(users);
		}
			
		return super.doSaveVo(vos);
	}
	
	@RequestMapping("/save_examine_service")
	@RequestMappingDescription("审核人保存服务认证审核数据")
	public Examine doSaveService(Examine vo) {
		Examine vos = getSettedVo(vo);
		if(vos.getResults().contains("通过")){
			Company company = companyService.getOne(vos.getCompanyId());
			company.setIsServiceauthentication("1");
			companyService.update(company);
		}
			
		return super.doSaveVo(vos);
	}
	
	@RequestMapping("/save_examine_person")
	@RequestMappingDescription("审核人保存个人认证审核数据")
	public Examine doSavePerson(Examine vo) {
		Examine vos = getSettedVo(vo);
		if(vos.getResults().contains("通过")){
			User users = userService.getOne(vos.getUserId());
			users.setIsPersonauthentication("1");
			userService.update(users);
		}
			
		return super.doSaveVo(vos);
	}
	
	@RequestMapping("/save_examine_company")
	@RequestMappingDescription("审核人保存企业认证审核数据")
	public Examine doSaveCompany(Examine vo) {
		Examine vos = getSettedVo(vo);
		if(vo.getResults().contains("通过")){
			User users = userService.getOne(vos.getUserId());
			Company company = companyService.getOne(vos.getCompanyId());
			company.setIsCompanyauthentication("1");
			companyService.update(company);
			//进行了企业认证，个人认证也就认证了
			users.setIsPersonauthentication("1");
			//在保存企业的同时保存用户
//			users.setRealName(company.getLegalPerson());
//			users.setCardId(company.getLegalCardId());
//			users.setFrontCard(company.getLegalFrontcard());
//			users.setBackCard(company.getLegalBackcard());
//			userService.update(users);
		}
		
		return super.doSaveVo(vos);
	}

	private Examine getSettedVo(Examine vo) {

		String id = vo.getId();
		String results = vo.getResults();
		String errorField = vo.getErrorField();
		String errorReason = vo.getErrorReason();
		String companyId = vo.getCompanyId();//如果有公司的话

		vo = examineService.getOne(id);
		vo.setEndTime(new Date());
		vo.setStatus("1");
		vo.setResults(results);
		vo.setErrorField(errorField);
		vo.setCompanyId(companyId);
		vo.setErrorReason(errorReason);
		return vo;
	}

	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@RequestMapping("/examine_validate")
	@RequestMappingDescription("获取验证表单的json数据")
	@ResponseBody
	public String getExamineVJson() {
		List<FormFieldValidDefine> list = getVoValidation(Examine.class);// 你自己的Vo类
		String str = new Gson().toJson(list);
		return str;
	}

	/**
	 * 获取用户session
	 * @return 用户对象
	 */
	public User getUserSession(){
		String uids = getCurrUserId();
		//暂时设置用户uid值为00000002,管理员为00000001
		//uids = "00010207";//管理员 adminEmall abc123
		return userService.getUserById(uids);
	}
}
