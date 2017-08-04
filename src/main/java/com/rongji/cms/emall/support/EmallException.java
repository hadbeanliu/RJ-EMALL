package com.rongji.cms.emall.support;

import com.rongji.rjskeleton.support.exception.SkeletonException;

/**
 * 项目统一错误
 *
 * @since 2015-5-15
 * @author rjf
 *
 */
public class EmallException extends SkeletonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5617790571627700215L;

	/**
	 * 状态码来源：{@link EmallStatusCode}
	 * 
	 * @param statusCode 状态码, 不可为空
	 */
	public EmallException(EmallStatusCode statusCode) {
		super(statusCode);
	}
	
	/**
	 * 状态码来源：{@link EmallStatusCode}
	 * 
	 * @param statusCode 状态码, 不可为空
	 * @param exception 原始异常
	 */
	public EmallException(EmallStatusCode statusCode, Exception exception) {
		super(statusCode, exception);
	}

	@Override
	public String toString() {
		return "EmallException [statusCode=" + statusCode + (statusCode != null ? ", description="+ statusCode.getDescription() : "") +"]";
	}

}
