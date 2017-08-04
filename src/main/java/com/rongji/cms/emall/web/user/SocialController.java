package com.rongji.cms.emall.web.user;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.service.social.SocialUserService;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.cms.emall.vo.Social;
import com.rongji.cms.emall.vo.User;
import com.rongji.dfish.base.Utils;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.BaseController;
import com.rongji.social.base.model.ShareContentBean;
import com.rongji.social.base.model.ShareResultBean;
import com.rongji.social.webservice.client.json.EmbeddedSocialService;
import com.rongji.social.webservice.client.json.SocialMediaAuthorizeServiceClient;
import com.rongji.social.webservice.client.json.SocialMediaRelationServiceClient;
import com.rongji.social.webservice.client.json.SocialMediaServiceClient;
import com.rongji.social.webservice.client.json.SocialService;
import com.rongji.social.webservice.client.json.SocialServiceClient;
import com.rongji.social.webservice.client.json.impl.EmbeddedSocialServiceImpl;
import com.rongji.social.webservice.client.json.impl.SocialMediaAthorizeServiceImpl;
import com.rongji.social.webservice.client.json.impl.SocialMediaRelationServiceImpl;
import com.rongji.social.webservice.client.json.impl.SocialMediaServiceImpl;
import com.rongji.social.webservice.client.json.impl.SocialServiceImpl;
import com.rongji.social.webservice.client.json.impl.SocialServicetImpl;
import com.rongji.social.webservice.domain.WsSocialMedia;
import com.rongji.social.webservice.domain.WsSocialMediaAuthorize;
import com.rongji.social.webservice.domain.WsSocialMediaRelation;
import com.rongji.social.webservice.type.AuthType;
import com.rongji.sso.core.security.SecurityServiceFactory;
import com.rongji.sso.core.utils.SSOUtils;


@Controller
@RequestMapping("/user/social")
public class SocialController extends BaseController  {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SocialUserService socialUserService;

	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("社会化媒体注入")
	public String index() {
		return "/dsww/001/user-mysocial";
	}

	SocialMediaRelationServiceClient socialMediaRelationService = new SocialMediaRelationServiceImpl("http://192.168.14.202/social/");
	SocialServiceClient socialService = new SocialServiceImpl("http://192.168.14.202/social/");
	SocialMediaAuthorizeServiceClient socialMediaAuthorizeService = new SocialMediaAthorizeServiceImpl("http://192.168.14.202/social/");

