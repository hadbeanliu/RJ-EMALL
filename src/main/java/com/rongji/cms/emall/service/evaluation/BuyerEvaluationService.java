package com.rongji.cms.emall.service.evaluation;

import java.util.List;
import java.util.Map;

import com.rongji.cms.emall.vo.Evaluation;
import com.rongji.cms.emall.vo.Order;
import com.rongji.rjskeleton.service.NoneDaoCommonService;

/**
 *  我的评价
 * @author zhn
 *
 */
public interface BuyerEvaluationService extends NoneDaoCommonService<Evaluation> {
	/**
	 * 分页获取用户评论信息
	 * @param userId 用户ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getUserCommentInfo(String userId,String start,String rows);	
	/**
	 * 买家追加评论
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @param additionalComment 追加内容
	 * @param sunAdditionalCommentPicture 追加晒图信息
	 * @return
	 */
	public Map<String,String> setAdditionalComment(String objId,String cmtId,String userId,String additionalComment,String sunAdditionalCommentPicture);
	/**
	 * 是否可追加评论
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isAdditionalComment(String objId,String cmtId,String userId);
	/**
	 * 是否可晒图
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isSunPicture(String objId,String cmtId,String userId);
	/**
	 * 获取单条评论信息
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,Object> getCommentInfo(String objId,String cmtId,String userId);
	/**
	 * 获取需评价商品订单
	 * @param orderId
	 * @return
	 */
	public Order getEvaluationOrder(String orderId,String userId);
}
