package com.rongji.cms.emall.support.filter;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rongji.dfish.base.Utils;
import com.rongji.dfish.platform.sso.service.impl.SsoClientJsonImpl;
import com.rongji.sso.core.commons.ThreadLocalFactory;
import com.rongji.sso.core.security.SecurityServiceFactory;
import com.rongji.sso.core.utils.SSOUtils;

public class DFishSSOFilter implements Filter{
	
	private static String serverPage;
	private String serverInterface;
	private String account;
	private String password;
	private String sysMark;
	SsoClientJsonImpl ssoClinet;
	public static boolean hasConfig = false;
	public static boolean hasRefreshXML=false;
	
	public void destroy() {	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
	    ThreadLocalFactory.setThreadLocalRequest((HttpServletRequest)servletRequest);
	    ThreadLocalFactory.setThreadLocalResponse((HttpServletResponse)servletResponse);
		
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		String uri=request.getRequestURI();
		if (request.getContextPath().length() > 0 && uri.startsWith(request.getContextPath())) {
			uri = uri.substring(request.getContextPath().length());
		}

		String relogin = request.getParameter("relogin");
		if(Utils.notEmpty(relogin) && "1".equals(relogin)){
			request.getSession().removeAttribute(LOGIN_USER_KEY);
			
			SecurityServiceFactory.getSessionService().invalidate();
			//退出登录，去统一sso获取登录信息
			String serverHost = SecurityServiceFactory.getSSOService().getServerHost();
			String redirectUrl=serverHost+"/sso_ws/getLogin?";
//			if(Utils.requestFromDFish(request)) {//ajax请求时，js跳转
//				redirectUrl+="&host="+SSOUtils.getRequestHostAndProjectName(true)+"&path=/admin.jsp";
//				outPutXML(response, new JSCommand("reload", "window.location.replace(\""+redirectUrl+"\");"));
//			}else{
				redirectUrl+="&host="+SSOUtils.getRequestHostAndProjectName(true)+"&path=/";
				response.sendRedirect(redirectUrl);
//			}
			
		}
		int lastIndex=uri.lastIndexOf("/");
		String shortUri=lastIndex<0?uri:uri.substring(lastIndex+1);
		if (shortUri.contains("initConfig.sp") || shortUri.contains("initConfig.jsp")) {
			chain.doFilter(request, response);
			return;
		}
		if(!hasConfig){
			serverPage = "http://192.168.21.199:8888";
			serverInterface = "http://192.168.21.199:8888/dfish-servman";
			ssoClinet.setPlatformUrl(serverInterface);
			ssoClinet.setEndpoints(serverPage);
			account = "DFISH";
			password = "123ABC";
			ssoClinet.setAccount(account);
			ssoClinet.setPassword(password);
			
			modifyXMLFile(serverInterface,"DFISH","123ABC");
			
			hasConfig = true;
		}
		if(exceptURIs.contains(uri)||exceptURIs.contains(shortUri) || checkUri(uri) ){
			chain.doFilter(request, response);
			return;
		}
		
		//获取登录用户ID
		String userId = SecurityServiceFactory.getSessionService().getLoginUserId();
		
		if (userId != null) {
			request.getSession().setAttribute(LOGIN_USER_KEY, userId);
			chain.doFilter(request, response);
			return;
		}
		
		
		
		String curUrl=request.getRequestURL().toString();
		String queryString = request.getQueryString();
		String fullPath = curUrl + queryString;
//		String stock=request.getParameter("DFISH_STOCK");
//		if(queryString != null && queryString.length() > 0){
//			if(queryString.contains("relogin=1")){
//				queryString = queryString.replace("relogin=1", "");
//			}
//			queryString = replaceStock(queryString);
//			if(Utils.notEmpty(queryString)){
//				queryString = "?"+queryString.toString();
//			}
//		}else{
//			queryString = "";
//		}
//		fullPath = curUrl + queryString;
//		if(stock!=null&&!stock.equals("")){
//			try {
//				PubUser user = ssoClinet.getPubUserByStock(stock);
//				String userIdFormSSOserver=null;
//				if(user==null){
//					response.sendError(HttpServletResponse.SC_FORBIDDEN);
//					return;
//				}
//				userIdFormSSOserver	= user.getUserId();
//				if(userIdFormSSOserver!=null&&!userIdFormSSOserver.equals("")){
//					if("SYSTEM".equals(userIdFormSSOserver) || 
//							ssoClinet.isUserAppSystem(userIdFormSSOserver,sysMark)){
//						request.getSession().setAttribute(LOGIN_USER_KEY,userIdFormSSOserver);
//						//chain.doFilter(request, response);
//						response.sendRedirect(fullPath);
//						return;
//					}
//					response.sendError(HttpServletResponse.SC_FORBIDDEN);
//					return;
//				}
//			}catch (DfishException ex) {
//				ex.printStackTrace();
//				response.sendError(HttpServletResponse.SC_FORBIDDEN);
//				return;
////			}catch (IOException ex) {
////				ex.printStackTrace();
////				request.getSession().setAttribute(LOGIN_USER_KEY,"SYSTEM");
////				chain.doFilter(request, response);
////				return ;			
//			}catch (Exception ex) {
//				ex.printStackTrace();
//				response.sendError(HttpServletResponse.SC_FORBIDDEN);
//				return;
//			}
//		}
	
//		String redirectUrl=serverPage+"/login.sp?act=index&redirect="+
//			java.net.URLEncoder.encode(fullPath, "UTF-8");
//			HttpGet getMethod = new HttpGet(serverPage);
//			HttpClient client=new DefaultHttpClient();
//			HttpResponse status = client.execute(getMethod);
//			
////			if(status.getStatusLine().getStatusCode()!=200){
////				//request.getSession().setAttribute(LOGIN_USER_KEY,"SYSTEM");
////				response.sendRedirect(fullPath);
////				//chain.doFilter(request, response); 0312改
////				return;
////			}
//		response.sendRedirect(redirectUrl);
			
			
		//未登录，去统一sso获取登录信息
		String serverHost = SecurityServiceFactory.getSSOService().getServerHost();
		String redirectUrl=serverHost+"/sso_ws/getLogin?";
//		if(Utils.requestFromDFish(request)) {//ajax请求时，js跳转
//			redirectUrl+="&host="+SSOUtils.getRequestHostAndProjectName(true)+"&path=/admin.jsp";
//			outPutXML(response, new JSCommand("reload", "window.location.replace(\""+redirectUrl+"\");"));
//		}else{
			redirectUrl+="&host="+SSOUtils.getRequestHostAndProjectName(true)+"&path="+SSOUtils.getRequestPath(true);
			response.sendRedirect(redirectUrl);
//		}
			
	}
	public static final String LOGIN_USER_KEY="com.rongji.dfish.LOGIN_USER_KEY";
	private HashSet<String> exceptURIs=new  HashSet<String>();
	private HashSet<String> otherURIs=new  HashSet<String>();
	public void init(FilterConfig config) throws ServletException {
		serverPage=config.getInitParameter("SSO_SERVER_PAGE");
		serverInterface=config.getInitParameter("SSO_SERVER_INTERFACE");
		account=config.getInitParameter("ACCOUNT");
		password=config.getInitParameter("PASSWORD");
		String exceptURI=config.getInitParameter("exceptURI");
		sysMark = config.getInitParameter("SYSMARK");
		if(exceptURI!=null){
			for(String uri:exceptURI.split(";")){
				if(uri!=null&&!uri.trim().equals("")){
					exceptURIs.add(uri.trim());
				}
				if(uri!=null && uri.endsWith("*")){
					otherURIs.add(uri.trim().substring(0, uri.length()-1));
				}
			}
		}
		ssoClinet = new SsoClientJsonImpl();
		ssoClinet.setPlatformUrl(serverInterface);
		ssoClinet.setAccount(account);
		ssoClinet.setPassword(password);
	}	
	public static String getLogoutUrl(HttpServletRequest request) {
		String curUrl = request.getParameter("redirect");
		StringBuilder sb = new StringBuilder();
		sb.append(serverPage + "/login.sp?act=logout");
		if (curUrl != null && curUrl.length() > 0) {
			sb.append("&redirect=" + curUrl + "");
		}
		return sb.toString();
	}
	
	public boolean checkUri(String uri) {
		for(String exp:otherURIs) {
			if(uri.startsWith(exp)){
				return true;
			}
		}
		return false;
		
	}
	
	public static String replaceStock(String queryString){
		if(queryString == null || queryString.length() == 0 )return "";
		int a = queryString.indexOf("DFISH_STOCK");
		int e = 1;
		if(a != -1){
			if(a == 0 ){ 
				e = 0;
			}
			String b  = a == 0 ? "":String.valueOf(queryString.charAt(a-e));
			int c = queryString.indexOf("&", a);
			if(c != -1){
			String d = 	queryString.substring(a-e, c+1);
			queryString = queryString.replace(d, b);
			}else{	
				String d = 	queryString.substring(a-e);
				queryString = queryString.replace(d,"");
			}
		}
		return queryString;
	}
	/****
	 * 读取此时的applicationContext,与要设置的值比较,如果有不同,则修改,并且重新启动beanFactory
	 * @param platformUrl	servman平台地址
	 * @param account		账号
	 * @param pwd			密码
	 */
	public static void modifyXMLFile(String platformUrl,String account,String pwd){
		
		
	}

}