	private static final String CALL_BACK_URL =EmallConfig.getEmallUrl() +"/user/social/callbackSocial.htm";
	private static final String CALL_BACK_SHAREURL = EmallConfig.getEmallUrl()+"/user/social/callbackShareSocial.htm";
	//private static final String CALL_BACK_URL ="http://localhost:8080/emall/user/social/callbackSocial.htm";
	//private static final String CALL_BACK_SHAREURL = "http://localhost:8080/emall/user/social/callbackShareSocial.htm";
	SocialMediaServiceClient socialmediaservice = new SocialMediaServiceImpl("http://192.168.14.202/social/");
	SocialService socialservice = new SocialServicetImpl("http://192.168.14.202/social/");
	
	
	/**
	 * 如果是JsonP的格式访问，则返回Jsonp格式，或则返回Json格式
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	private String append4jsonP(HttpServletRequest request, String json) {
		String callBack = Utils.getParameter(request, "callback");
		if (Utils.notEmpty(callBack)) {
			return callBack + "(" + json + ")";
		}
		return json;

	}

	@RequestMapping(value="vo_getMedie")
	@ResponseBody
	public String getMedie(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("count");
		String pId= request.getParameter("pId");
		String refId= request.getParameter("refId");
		String socialRandom = RandomStringUtils.randomAlphanumeric(6);
		Map<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		List<Object> availableMedia = new ArrayList<Object>(5);
		List<WsSocialMediaRelation> allCsmr=null;
		// 获取所有支持的评论媒体
		//System.out.println("http://192.168.14.202/social/");
	  try {
		 allCsmr = socialMediaRelationService.getAllPsmrByRefId(refId,pId);
	  } catch (Exception e) {
		e.printStackTrace();
	  }
			
		if(allCsmr!=null){

			for (WsSocialMediaRelation cmp : allCsmr) {
					Map<String, String> mapMedia = new HashMap<String, String>();
					String href = socialService.getAuthorizeUrl(cmp.getPsmrID(), AuthType.CMS, CALL_BACK_URL, socialRandom,request);
					String type = cmp.getMediaFlag();
					String mediaName = cmp.getWsSocialMedia().getMediaName();
					mapMedia.put("socialType", type);
					mapMedia.put("socialHref", href);
					mapMedia.put("mediaName", mediaName);
					availableMedia.add(mapMedia);
			  }
		}
		map.put("availableMedia", availableMedia);
		//Utils.outPutJSON(response, append4jsonP(request, gson.toJson(map)));
		return gson.toJson(map);
	}
	
	@RequestMapping(value="vo_getMedieByUser")
	@ResponseBody
	public String getMedieByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("count");
		//String userName= "00010157";
		String userName = getCurrUserId();
		String pId= "00000005";
		String refId= "emall";
		if(Utils.isEmpty(userName)){
			userName="asss";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		List<Object> availableMedia = new ArrayList<Object>(5);
		List<WsSocialMediaRelation> allCsmr=null;
		// 获取所有支持的评论媒体
		//System.out.println("http://192.168.14.202/social/");
	  try {
		 allCsmr = socialMediaRelationService.getAllPsmrByRefId(refId,pId);
	  } catch (Exception e) {
		e.printStackTrace();
	  }
			
		if(allCsmr!=null){

			for (WsSocialMediaRelation cmp : allCsmr) {
				  WsSocialMediaAuthorize csma=socialMediaAuthorizeService.getPsmaByRefIdAndPsmrId(userName, cmp.getPsmrID());
					int status=socialMediaAuthorizeService.checkStatus(csma);
					Map<String, String> mapMedia = new HashMap<String, String>();
					String href = socialService.getAuthorizeUrl(cmp.getPsmrID(), AuthType.CMS, CALL_BACK_SHAREURL, userName,request);
					String type = cmp.getMediaFlag();
					String mediaName = cmp.getWsSocialMedia().getMediaName();
					if(csma.getPsmaExpireTime()!=null){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						String expireTime = dateFormat.format(csma.getPsmaExpireTime());
						String head = csma.getPsmaPic();
						String userNickName = csma.getPsmaNickname();
						mapMedia.put("expire", expireTime);
						mapMedia.put("head", head);
						mapMedia.put("nickName", userNickName);
					}
					mapMedia.put("socialType", type);
					mapMedia.put("socialHref", href);
					mapMedia.put("mediaName", mediaName);
					mapMedia.put("status", status+"");
					if(status==-1){
						
					}else if(status==0){
						mapMedia.put("csmaId", csma.getPsmaID());
					}else{
						mapMedia.put("csmaId", csma.getPsmaID());
					}
					availableMedia.add(mapMedia);
			  }
		}
		map.put("availableMedia", availableMedia);
		//Utils.outPutJSON(response, append4jsonP(request, gson.toJson(map)));
		return gson.toJson(map);
	}
	
	@RequestMapping(value="vo_outline")
	@ResponseBody
	public ModelAndView outlined(HttpServletRequest request, HttpServletResponse response)  {
		String cmsaId= request.getParameter("csmaId");
		String a=socialMediaAuthorizeService.delPsma(cmsaId);
		//Utils.outPutTEXT(response, a);
		return null;
		
	}
	public ModelAndView getExtUrl(HttpServletRequest request, HttpServletResponse response)  {
		String mediaFlag = request.getParameter("mediaFlag");
		String url = request.getParameter("url");
		
		String result=socialservice.getExtUrl(mediaFlag, url);
		result=	URLDecoder.decode(result);
		//Utils.outPutTEXT(response, result);
		return null;
		
	}

	public ModelAndView shareMedie(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String csmaId= request.getParameter("csmaId");
		String title= request.getParameter("title");
		String titleUrl= request.getParameter("titleUrl");
		String siteUrl= request.getParameter("siteUrl");
		String connent= request.getParameter("connent");
		
		WsSocialMediaAuthorize cmsa=socialMediaAuthorizeService.getPsmaById(csmaId);
		if (cmsa != null) {
			ShareContentBean contentBean = new ShareContentBean();

			contentBean.setMessage(connent);
			contentBean.setSiteUrl(siteUrl);
			contentBean.setTitle(title);
			contentBean.setTitleUrl(titleUrl);
			//本地分享
			EmbeddedSocialService service = new EmbeddedSocialServiceImpl();
			//ShareResultBean result =  service.share(csma, null, contentBean);
			//远程分享
			ShareResultBean result = socialService.share(csmaId, contentBean);
			System.out.println(result.getCode());
			//Utils.outPutTEXT(response, ""+result.getCode());
		}else{
			//Utils.outPutTEXT(response, "fail");
		}
		return null;
		
	}
	public ModelAndView oneTimeShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String content = request.getParameter("content");
		String csmaIds= request.getParameter("csmaId");
		String[]  csmaId=null;
		String result="fail";
		if(!Utils.isEmpty(csmaIds)){
			csmaId=csmaIds.split(",");
		}else{	
			result="fail";
		}
	    
		if(csmaId!=null){
		for (int i = 0; i < csmaId.length; i++) {
			WsSocialMediaAuthorize cmsa=socialMediaAuthorizeService.getPsmaById(csmaId[i]);
			if(cmsa!=null){
				ShareContentBean contentBean = new ShareContentBean();
				contentBean.setMessage(content);
				ShareResultBean results = socialService.share(csmaId[i], contentBean);
				if(results.getCode()!=0){
					result="fail";
					break;
				}else{
					result="0";
				}
			}
			
			}
		}
		//Utils.outPutHTML(response, result);
		return null;
	}
	public ModelAndView share(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String content = request.getParameter("content");
		String mediaFlag = request.getParameter("mediaFlag");
		Object obj = request.getSession().getAttribute(mediaFlag);
		WsSocialMediaAuthorize csma = obj == null ? null : (WsSocialMediaAuthorize) obj;
		if (csma != null) {
			ShareContentBean contentBean = new ShareContentBean();

			contentBean.setMessage(content);
			contentBean.setSiteUrl("http://localhost:8080");
			contentBean.setTitle("fenx");
			contentBean.setTitleUrl("http://localhost:8080?_=" + System.currentTimeMillis());
			//本地分享
			EmbeddedSocialService service = new EmbeddedSocialServiceImpl();
			//ShareResultBean result =  service.share(csma, null, contentBean);
			//远程分享
			//ShareResultBean result = socialService.share(csma.getWsSocialMediaRelation().getPsmrID(), csma.getPsmaRefID(), contentBean);
			//System.out.println(result.getCode());
			//Utils.outPutHTML(response, ""+result.getCode());
		}else{
			//Utils.outPutHTML(response, "fail");
		}
		
		return null;
	
	}
	
	public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String csmaId = request.getParameter("csmaId");
		WsSocialMediaAuthorize cmsa=socialMediaAuthorizeService.getPsmaById(csmaId);
		Gson gson = new Gson();
		//Utils.outPutJSON(response, append4jsonP(request, gson.toJson(cmsa)));
		return null;
		
	}
	
	/**
	 * 社会化成功登陆后回调的地址页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/callbackSocial")
	public String callback4Social(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		String mediaFlag = request.getParameter("mediaFlag");
		String s = request.getParameter("psmaRefID");
		String psmrID = request.getParameter("cmsrId");
		System.out.println(mediaFlag);
		WsSocialMediaRelation psmr = socialMediaRelationService.getPsmrById(psmrID);
		System.out.println(psmr.getPsmrID());
		String psmaRefID = request.getParameter("psmaRefID");
		WsSocialMediaAuthorize cmsa = socialMediaAuthorizeService.getPsmaByRefIdAndPsmrId(psmaRefID, psmr.getPsmrID());
		Social social=new Gson().fromJson(cmsa.getPsmaExtend(), Social.class);
		model.addAttribute("psmaRefID", psmaRefID);
		model.addAttribute("psmrID", psmrID);
		model.addAttribute("socialUserId", social.getUserID());
		System.out.println(psmaRefID);
		System.out.println(psmrID);
		System.out.println(social.getUserID());
		String psmaNickname = request.getParameter("psmaNickname");
		String psmaPic = request.getParameter("psmaPic");
		WsSocialMedia sub = socialmediaservice.getPubSocialMediaByFlag(mediaFlag);
		if (psmaNickname != null) {
			cmsa.setPsmaNickname(psmaNickname);
			cmsa.setPsmaPic(psmaPic);
			psmr.setMediaFlag(mediaFlag);
			cmsa.getWsSocialMediaRelation().setWsSocialMedia(sub);
			request.getSession().setAttribute(mediaFlag, cmsa);
			Map<String, String> userInfo = new HashMap<String, String>();
			userInfo.put("Username", cmsa.getPsmaNickname());
			userInfo.put("headImg", cmsa.getPsmaPic());
			//Utils.outPutHTML(response, js1);
		}
		User user = new User();
		user.setLoginName(social.getUserID());
		user.setHead(cmsa.getPsmaPic());
		user.setNickName(cmsa.getPsmaNickname());
		String userId = socialUserService.addSocialUser(user, social);
//		SecurityServiceFactory.getSessionService().setLoginUserId(userId);
//		String token = SecurityServiceFactory.getSessionService().getJSessionId(false);
//		String redirect = SecurityServiceFactory.getSSOService().getServerHost()+"/sso_ws/setLogin?r=1&v="+token+"&host="+SSOUtils.getRequestHost(true)+"&path=/reg/socialreg";
		//user.set
		//userService.addNoActiveUser(user);
		return "/reg/socialreg";
	}

	/**
	 * 社会化分享成功后回调的地址页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/callbackShareSocial")
	public String callbackShareSocial(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {

		return "/dsww/001/user-mysocial";
	}
	


	

	






	


}
