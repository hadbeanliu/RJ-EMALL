package com.rongji.cms.emall.web.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.service.social.SocialUserService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.filter.DFishSSOFilter;
import com.rongji.cms.emall.vo.Social;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.support.mvc.RequestUtil;
import com.rongji.rjskeleton.support.valid.FormFieldValidDefine;
import com.rongji.rjskeleton.web.controller.BaseController;
import com.rongji.social.webservice.client.json.SocialMediaAuthorizeServiceClient;
import com.rongji.social.webservice.client.json.impl.SocialMediaAthorizeServiceImpl;
import com.rongji.sso.core.security.SecurityServiceFactory;
import com.rongji.sso.core.utils.SSOUtils;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SocialUserService socialService;
	
	SocialMediaAuthorizeServiceClient socialMediaAuthorizeService = new SocialMediaAthorizeServiceImpl("http://192.168.14.202/social/");
	
	static String url = "http://192.168.14.202:8980/message/index.sp?act=";
	
	protected CommonService<User, ?> getService() {
		return userService;
	}
	
//	@RequestMapping(method=RequestMethod.GET)
//	@RequestMappingDescription("用户后台界面")
//	public String index(Model model) {	
//		System.out.println(getCurrUserId());
//		return "/dsww/001/user-list";
//	}
		
	@RequestMapping(value="vo_checkName")
	@ResponseBody
	public boolean voLogin(String loginName) throws Exception{
		return userService.isUserLoginNameRepeat(loginName);
	}

	@RequestMapping(value="vo_edituser")
	@ResponseBody
	public User editUser(User vo){
		return userService.updateUser(vo);
	}
	
	@RequestMapping(value="vo_edituserhead")
	@ResponseBody
	public User editUserHead(String urlHead){
		User user = new User();
		user.setHead(urlHead);
		user.setId(getCurrUserId());
		user = userService.updateUser(user);
		return user;
	}
	
	@RequestMapping(value="vo_editpwd")
	@ResponseBody
	public String editPassword(String oldPwd,String newPwd){
		String userId= getCurrUserId();
		String a =  userService.updateUserPsd(userId, oldPwd, newPwd);
		return a;
		
	}
	
	@RequestMapping(value="vo_save")
	@ResponseBody
	protected User saveUserVo(User vo) {
		if(vo.getNickName()==null || "".equals(vo.getNickName())){
			vo.setNickName(vo.getLoginName());
		}
		userService.save(vo);
		return vo;
	}
	
	@RequestMapping(value="vo_socailsave")
	protected User saveSocailVo(@Validated User vo) {
		userService.save(vo);
    	Social social = new Social();
    	social.setUser(vo);
    	social.setId(vo.getSocialUserId());
    	socialService.save(social);
		return vo;
	}
	
	@RequestMapping(value="vo_getuser")
	@ResponseBody
	public User getUserById() {
		String userId = getCurrUserId();
		User user = userService.getUserById(userId);
		return user;
	}
	
	@RequestMapping(value="/info")
	public String getUserBasic(Model model) {
		String userId = getCurrUserId();
		model.addAttribute("userId", userId);
		User user = userService.getUserById(userId);
		Gson gson = new Gson();
		getRequest().setAttribute("vo", gson.toJson(user));
		return "/dsww/001/user-edit";
	}
	
	@RequestMapping(value="loginout")
	public String loginOut(HttpServletRequest request,String url) {
		clearCurrUser();
		String logoutUrl = DFishSSOFilter.getLogoutUrl(request);
		//response.sendRedirect(logoutUrl+"&redirect="+url);
		
		if (url == null) {
			url = request.getScheme() + "://" + request.getServerName() + (request.getServerPort() != 80 ? ":" + request.getServerPort() : "") + request.getContextPath() + "?" + request.getQueryString();
		}
//		return RequestUtil.REDIRECT_URL_PREFIX + logoutUrl+"&redirect="+url;
		
		
		SecurityServiceFactory.getSessionService().invalidate();
		//退出登录，去统一sso获取登录信息
		String serverHost = SecurityServiceFactory.getSSOService().getServerHost();
		String redirectUrl=serverHost+"/sso_ws/getLogin?";
//		if(Utils.requestFromDFish(request)) {//ajax请求时，js跳转
//			redirectUrl+="&host="+SSOUtils.getRequestHostAndProjectName(true)+"&path=/admin.jsp";
//			outPutXML(response, new JSCommand("reload", "window.location.replace(\""+redirectUrl+"\");"));
//		}else{
			redirectUrl+="&host="+SSOUtils.getRequestHostAndProjectName(true)+"&path=/";
//			response.sendRedirect(redirectUrl);
//		}
		return RequestUtil.REDIRECT_URL_PREFIX + redirectUrl;	}
	
	@RequestMapping("check/sendcode")
	@ResponseBody
	public String sendPhoneCode(String phone){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String reslut="";
		int random = (int)((Math.random()*6+1)*100000);
		String code;
		code=random+"";
		getRequest().getSession().removeAttribute("code");
		getRequest().getSession().setAttribute("code", code);
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url+"sendMes&phone="+phone+"&content="+code);  
            //System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                // 打印响应状态    
                if (entity != null) {  
                    //System.out.println("Response content: " + EntityUtils.toString(entity));
                    reslut = EntityUtils.toString(entity);
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        getRequest().getSession().setAttribute("time", System.currentTimeMillis());
		return reslut;
	}
	
	@RequestMapping("check/checkcode")
	@ResponseBody
	public String checkPhoneCode(String code){
		String temp=""+ getRequest().getSession().getAttribute("time");
		String reslut="";
		long aTime=0;
		if(temp!=null){
			try {
				aTime=Long.parseLong(temp);
			} catch (Exception e) {
				reslut="error code";
				return reslut;
			}
			
		}
		if(	System.currentTimeMillis()-aTime<80000){
			code= (String) getRequest().getParameter("code");
			//System.out.println(getRequest().getSession().getAttribute("code"));
			if(code.equals(getRequest().getSession().getAttribute("code")+"")){
				reslut="right";
			}else{
				reslut="error code";
			}}else{
				reslut="error overtime";
			}
			return reslut;
	}
	
	@RequestMapping(value="check_islogin")
	@ResponseBody
	public boolean checkIsLogin() {
		String userId = SecurityServiceFactory.getSessionService().getLoginUserId();
//		String userId = getCurrUserId();
		User user = userService.getUserById(userId);
		if(user!=null&&user.getId()!=null&&!"".equals(user.getId())){
			return true;
		}else{
			return false;
		}
	}
	
	@RequestMapping(value="checkuserpwd")
	@ResponseBody
	public boolean checkPwdByloginName(String pwd) {
		String userId = getCurrUserId();
		User user = userService.getUserById(userId);
		return userService.checkPwdByloginName(user.getLoginName(), pwd);
	}
	
	@RequestMapping(value="checkphone")
	@ResponseBody
	public boolean checkPhone(String mobiletel) {
		return userService.checkPhone(mobiletel);
	}
	
	@RequestMapping("/user_validate")
	@RequestMappingDescription("获取校验表单的json数据")
	@ResponseBody
	public String getUserVJson() {
		List<FormFieldValidDefine> list = getVoValidation(User.class);
		String str = new Gson().toJson(list);
		return str;
	}
	
}
