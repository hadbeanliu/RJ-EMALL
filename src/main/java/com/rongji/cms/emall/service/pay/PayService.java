package com.rongji.cms.emall.service.pay;

import java.util.Map;

import com.rongji.cms.emall.vo.Order;


/**
 * 支付处理
 *
 * @since 2015-7-1
 * @author rjf
 *
 */
public interface PayService {
	
	/**
	 * 执行支付
	 * 
	 * @param order 订单
	 * @param params 具体支付方式需要的参数
	 * @return
	 */
	public String doPay(Order order, String payCode, Map<String, String> params);
	
	/**
	 * 进行退款
	 * 
	 * @param refundId 退款编号
	 * @param money 退款金额
	 * @param remark 退款描述
	 * @param params 具体支付方式需要的参数
	 * @return
	 */
	public String doRefund(String refundId, String money, String remark, Map<String, String> params);
	
	/**
	 * 校验支付信息
	 * 
	 * @param order 订单
	 * @param params 具体支付方式需要的参数
	 * @return
	 */
	public PayInfo verify(Order order, Map<String, String> params);
	
	public Order doPayNotifyInfo(String orderId, Map<String, String> params);
	
//	public String getOrderIdByAlipay(Map<String, String> params);
//	public String getOrderIdByWeixinpay(Map<String, String> params);
	
//	/**
//	 * 获取移动端执行支付的信息
//	 * 
//	 * @return
//	 */
//	public String getMobileDoPayInfo(Order order, String payCode, Map<String, String> params);

}
