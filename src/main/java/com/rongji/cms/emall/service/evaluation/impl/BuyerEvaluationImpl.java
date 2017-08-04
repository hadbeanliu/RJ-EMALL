package com.rongji.cms.emall.service.evaluation.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rongji.cms.emall.service.evaluation.BuyerEvaluationService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.vo.Evaluation;
import com.rongji.cms.emall.vo.EvaluationComment;
import com.rongji.cms.emall.vo.EvaluationTemp;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.dfish.base.Utils;
import com.rongji.dfish.cache.meta.HashMeta;
import com.rongji.dfish.cache.meta.KeysMeta;
import com.rongji.dfish.cache.meta.ListMeta;
import com.rongji.rjskeleton.service.NoneDaoCommonServiceImpl;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class BuyerEvaluationImpl extends NoneDaoCommonServiceImpl<Evaluation> implements BuyerEvaluationService {

	private String dynamicPictureListFlag="dynamicPictureList.";//商品有图片评论列表;
	private String dynamicAdditionalListFlag="dynamicAdditionalList.";//商品有追加评论列表;
	private String dynamicMapFlag="dynamicMap.";//用户动态信息标识
	private String dynamicUserListFlag="dynamicUserList.";//用户动态列表标识
	private String dynamicCommentMapFlag="dynamicCommentMap.";//动态评论内容
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	private Map<String,String> info=new HashMap<String,String>();//返回信息
	
	@Autowired
	private OrderService orderService;
		
	@Override
	public PageEntity<Evaluation> getByDatatables(DatatablesMetadata metadata) {
        Gson gson=new Gson();
		List<Evaluation> evaluations=new ArrayList<Evaluation>();
		String userId=metadata.getSearchs().get(0)[2];
		ListMeta dynamicList=new ListMeta(dynamicUserListFlag+"userId:"+userId);//记录评论列表
		int start=metadata.getStart();		
		int currPage=start/metadata.getLenght()+1;//第几页
		int end=metadata.getLenght()*currPage-1;//结束
		int size=Integer.valueOf(dynamicList.size().toString());
	     for (String dynamicMapKey : dynamicList.gets(start, end)) {	
	    	 Evaluation evaluation=new Evaluation();
	    	 HashMeta dynamicMap=new HashMeta(dynamicMapKey);//单条评论信息
	    	 EvaluationTemp evaluationTemp=gson.fromJson(gson.toJson(dynamicMap.getAll()),EvaluationTemp.class);	    	 
	    	 HashMeta dynamicCommentMap=new HashMeta(evaluationTemp.getComment());
	    	 evaluation.setGoodsTitle(evaluationTemp.getGoodsTitle());
	    	 evaluation.setSkuInfo(evaluationTemp.getSkuInfo());
	    	 evaluation.setSellerName(evaluationTemp.getSellerName());
	    	 EvaluationComment evaluationComment=gson.fromJson(gson.toJson(dynamicCommentMap.getAll()), EvaluationComment.class);
	    	 evaluation.setComment(evaluationComment);//添加评论信息	    	
	    	 //是否可追加评论
	    	 if(isAdditionalComment(evaluationComment.getObjId(), evaluationComment.getCmtId(), evaluationComment.getUserId()).get("Code").equals("200")){
	    		 evaluation.setIsAdditionalComment(true);
	    	 }else{
	    		 evaluation.setIsAdditionalComment(false);
	    	 }
	    	 evaluations.add(evaluation);
		}
		PageEntity<Evaluation> ppsp = new PageEntity<Evaluation>(metadata.getDraw(),size, size,evaluations);
		
		return ppsp;
	}
	
	/**
	 * 分页获取用户评论信息
	 * @param userId 用户ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getUserCommentInfo(String userId,String start,String rows){
		if (Utils.isEmpty(start)) {
			start = "0";
		}
		if (Utils.isEmpty(rows)) {
			rows = "10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Long.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicUserList=new ListMeta(dynamicUserListFlag+"userId:"+userId);//用户评论列表
		ListMeta lm=new ListMeta();		
		return lm.getHashDatasByListKeys(dynamicUserList.gets(Long.valueOf(start), end));
	}
	

	/**
	 * 买家追加评论
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @param additionalComment 追加内容
	 * @param sunAdditionalCommentPicture 追加晒图信息
	 * @return
	 */
	public Map<String,String> setAdditionalComment(String objId,String cmtId,String userId,String additionalComment,String sunAdditionalCommentPicture){
		info=isAdditionalComment(objId,cmtId,userId);
		if(info.get("Code").equals("200")){
			HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
			info=isSunPicture(objId,cmtId,userId);
			if(info.get("Code").equals("200") || info.get("Code").equals("204")){
				if(sunAdditionalCommentPicture!=null){
				commentMap.set("sunAdditionalCommentPicture", sunAdditionalCommentPicture);
				ListMeta dynamicAdditionalList=new ListMeta(dynamicPictureListFlag+"objId:"+objId);//有图片评论
				dynamicAdditionalList.add(dynamicMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
				}
			}else{
				return info;
			}
			commentMap.set("additionalComment", additionalComment);
			commentMap.set("additionalCommentTime", df.format(new Date()));
			ListMeta dynamicPictureList=new ListMeta(dynamicAdditionalListFlag+"objId:"+objId);//追加评论
			dynamicPictureList.lAdd(dynamicMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
			info.put("Code", "200");
			info.put("Msg", "追加评论成功!");
			return info;
		}else{
			return info;
		}
	}	

	/**
	 * 是否可追加评论
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isAdditionalComment(String objId,String cmtId,String userId){
		HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
		String determineReceipt=commentMap.get("determineReceipt");		
		Date determineReceiptDate=null;
		try {
			determineReceiptDate = df.parse(determineReceipt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -180);//180天前
    	Date nowDate = calendar.getTime();	
		if(commentMap.get("additionalComment")!=null){
			info.put("Code", "404");			
			info.put("Msg", "已追加评论!");
		}else if(nowDate.getTime()-determineReceiptDate.getTime()>86400*180){
			info.put("Code", "404");			
			info.put("Msg", "已超过规定时间,不能追加!");
		}else{
			info.put("Code", "200");
			info.put("Msg", "可追加!");
		}
		return info;
	}
	
	/**
	 * 是否可晒图
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isSunPicture(String objId,String cmtId,String userId){
		HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
		if(commentMap.get("sunCommentPicture")==null||commentMap.get("sunCommentPicture").equals("")){
			if(commentMap.get("sunAdditionalCommentPicture")==null||commentMap.get("sunAdditionalCommentPicture").equals("")){
				info.put("Code", "200");
				info.put("Msg", "可晒图!");
			}else{
				info.put("Code", "204");
				info.put("Msg", "只能晒图一次!");
			}
		}else{
			info.put("Code", "204");
			info.put("Msg", "只能晒图一次!");
		}
		return info;
	}
	
	/**
	 * 获取单条评论信息
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,Object> getCommentInfo(String objId,String cmtId,String userId){
		HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
		Map<String,Object> info=new HashMap<String,Object>();
		Map<String,String> cMap=commentMap.getAll();
		if(!cMap.isEmpty()){
			info.put("comment", cMap);
			info.put("Code", "200");
			info.put("Msg", "单条评论信息!");
		}else{			
			info.put("Code", "404");
			info.put("Msg", "单条评论信息不存在!");
		}
		return info;
	}

	@Override
	public Order getEvaluationOrder(String orderId,String userId) {
		Order orders = orderService.getOrder(orderId);
		//去除已评价商品
	    List<OrderGoods> orderGoods=new ArrayList<OrderGoods>();//需评价的商品列表
	    orderGoods= orders.getGoods();
	    List<OrderGoods> removeOrderGoods=new ArrayList<OrderGoods>();//记录需删除的商品
	    for (int i=0;i<orderGoods.size();i++) {
			//redis查找判断是否已评价
	    	KeysMeta km=new KeysMeta();		
	    	String goodsId=orderGoods.get(i).getGoodsId();//商品ID
	    	String skuId=orderGoods.get(i).getSkuId();//商品规格ID
	    	for (String  dynamicCommentMapKey: km.srem(dynamicCommentMapFlag+"objId:"+goodsId+"*userId:"+userId)) {
				HashMeta hm=new HashMeta(dynamicCommentMapKey);
				//判断是否已评价
				if(hm.get("skuId").equals(skuId)){
					removeOrderGoods.add(orderGoods.get(i));
					break;
				}
			}		    
		}
	    if(removeOrderGoods.size()>0){
	    	orderGoods.removeAll(removeOrderGoods);	
	    	orders.setGoods(orderGoods);
	    }
		return orders;
		
	}
}
