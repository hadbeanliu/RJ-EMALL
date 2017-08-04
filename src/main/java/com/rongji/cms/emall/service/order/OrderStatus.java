package com.rongji.cms.emall.service.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态
 *
 * @since 2015-7-7
 * @author rjf
 *
 */
public class OrderStatus {

	/** 等待买家付款 */
	public static final String NOT_PAID = "1";
	/** 买家已付款 */
	public static final String PAID = "2";
	/** 卖家已发货 */
	public static final String SEND = "3";
	/** 交易成功 */
	public static final String SUCCESS = "4";
	/** 交易关闭 未走正常流程的完成订单 */
	public static final String DROP = "5";
//	/** 待付款和待发货订单 */
//	public static final String NOT_PAID_AND_NOT_SEND = "NOT_PAID_AND_NOT_SEND";
	/** 退款中的订单 */
	public static final String REFUNDING = "6";
	/** 定金已付 */
	public static final String FRONT_PAID = "7";
	/** 异常订单 */
	public static final String EXCEPTIONAL = "8";
	
	
	private static final Map<String, String> titles = new HashMap<String, String>();
	
	static {
		titles.put("1", "等待买家付款");
		titles.put("2", "买家已付款");
		titles.put("3", "卖家已发货");
		titles.put("4", "交易成功");
		titles.put("5", "交易关闭");
//		titles.put("NOT_PAID_AND_NOT_SEND", "待付款和待发货订单");
		titles.put("6", "退款中的订单");
		titles.put("7", "定金已付");
		titles.put("8", "异常订单");
	}

	/**
	 * 获取状态的描述
	 * 
	 * @param status 订单状态
	 * @return 不存在返回{@literal null}
	 */
	public static String getDesc(String status) {
		return titles.get(status);
	}

}
