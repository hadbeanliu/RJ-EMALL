package com.rongji.cms.emall.support;

import com.rongji.rjskeleton.support.exception.SkeletonStatusCode;
import com.rongji.rjskeleton.support.exception.SkeletonStatusCodes;

/**
 * 项目的出错及提示信息列表
 *
 * @author rjf
 * @since 2015-5-14
 *
 */
public class EmallStatusCode extends SkeletonStatusCodes {

	private static final String NAME = "EMALL_";

	/**
	 * 数据库出错
	 */
	public static final EmallStatusCode ERROR_DB = new EmallStatusCode(NAME+"DB");
	
	
	/**
	 * 服务层出错
	 */
	public static final EmallStatusCode ERROR_SERVICE = new EmallStatusCode(NAME+"SV");
	
	
	/**
	 * 数据错误
	 */
	public static final EmallStatusCode ERROR_DATA = new EmallStatusCode(NAME+"DT");
	
	/**
	 * 请求错误
	 */
	public static final EmallStatusCode ERROR_REQUEST = new EmallStatusCode(NAME+"RQ");
	
	
	/**
	 * 请求不支持，
	 */
	public static final EmallStatusCode ERROR_REQUEST_NOT_SUPPORT = new EmallStatusCode(ERROR_REQUEST, "NOT_SUPPORT");
	
	/**
	 * 请求无权限
	 */
	public static final EmallStatusCode ERROR_REQUEST_NO_PERMISSION = new EmallStatusCode(ERROR_REQUEST, "NO_PERMISSION");
	
	
	
	
	/**
	 * 数据为空
	 */
	public static final EmallStatusCode ERROR_DATA_EMPTY = new EmallStatusCode(ERROR_DATA, "EMPTY");
	
	/**
	 * 数据已存在
	 */
	public static final EmallStatusCode ERROR_DATA_EXIST = new EmallStatusCode(ERROR_DATA, "EXISTED");
	/**
	 * 数据不可改变
	 */
	public static final EmallStatusCode ERROR_DATA_NOT_CHANGE = new EmallStatusCode(ERROR_DATA, "NOT_CHANGE");
	
	/**
	 * 用户不存在
	 */
	public static final EmallStatusCode ERROR_DATA_USER_NOT_FOUND = new EmallStatusCode(ERROR_DATA, "USER_NOT_FOUND");
	
	/**
	 * 商店不存在
	 */
	public static final EmallStatusCode ERROR_DATA_STORE_NOT_FOUND = new EmallStatusCode(ERROR_DATA, "STORE_NOT_FOUND");
	
	/**
	 * 订单不存在
	 */
	public static final EmallStatusCode ERROR_DATA_ORDER_NOT_FOUND = new EmallStatusCode(ERROR_DATA, "ORDER_NOT_FOUND");
	
	/**
	 * 订单信息不正确
	 */
	public static final EmallStatusCode ERROR_DATA_ORDER_INFO = new EmallStatusCode(ERROR_DATA, "ORDER_INFO");
	/**
	 * 预订单下单超时
	 */
	public static final EmallStatusCode ERROR_DATA_ORDER_PRE_TIME_OUT = new EmallStatusCode(ERROR_DATA, "ORDER_PRE_TIME_OUT");
	/**
	 * 订单不属于当前用户
	 */
	public static final EmallStatusCode ERROR_DATA_ORDER_NOT_BELONG_USER = new EmallStatusCode(ERROR_DATA, "ORDER_NOT_BELONG_USER");
	
	/**
	 * 订单中商品信息不正确
	 */
	public static final EmallStatusCode ERROR_DATA_ORDER_GOODS_INFO = new EmallStatusCode(ERROR_DATA, "ORDER_GOODS_INFO");
	
	
	/**
	 * 编码字符集不正确
	 */
	public static final EmallStatusCode ERROR_DATA_UNSUPPORT_EDENCODING = new EmallStatusCode(ERROR_DATA, "UNSUPPORT_EDENCODING");
	
	/**
	 * 验证支付发起的请求校验失败
	 */
	public static final EmallStatusCode ERROR_PAY_VERIFY = new EmallStatusCode(ERROR_DATA, "PAY_VERIFY");
	
	/**
	 * 创建支付失败
	 */
	public static final EmallStatusCode ERROR_PAY_CREATE = new EmallStatusCode(ERROR_DATA, "PAY_CREATE");
	
	/**
	 * 支付配置信息错误
	 */
	public static final EmallStatusCode ERROR_PAY_CONFIG = new EmallStatusCode(ERROR_DATA, "PAY_CONFIG");
	
	/**
	 * 支付服务提供商不存在
	 */
	public static final EmallStatusCode ERROR_PAY_PROVIDER_NOT_FOUND = new EmallStatusCode(ERROR_DATA, "PROVIDER_NOT_FOUND");

	
	public EmallStatusCode(String statusCode) {
		super(statusCode);
	}
	public EmallStatusCode(SkeletonStatusCode prefixCode, String statusCode) {
		super(prefixCode, statusCode);
	}

}
