package com.rongji.cms.emall.service.evaluation;

import java.util.List;
import java.util.Map;

import com.rongji.cms.emall.vo.Evaluation;
import com.rongji.rjskeleton.service.NoneDaoCommonService;

/**
 *  我的评价
 * @author zhn
 *
 */
public interface SellerEvaluationService extends NoneDaoCommonService<Evaluation> {
	/**
	 * 获取店铺规定时间内的动态评分
	 * @param storeId  店铺Id
	 * @param withinDays 几天内
	 * @return
	 */
	public String getDynamicInfoByStoreIdAndWithinDays(String storeId,int withinDays);
	/**
	 * 获取所有平均分
	 * @param withinDays 几天内
	 * @return
	 */
	public Map<String,Float> getHigerInfo(int withinDays);
	/**
	 * 分页获取所有评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicAllInfo(String objId,String start,String rows);
	/**
	 * 分页获取好评评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicGoodInfo(String objId,String start,String rows);
	/**
	 * 分页获取中评评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicInInfo(String objId,String start,String rows);
	/**
	 * 分页获取差评评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicBadInfo(String objId,String start,String rows);
	/**
	 * 分页获取带有图片评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicPictureInfo(String objId,String start,String rows);
	/**
	 * 分页获取用户评论信息
	 * @param userId 用户ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getUserCommentInfo(String userId,String start,String rows);	
	/**
	 * 卖家解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @param explain 解释内容
	 * @return
	 */
	public Map<String,String> setExplainComment(String objId,String cmtId,String userId,String explain);
	/**
	 * 卖家追加解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @param additionalExplain 追加解释
	 * @return
	 */
	public Map<String,String> setAdditionalExplain(String objId,String cmtId,String userId,String additionalExplain);
	/**
	 * 是否可解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isExplain(String objId,String cmtId,String userId);
	/**
	 * 是否可追加解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isAdditionalExplain(String objId,String cmtId,String userId);	
	/**
	 * 获取单条评论信息
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,Object> getCommentInfo(String objId,String cmtId,String userId);
}
