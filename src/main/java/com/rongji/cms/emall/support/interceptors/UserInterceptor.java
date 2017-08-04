package com.rongji.cms.emall.support.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rongji.rjskeleton.support.interceptor.HandlerOrderedInterceptor;

/**
 * 进行用户权限信息等校验
 *
 * @since 2015-6-8
 * @author rjf
 *
 */
public class UserInterceptor extends HandlerInterceptorAdapter implements HandlerOrderedInterceptor {
	
	private int order = Integer.MAX_VALUE;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// 真实路径：/order/00000001/goods/v_page.json
		String realPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		// 匹配路径：/order/{orderId}/goods/v_page.*
		String matchingPath = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		
		return true; // 不满足条件返回false
	}

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
