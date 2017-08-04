package com.rongji.cms.emall.web.authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlCompany;
import com.rongji.cms.emall.entity.EmlCompany_;
import com.rongji.cms.emall.entity.EmlExamine;
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
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

/**
 * 认证管理 2015-6-25
 * 
 * @author lzm
 * 
 */
@Controller
@RequestMapping("/seller/authentication")
@RequestMappingDescription("卖家认证")
public class AuthenticationBySellerController extends CrudAndPageByJsonController<Company> {

	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn(EmlCompany_.id);
	}

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ExamineService examineService;
	
	@Autowired
	private UserService userService;

	@Override
	protected CommonService<Company, ?> getService() {
		return companyService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@RequestMapping(method = RequestMethod.GET)
	@RequestMappingDescription("主界面，选择界面")
	public void index(Model model) {
	}
	
	@RequestMapping("save_service")
	@RequestMappingDescription("服务认证保存")
	public Company doSaveService(Company vo) {
		String authenticationService = vo.getAuthenticationService();
		Company vos = companyService.getOne(vo.getId());//id这里为空值
		vos.setAuthenticationService(authenticationService);
		
		vos.setUserId(getCurrUserId());
		
		return super.doSaveVo(vos);
	}
	
	@Override
	protected Company doSaveVo(Company vo) {
		vo.setUserId(getCurrUserId());
		//是否一个用户对应一个企业编号
		List<EmlCompany> companieslList = companyService.getCompanyByUserId(getCurrUserId());
		if(companieslList.size() == 1){
			vo.setId(companieslList.get(0).getId());
			vo.setStatus("0");
			return companyService.update(vo);
		}

		return super.doSaveVo(vo);
	}
	
	@RequestMapping("/v_saveinfo")
	@ResponseBody
	public Company doSaveInfo(Company vo) {
		vo.setUserId(getCurrUserId());
		vo.setInformance("1");
		//是否一个用户对应一个企业编号
		List<EmlCompany> companieslList = companyService.getCompanyByUserId(getCurrUserId());
		if(companieslList.size() == 1){
			vo.setId(companieslList.get(0).getId());
			vo.setStatus("0");
//			return companyService.update(vo);
		}

		return super.doSaveVo(vo);
	}
	
	@RequestMapping("/company")
	@RequestMappingDescription("企业认证")
	public String company(Model model) {
		
		User user = getUserSession();
		
		List<EmlCompany> companieslList = companyService.getCompanyByUserId(user.getId());
		if(companieslList.size() == 1){
			EmlCompany company = companieslList.get(0);
			if("1".equals(company.getIsCompanyauthentication())){
				model.addAttribute("status", "1");
				return "/dsww/001/seller-authentication-company-result";
			}
			//正在审核个人认证不能进行企业认证
			if(isProcess("4")){//如果个人认证没有通过或者个人认证有未审核信息
				model.addAttribute("status", "error-company");
				return "/dsww/001/seller-authentication-company-result";
			}
			if("1".equals(company.getInformance())){//开店须知通过后跳转到企业认证页面
				model.addAttribute("uid", user.getId());
				model.addAttribute("realName", user.getRealName());
				return "/dsww/001/seller-authentication-company";
			}

			if("0".equals(isPass("1"))) {//待审核
				model.addAttribute("status", "0");
				return "/dsww/001/seller-authentication-company-result";
			}else if("1".equals(isPass("1"))){//审核失败
				model.addAttribute("status", "-1");
				List<EmlExamine> examine = examineService.getExamine(user.getId(), "1");
				if(examine.size() == 1){
					
					model.addAttribute("errorReason", examine.get(0).getErrorReason());
				}
				return "/dsww/001/seller-authentication-company-result";
			}
			
			model.addAttribute("companyId", companieslList.get(0).getId());
		}
//		model.addAttribute("uid", user.getId());
//		model.addAttribute("realName", user.getRealName());
//		return "/dsww/001/seller-authentication-company";
		return "/dsww/001/seller-authentication-company-information";
	}

	@RequestMapping("/service")
	@RequestMappingDescription("服务认证")
	public String service(Model model) {
		
		List<EmlCompany> companiesList = companyService.getCompanyByUserId(getCurrUserId());
		if(companiesList.size() == 0){
			model.addAttribute("status", "1");
			return "/dsww/001/seller-authentication-service-result";
			
		}else if(companiesList.size() == 1){//判断是否进行了企业认证
			String i = companiesList.get(0).getIsCompanyauthentication();
			String j = companiesList.get(0).getIsServiceauthentication();
			if("0".equals(i) || i == null){
				model.addAttribute("status", "-111");
				return "/dsww/001/seller-authentication-service-result";//您还没有进行企业认证，不能进行服务认证！
			}else{
				model.addAttribute("companyId", companiesList.get(0).getId());
			}
			
			if("1".equals(j)){
				model.addAttribute("status", "1");
				return "/dsww/001/seller-authentication-service-result";
			}
			
			if("0".equals(isPass("3"))) {//待审核
				model.addAttribute("status", "0");
				return "/dsww/001/seller-authentication-service-result";
			}else if("1".equals(isPass("3"))){//审核失败
				model.addAttribute("status", "-1");
				List<EmlExamine> examine = examineService.getExamine(getCurrUserId(), "3");
				if(examine.size() == 1){
					
					model.addAttribute("errorReason", examine.get(0).getErrorReason());
				}
				return "/dsww/001/seller-authentication-service-result";
			}
		}
			
		model.addAttribute("uid", getCurrUserId());
//		if(isPass("3")) return "/dsww/001/seller-authentication-service-result";
		return "/dsww/001/seller-authentication-service";
	}

	@RequestMapping("/to_service")
	@RequestMappingDescription("回到服务认证界面")
	public String serviceresult(Model model) {
		return "/dsww/001/seller-authentication-service";
	}
	@RequestMapping("/to_company")
	@RequestMappingDescription("回到企业认证界面")
	public String personresult(Model model) {
		return "/dsww/001/seller-authentication-company";
	}
	
	@Override
	protected Class<?>[] getValidClasses() {
		return new Class<?>[]{ com.rongji.cms.emall.web.authentication.Company.class };//PoJo
	}

	@RequestMapping("/company_validator")
	@RequestMappingDescription("获取验证表单的json数据")
	@ResponseBody
	public String getCompanyJson() {
		List<FormFieldValidDefine> list = getVoValidation(getValidClasses());
		
		String str = new Gson().toJson(list);

		return str;
	}
	
	@RequestMapping("/get_company_errorfield")
	@RequestMappingDescription("获得企业认证错误字段")	
	@ResponseBody
	public Examine getCompanyErrorField() {
		List<EmlExamine> examine = examineService.getExamine(getCurrUserId(), "1");
		if(examine.size() == 1){
			return examineService.convertToVo(examine.get(0));
		}
		return null;
	}
	
	@RequestMapping("/get_service_errorfield")
	@RequestMappingDescription("获得服务认证错误字段")
	@ResponseBody
	public Examine getServiceErrorField() {
		List<EmlExamine> examine = examineService.getExamine(getCurrUserId(), "3");
		if(examine.size() == 1){
			return examineService.convertToVo(examine.get(0));
		}
		return null;
	}
	
	/**
	 * 根据用户编号和认证指数判断该审核数据是否存在
	 * @param authentications 认证指数
	 * @return
	 */
	public String isPass(String authentications) {

		//获取审核表数据，判断审核表是否为null,是返回authentication/company
		List<EmlExamine> examinesList = examineService.getExamine(getCurrUserId(),authentications);
		if(examinesList.size() == 1) {
			if("0".equals(examinesList.get(0).getStatus())){//未审核
				return "0";
			}else{//已审核
				return "1";
			}
		}
		return null;
	}
	
	/**
	 * 根据用户编号和认证指数判断该审核数据是否存在
	 * @param authentications 认证指数
	 * @return
	 */
	@RequestMapping("/ispass")
	@RequestMappingDescription("重新认证时直接跳转页面")
	public String pass(Model model) {
		List<EmlCompany> companieslList = companyService.getCompanyByUserId(getCurrUserId());
		if(companieslList.size() == 1){
			model.addAttribute("companyId", companieslList.get(0).getId());
		}
		model.addAttribute("uid", getCurrUserId());
		String url = getRequest().getParameter("url");
		return forwardTo("/seller/authentication/to_"+url);
	}
	
	/**
	 * 判断是否已审核且审核是否是失败的
	 * 
	 * @param authentications
	 *            认证指数
	 * @return boolean
	 */
	public boolean isProcess(String authentications) {

		User user = getUserSession();
		// 获取审核表数据，判断审核表是否为null,是返回authentication/company
		List<EmlExamine> examinesList = examineService.getExamine(user.getId(),
				authentications);
		if (examinesList.size() > 0) {
			String status = "0";
			for (EmlExamine emlExamine : examinesList) {//如果每条审核信息都是已审核的，返回1
				if(emlExamine.getStatus().equals(status)){
					return true;
				}else if(emlExamine.getStatus() != null){
					return emlExamine.getStatus().contains("失败");
				}
			}
		}
		return false;
	}
	
	/**
	 * 获得用户数据（暂时提供）
	 * @param vo 用户表实体
	 * @param model
	 * @return 用户表
	 */
	@RequestMapping("/user_session")
	public User getUserSession(){
		String uids = getCurrUserId();
		//暂时设置用户uid值为00000002,管理员为00000001
		//uids = "00000002";
		return userService.getUserById(uids);
	}
}
