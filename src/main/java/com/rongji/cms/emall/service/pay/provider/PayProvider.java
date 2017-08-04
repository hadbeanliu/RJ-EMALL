package com.rongji.cms.emall.service.pay.provider;

import java.util.Map;

import com.rongji.cms.emall.service.pay.PayInfo;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;

/**
 * 支付提供商
 *
 * @since 2015-7-1
 * @author rjf
 *
 */
public interface PayProvider {
	
	/**
	 * 用户在创建交易时，该用户当前所使用机器的IP
	 */
	public static final String EXTERNAL_IP_KEY = "pay.user.external.ip";
	
	public String getTitle();
	
	public String getServiceName();
	
	public String getDescription();
	
//	public void setConfig();
	/**
	 * 转换配置文件，用于存储
	 * 
	 * @param payment 非空
	 * @return 返回配置信息
	 */
	public String convertConfig(Payment payment);
	
	/**
	 * 是否为移动支付专用
	 * 
	 * @return
	 */
	public boolean isMobile();
	
	/**
	 * 执行支付
	 * 
	 * @param payment 非空支付方式
	 * @param order 非空订单
	 * @param params 支付参数
	 * @return 支付信息
	 * @exception PayException 如果创建支付信息失败返回
	 */
	public String doPay(Payment payment, Order order, Map<String, String> params) throws PayException;
	
	/**
	 * 校验合法性
	 * 
	 * @param payment 非空支付方式
	 * @param params 支付参数
	 * @return
	 * @throws PayException 如果支付信息校验失败返回
	 */
	public PayInfo verify(Payment payment, Map<String, String> params) throws PayException;
	
	/**
	 * 退款
	 * 
	 * @param payment 非空支付方式
	 * @param order 退款订单
	 * @param money 退款金额
	 * @param remark 退款理由
	 * @param params 支付参数
	 * @return 返回html页面，失败返回{@literal FAIL}
	 * @throws PayException 如果创建退款信息失败返回
	 */
	public String doRefund(Payment payment, Order order, long money, String remark, Map<String, String> params) throws PayException;
	
//	public String doPayInfo(Payment payment, Order order, Map<String, String> params) throws PayException;

}
