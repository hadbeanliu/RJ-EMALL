package com.rongji.cms.emall.service.refund;

import java.util.List;

import com.rongji.cms.emall.entity.EmlRefund;
import com.rongji.cms.emall.vo.Refund;
import com.rongji.cms.emall.vo.ResultModel;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

public interface RefundService extends CommonService<Refund,EmlRefund>{
	/**
	 * 根据 用户id获取对应退款订单
	 * 
	 * @param userId    用户id
	 * @param  metadata
	 * 
	 * @return PageEntity<Refund>
	 */
	public	PageEntity<Refund>  getUserfund(String userId,DatatablesMetadata metadata);
	/**
	 * 根据商铺id获取对应退款订单
	 * 
	 * @param storeId    商铺id
	 * @param  metadata
	 * 
	 * @return PageEntity<Refund>
	 */
	public PageEntity<Refund> getStorefund(String storeId,DatatablesMetadata metadata);
	/**
	 * 根据订单id获取对应退款订单
	 * 
	 * @param orderId    订单
	 * 
	 * 
	 * @return List<Refund>
	 */
	public List<Refund> getfundByOrderId(String orderId);

	/**
	 * 卖家处理意见
	 * 
	 * @param refundId    退款订单id
	 * @param status 处理状态
	 * @param handlingSuggestion 处理意见
	 * @param refundSum 退款金额
	 * @return Refund
	 */
	public Refund sellRefund(String refundId, String status, String handlingSuggestion, String refundSum);
	/**
	 * 添加退款订单
	 * 
	 * @param orderId    订单
	 * @param refundReason 退款理由
	 * @param refundStatus 
	 * 
	 * @return ResultModel
	 */
	public ResultModel addrefund(String orderId, String refundReason, String refundStatus);
	/**
	 * 改变处理状态
	 * 
	 * @param refundId    
	 * @param status 处理状态
	 * 
	 * @return 退款订单
	 */
	public Refund changeStatus(String refundId, String status);
	/**
	 * 客服意见反馈
	 * 
	 * @param refundId    
	 * @param status 处理状态
	 * @param handlingSuggestion 处理意见
	 * @return 退款订单
	 */
	public Refund refundHandlingService(String refundId, String status,String handlingSuggestion);
	
	/**
	 * 退款成功
	 * 
	 * @param refundId    退款id 
	 * 
	 * 
	 * 
	 */
	public void refundSuccess(String refundId,double num);
	/**
	 * 拒绝退款
	 * 
	 * @param refundId    退款id 
	 * 
	 * 
	 * 
	 */
	public void refundFailure(String refundId);
	
}
