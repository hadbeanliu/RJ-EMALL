package com.rongji.cms.emall.web.redis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.service.redis.RedisService;
import com.rongji.cms.emall.service.redis.RedisVo;
import com.rongji.dfish.cache.RedisManage;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageUseServiceByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

@Controller
@RequestMapping("/redis")
public class RedisController extends CrudAndPageUseServiceByJsonController<RedisVo, RedisService> {
	
	@RequestMapping("/createService")
	@RequestMappingDescription("创建Redis服务")
	@ResponseBody
	public String createService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String serviceProviderId = request.getParameter("serviceProviderId");
		if (notEmpty(serviceProviderId)) {
			String serviceId = RedisManage.createService(serviceProviderId, request);
			if (notEmpty(serviceId)) {
				return serviceId;
			}
		}
		
		return null;
		
	}

	@RequestMapping("/doService")
	@RequestMappingDescription("执行Redis服务")
	@ResponseBody
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String serviceId = request.getParameter("serviceId");
		if (notEmpty(serviceId)) {
			String result = RedisManage.doService(serviceId, request);
			if (notEmpty(result)) {
				return result;
			}
		}
		
		return null;
		
	}
	
	@RequestMapping("/getService")
	@RequestMappingDescription("获取Redis服务数据")
	@ResponseBody
	public String getService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String serviceId = request.getParameter("serviceId");
		if (notEmpty(serviceId)) {
			String result = RedisManage.getService(serviceId, request);
			if (notEmpty(result)) {
				return result;
			}
		}
		
		return null;
		
	}
	
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.setIdColumn("id");
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}
	
	private boolean notEmpty(String value) {
		return value != null && !value.isEmpty();
	}

}
