package com.rongji.cms.emall.web.authentication;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlCompany_;
import com.rongji.cms.emall.entity.EmlExamine;
import com.rongji.cms.emall.service.company.CompanyService;
import com.rongji.cms.emall.service.examine.ExamineService;
import com.rongji.cms.emall.service.user.UserService;
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
@RequestMapping("/buyer/authentication")
@RequestMappingDescription("买家认证")
public class AuthenticationByBuyerController extends
		CrudAndPageByJsonController<User> {

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
	protected CommonService<User, ?> getService() {
		return userService;
	}

	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@RequestMapping(method = RequestMethod.GET)
	@RequestMappingDescription("主界面，选择界面")
	public String index(Model model) {
		return "/authentication";
	}

	@RequestMapping("/savebyuser")
	@RequestMappingDescription("用户提交审核数据")
	public Examine doSaveVoByUser(Examine vo,Model model) {
		//判断审核数据在表内是否已经有未审核的数据，如果有，不做保存操作
		if("0".equals(isPass(vo.getAuthentications()))) return vo;
		
//		if (isPass(vo.getAuthentications()) == null) {// 判断是否已经在审核表内，没有ID则设置为NULL
//			vo.setId(null);
//		} else {// 如果有获取这个id
//			vo.setId(examineService
//					.getExamine(getCurrUserId(), vo.getAuthentications())
//					.get(0).getId());
//		}
		vo.setId(null);
		if (companyService.getCompanyByUserId(getCurrUserId()).size() != 0) {
			vo.setCompanyId(companyService.getCompanyByUserId(getCurrUserId())
					.get(0).getId()); 
		}
		int i = Integer.parseInt(vo.getAuthentications());
		String str = "";
		switch (i) {
		case 1:
			str = "企业认证";
			break;
		case 2:
			str = "专家认证";
			break;
		case 3:
			str = "服务认证";
			break;
		case 4:
			str = "个人认证";
			break;
		default:
			break;
		}
		vo.setDescription("登录名为：" + getUserSession().getLoginName() + "的用户提交了"
				+ str + "审核信息");
		vo.setStartTime(new Date());
		vo.setStatus("0");
		vo.setUserId(getCurrUserId());
		vo.setResults("");
		vo.setEndTime(null);
		return examineService.save(vo);
	}

	@RequestMapping("/person")
	@RequestMappingDescription("个人身份验证")
	public String person(Model model) {
		User user = getUserSession();

		if ("1".equals(user.getIsPersonauthentication())) {
			model.addAttribute("status", "1");
			return "/dsww/001/buyer-authentication-person-result";
			
		}

		if ("0".equals(isPass("4"))) {// 待审核
			model.addAttribute("status", "0");
			return "/dsww/001/buyer-authentication-person-result";
		} else if ("1".equals(isPass("4"))) {// 审核失败
			model.addAttribute("status", "-1");
			List<EmlExamine> examine = examineService.getExamine(user.getId(),
					"1");
			if (examine.size() == 1) {

				model.addAttribute("errorReason", examine.get(0).getErrorReason());
			}
			return "/dsww/001/buyer-authentication-person-result";
		}

		// 如果专家认证或者企业认证正在认证，不能进行个人认证
		EmlExamine emlExamine = null;
		emlExamine = examineService.getOne(user.getId(), "0", "1");
		if (emlExamine != null) {
			model.addAttribute("status", "-10");//正在审核企业认证，您不能进行个人身份验证
			return "/dsww/001/buyer-authentication-person-result";
		}
		emlExamine = examineService.getOne(user.getId(), "0", "2");
		if (emlExamine != null) {
			model.addAttribute("status", "-11");//正在审核专家认证，您不能进行个人身份验证
			return "/dsww/001/buyer-authentication-person-result";
		}

		model.addAttribute("uid", user.getId());
		return "/dsww/001/buyer-authentication-person";
	}

	@RequestMapping("/expert")
	@RequestMappingDescription("专家认证")
	public String expert(Model model) {
		User user = getUserSession();
		if ("1".equals(user.getIsExpertauthentication())) {
			model.addAttribute("status", "1");
			return "/dsww/001/buyer-authentication-expert-result";
		}
		//正在审核个人认证不能进行专家认证
		if(isProcess("4")){//如果个人认证没有通过或者个人认证有未审核信息
			model.addAttribute("status", "error-expert");
			return "/dsww/001/buyer-authentication-expert-result";
		}
		if ("0".equals(isPass("2"))) {// 待审核
			model.addAttribute("status", "0");
			return "/dsww/001/buyer-authentication-expert-result";
		} else if ("1".equals(isPass("2"))) {// 审核失败
			model.addAttribute("status", "-1");
			List<EmlExamine> examine = examineService.getExamine(user.getId(),
					"2");
			if (examine.size() == 1) {

				model.addAttribute("errorReason", examine.get(0).getErrorReason());
			}
			return "/dsww/001/buyer-authentication-expert-result";
		}
		model.addAttribute("uid", user.getId());
		return "/dsww/001/buyer-authentication-expert";
	}

	@RequestMapping("/to_person")
	@RequestMappingDescription("回到个人认证界面")
	public String personresult(Model model) {
		return "/dsww/001/buyer-authentication-person";
	}

	@RequestMapping("/to_expert")
	@RequestMappingDescription("回到专家认证界面")
	public String expertresult(Model model) {
		return "/dsww/001/buyer-authentication-expert";
	}

	@RequestMapping("/v_update_user")
	@RequestMappingDescription("修改用户信息")
	@ResponseBody
	public User updateUser(User user) {
		user = userService.updateUser(user);
		return user;
	}

	@RequestMapping("/user_validator")
	@RequestMappingDescription("获取验证表单的json数据")
	@ResponseBody
	public String getUserJson() {
		List<FormFieldValidDefine> list = getVoValidation(com.rongji.cms.emall.web.authentication.User.class);// 你自己的Vo类

		String str = new Gson().toJson(list);

		return str;
	}

	/**
	 * 根据用户编号和认证指数判断该审核数据是否存在
	 * 
	 * @param authentications
	 *            认证指数
	 * @return
	 */
	public String isPass(String authentications) {

		User user = getUserSession();
		// 获取审核表数据，判断审核表是否为null,是返回authentication/company
		List<EmlExamine> examinesList = examineService.getExamine(user.getId(),
				authentications);
		if (examinesList.size() > 0) {
			String status = "1";
			for (EmlExamine emlExamine : examinesList) {//如果每条审核信息都是已审核的，返回1
				if(!emlExamine.getStatus().equals(status)){
					return "0";
				}
			}
			return "1";
		}
		return null;
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
	 * 根据用户编号和认证指数判断该审核数据是否存在
	 * 
	 * @param authentications
	 *            认证指数
	 * @return
	 */
	@RequestMapping("/ispass")
	@RequestMappingDescription("重新认证时直接跳转页面")
	public String pass(Model model) {
		User user = getUserSession();
		model.addAttribute("uid", user.getId());
		String url = getRequest().getParameter("url");
		return forwardTo("/buyer/authentication/to_" + url);
	}

	@RequestMapping("/get_expert_errorfield")
	@RequestMappingDescription("获得专家认证错误字段")
	@ResponseBody
	public Examine getExpertErrorField() {
		User user = getUserSession();
		List<EmlExamine> examine = examineService.getExamine(user.getId(), "2");
		if (examine.size() == 1) {
			return examineService.convertToVo(examine.get(0));
		}
		return null;
	}

	@RequestMapping("/get_person_errorfield")
	@RequestMappingDescription("获得个人认证错误字段")
	@ResponseBody
	public Examine getPersonErrorField() {
		User user = getUserSession();
		List<EmlExamine> examine = examineService.getExamine(user.getId(), "4");
		if (examine.size() == 1) {
			return examineService.convertToVo(examine.get(0));
		}
		return null;
	}

	/**
	 * 获得用户数据（暂时提供）
	 * 
	 * @param vo
	 *            用户表实体
	 * @param model
	 * @return 用户表
	 */
	@RequestMapping("/user_session")
	public User getUserSession() {
		String uids = getCurrUserId();
		// 暂时设置用户uid值为00000002,管理员为00000001
		// uids = "00000002";
		return userService.getOne(uids);
	}

}
