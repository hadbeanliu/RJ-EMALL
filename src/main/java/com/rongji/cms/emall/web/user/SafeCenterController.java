package com.rongji.cms.emall.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.cms.emall.vo.User;
import com.rongji.dfish.cache.meta.StringMeta;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.BaseController;

@Controller
@RequestMapping("/user/safe")
public class SafeCenterController extends BaseController {
	
	@Autowired
	UserService userService;
	@Autowired
	MessageModelService messageModelService;
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("用户后台界面")
	public String index(Model model) {	
		return "/dsww/001/user-safe-center";
	}
	
	@RequestMapping(value="checkpwdtoemail")
	public String checkPwd() throws Exception{
			return "/dsww/001/user-safe-checkpwdtoemail";
	}
	
	@RequestMapping(value="checkpwdtomobiletel")
	public String checkPwdToMobiletel() throws Exception{
			return "/dsww/001/user-safe-checkpwdtomobiletel";
	}
	
	@RequestMapping(value="checkmobiletel")
	public String checkMobiletel() throws Exception{
		User user = userService.getUserById(getCurrUserId());
		if(user.getMobiletel()!=null&&!"".equals(user.getMobiletel())){
			return "/dsww/001/user-safe-checkmobiletel";
		}else{
			return "/dsww/001/user-safe-center";
		}
		
	}
	
	@RequestMapping(value="checkemail")
	public String checkSendEmail() throws Exception{
		User user = userService.getUserById(getCurrUserId());
		if(user.getEmail()!=null&&!"".equals(user.getEmail())){
			return "/dsww/001/user-safe-checkemail";
		}else{
			return "/dsww/001/user-safe-center";
		}
		
	}
	
	@RequestMapping(value="checknewmobiletel")
	public String checkNewMobiletel() throws Exception{
		User user = userService.getUserById(getCurrUserId());
		if(user.getMobiletel()!=null&&!"".equals(user.getMobiletel())){
			return "/dsww/001/user-safe-checknewmobiletel";
		}else{
			return "/dsww/001/user-safe-center";
		}
		
	}
	
	@RequestMapping(value="editpassword")
	public String editPassword(String password) throws Exception{
		return "/dsww/001/user-safe-editpassword";
	}
	
	@RequestMapping(value="editmobiletel")
	public String editMobiletel(String password) throws Exception{
		return "/dsww/001/user-safe-editmobiletel";
	}
	
	@RequestMapping(value="complete")
	public String complete() {
		return "/dsww/001/user-safe-complete";
	}
	
	@RequestMapping(value="sendcheckemail")
	@ResponseBody
	public boolean sendCheckEmail(String email) throws Exception{
		User user = userService.getUserById(getCurrUserId());
		int code = (int)((Math.random()*6+1)*100000);
		StringMeta sm=new StringMeta("emailCode.userId:"+getCurrUserId());
		sm.set(code+"");
		sm.expire(10000*60*60*24);
		String content = "尊敬的"+user.getNickName()+"您好！请点击已下链接进行验证："+EmallConfig.getEmallUrl()+"/user/safe/editemail.htm?code="+code+" 本邮件24小时有效";
		String title = "文玩天下邮箱验证提醒";
		if(messageModelService.sendEmail(null, email, title, content)){
			return true;
		}else{
			return false;
		}
		
	}
	
	@RequestMapping(value="sendemailcomplete")
	public String sendemailcomplete() {
		return "/dsww/001/user-safe-sendemailcomplete";
	}
	
	@RequestMapping(value="editemail")
	public String editemail(String email) throws Exception{
		String code = getRequest().getParameter("code");
		StringMeta sm=new StringMeta("emailCode.userId:"+getCurrUserId());
		if(code!=null&&code.equals(sm.get())){
			return "/dsww/001/user-safe-editemail";
		}else{
			return "/dsww/001/user-safe-failed";
		}
	}
	
	@RequestMapping(value="editnewemail")
	public String editnewemail() throws Exception{
			return "/dsww/001/user-safe-editemail";
	}
	
	@RequestMapping(value="sendnewemail")
	@ResponseBody
	public boolean sendNewEmail(String email) throws Exception{
		User user = userService.getUserById(getCurrUserId());
		int code = (int)((Math.random()*6+1)*100000);
		StringMeta sm=new StringMeta("emailCode.userId:"+getCurrUserId());
		sm.set(code+"");
		sm.expire(10000*60*60*24);
		String content = "尊敬的"+user.getNickName()+"您好！请点击已下链接进行验证："+EmallConfig.getEmallUrl()+"/user/safe/checkemailcomplete.htm?email="+email+"&code="+code+" 本邮件24小时有效";
		String title = "文玩天下邮箱验证提醒";
		if(messageModelService.sendEmail(null, email, title, content)){
			return true;
		}else{
			return false;
		}
		
	}
	
	@RequestMapping(value="checkemailcomplete")
	public String checkEmailComplete() {
		User user = userService.getUserById(getCurrUserId());
		String code = getRequest().getParameter("code");
		StringMeta sm=new StringMeta("emailCode.userId:"+getCurrUserId());
		if(code!=null&&code.equals(sm.get())){
			user.setIsActiveemail("1");
			String email = getRequest().getParameter("email");
			user.setEmail(email);
			userService.updateUser(user);
			return "/dsww/001/user-safe-complete";
		}else{
			return "/dsww/001/user-safe-failed";
		}
		
	}
	
	@RequestMapping(value="checkmobiletelcomplete")
	public String checkMobiletelComplete(String mobiletel) {
		User user = userService.getUserById(getCurrUserId());
			user.setMobiletel(mobiletel);
			userService.updateUser(user);
			return "/dsww/001/user-safe-complete";
	}
		

}
