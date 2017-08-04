package com.rongji.cms.emall.service.evaluation.impl;

import java.text.DecimalFormat;
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
import com.rongji.cms.emall.service.evaluation.SellerEvaluationService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.Evaluation;
import com.rongji.cms.emall.vo.EvaluationComment;
import com.rongji.cms.emall.vo.EvaluationTemp;
import com.rongji.dfish.base.Utils;
import com.rongji.dfish.cache.meta.HashMeta;
import com.rongji.dfish.cache.meta.KeysMeta;
import com.rongji.dfish.cache.meta.ListMeta;
import com.rongji.dfish.cache.meta.SortedSetMeta;
import com.rongji.dfish.cache.meta.StringMeta;
import com.rongji.rjskeleton.service.NoneDaoCommonServiceImpl;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class SellerEvaluationImpl extends NoneDaoCommonServiceImpl<Evaluation> implements SellerEvaluationService {

	private String dynamicListFlag="dynamicList.";//动态列表标识	
	private String dynamicGoodListFlag="dynamicGoodList.";//商品好评评论列表;
	private String dynamicInListFlag="dynamicInList.";//商品中评评论列表;
	private String dynamicBadListFlag="dynamicBadList.";//商品差评评论列表;
	private String dynamicPictureListFlag="dynamicPictureList.";//商品有图片评论列表;
	private String dynamicAdditionalListFlag="dynamicAdditionalList.";//商品有追加评论列表;
	private String dynamicUserListFlag="dynamicUserList.";//用户动态列表标识
	private String dynamicStoreGoodsScoreFlag="dynamicStoreGoodsScore.";//店铺商品评分
	private String dynamicStoreServiceScoreFlag="dynamicStoreServiceScore.";//店铺服务评分
	private String dynamicStoreLogisticsScoreFlag="dynamicStoreLogisticsScore.";//店铺物流评分
	
	private String dynamicStoreSocreFlag="dynamicStoreSocre.";//店铺动态评分
	private String dynamicCommentMapFlag="dynamicCommentMap.";//动态评论内容
	
	private String dynamicStoreListFlag="dynamicStoreList.";//店铺所有评论列表标识	
	private String dynamicStoreGoodListFlag="dynamicStoreGoodList.";//店铺好评评论列表;
	private String dynamicStoreInListFlag="dynamicStoreInList.";//店铺中评评论列表;
	private String dynamicStoreBadListFlag="dynamicStoreBadList.";//店铺差评评论列表;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	private Map<String,String> info=new HashMap<String,String>();//返回信息
		
	@Autowired
	private StoreService storeService;
	@Override
	public PageEntity<Evaluation> getByDatatables(DatatablesMetadata metadata) {
        Gson gson=new Gson();
		List<Evaluation> evaluations=new ArrayList<Evaluation>();
		String userId=null;//获取用户ID		
		if(metadata.getSearchs().get(0)[0].equals("userId")){
			userId=metadata.getSearchs().get(0)[2];
		}else{
			userId=metadata.getSearchs().get(1)[2];
		}
		String storeId=storeService.getStoreByUserId(userId).getStoreId();
		String evaluationType="0";
		if(metadata.getSearch()!=""){
			evaluationType=metadata.getSearch().split("=")[1];
		}
		String evaluationKey=null;
		if(evaluationType.equals("0")){
			evaluationKey=dynamicStoreListFlag+"storeId:"+storeId;
		}else if(evaluationType.equals("1")){
			evaluationKey=dynamicStoreGoodListFlag+"storeId:"+storeId;
		}else if(evaluationType.equals("2")){
			evaluationKey=dynamicStoreInListFlag+"storeId:"+storeId;
		}else if(evaluationType.equals("3")){
			evaluationKey=dynamicStoreBadListFlag+"storeId:"+storeId;
		}
		ListMeta dynamicList=new ListMeta(evaluationKey);//记录评论列表
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
	    	 evaluation.setNickName(evaluationTemp.getNickName());
	    	 EvaluationComment evaluationComment=gson.fromJson(gson.toJson(dynamicCommentMap.getAll()), EvaluationComment.class);
	    	 evaluation.setComment(evaluationComment);//添加评论信息	    	
	    	 //是否可解释
	    	 if(isExplain(evaluationComment.getObjId(), evaluationComment.getCmtId(), evaluationComment.getUserId()).get("Code").equals("200")){
	    		 evaluation.setIsExplain(true);	    		 
	    	 }else{
	    		 evaluation.setIsExplain(false);
	    	 }
	    	 //是否可以追加解释
	    	 if(isAdditionalExplain(evaluationComment.getObjId(), evaluationComment.getCmtId(), evaluationComment.getUserId()).get("Code").equals("200")){
	    		 evaluation.setIsAdditionalExplain(true);
	    	 }else{
	    		 evaluation.setIsAdditionalExplain(false);
	    	 }	
	    	 evaluations.add(evaluation);
		}
		PageEntity<Evaluation> ppsp = new PageEntity<Evaluation>(metadata.getDraw(),size, size,evaluations);
		
		return ppsp;
	}

	/**
	 * 获取店铺规定时间内的动态评分
	 * @param StoreId  店铺Id
	 * @param withinDays 几天内
	 * @return
	 */
	public String getDynamicInfoByStoreIdAndWithinDays(String storeId,int withinDays)
	{
		Map<String,Object> info=new HashMap<String,Object>();
		StringMeta dynamicStoreSocre=new StringMeta(dynamicStoreSocreFlag+"storeId:"+storeId);//店铺动态评分信息json格式字符串
		String dss=dynamicStoreSocre.get();
		Map<String,Float> higherInfo=getHigerInfo(withinDays);//用于高于百分比计算 (店铺得分-所有店铺平均分)/(所有店铺最高得分-所有店铺平均分)
		//是否已统计
//		if(dss==null){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -withinDays);//起始时间
	    	Date date = calendar.getTime();	
	    	DecimalFormat df = new DecimalFormat("0.00");  
	    	
	    	float dynamicStoreGoodsScoreTotal=0;//商品总分	    	
			SortedSetMeta dynamicStoreGoodsScoreSorted=new SortedSetMeta(dynamicStoreGoodsScoreFlag+"storeId:"+storeId);//商品分
			for (String dynamicStoreGoodsScore : dynamicStoreGoodsScoreSorted.zrangeByScore(date.getTime()+"", new Date().getTime()+"")) {
				dynamicStoreGoodsScoreTotal+=Integer.valueOf(dynamicStoreGoodsScore.substring(16));
			}	
			Long dynamicStoreGoodsScoreSortedSize=dynamicStoreGoodsScoreSorted.zcard();
			float avgDynamicStoreGoodsScore=0;//好评平均分
			if(dynamicStoreGoodsScoreSortedSize!=0){
			   avgDynamicStoreGoodsScore=Float.valueOf(df.format((float)dynamicStoreGoodsScoreTotal/dynamicStoreGoodsScoreSortedSize));
			}
			if(dynamicStoreGoodsScoreTotal!=0){
				info.put("avgDynamicStoreGoodsScore",avgDynamicStoreGoodsScore);
			}else{
				info.put("avgDynamicStoreGoodsScore",5);
			}
			//商品高于百分比
			float higerStoreGoodsRate=(avgDynamicStoreGoodsScore-higherInfo.get("avgDynamicStoreGoodsScore"))/(higherInfo.get("dynamicStoreGoodsHighScore")-higherInfo.get("avgDynamicStoreGoodsScore"));
			if(higerStoreGoodsRate==0){
				info.put("higerStoreGoodsRate", 1);
			}else{
				info.put("higerStoreGoodsRate", df.format(higerStoreGoodsRate));
			}			
			
			float dynamicStoreServiceScoreTotal=0;//服务总分
			SortedSetMeta dynamicStoreServiceScoreSorted=new SortedSetMeta(dynamicStoreServiceScoreFlag+"storeId:"+storeId);//服务分
			for (String dynamicStoreServiceScore : dynamicStoreServiceScoreSorted.zrangeByScore(date.getTime()+"", new Date().getTime()+"")) {
				dynamicStoreServiceScoreTotal+=Integer.valueOf(dynamicStoreServiceScore.substring(16));
			}	
			Long dynamicStoreServiceScoreSortedSize=dynamicStoreServiceScoreSorted.zcard();
			float avgDynamicStoreServiceScore=0;
			if(dynamicStoreServiceScoreSortedSize!=0){
				avgDynamicStoreServiceScore=Float.valueOf(df.format((float)dynamicStoreServiceScoreTotal/dynamicStoreServiceScoreSorted.zcard()));
			}
			if(dynamicStoreServiceScoreTotal!=0){
				info.put("avgDynamicStoreServiceScore",avgDynamicStoreServiceScore);
			}else{
				info.put("avgDynamicStoreServiceScore",5);
			}	
			//服务高于百分比
			float higerStoreServiceRate=(avgDynamicStoreServiceScore-higherInfo.get("avgDynamicStoreServiceScore"))/(higherInfo.get("dynamicStoreServiceHighScore")-higherInfo.get("avgDynamicStoreServiceScore"));
			if(higerStoreServiceRate==0){
				info.put("higerStoreServiceRate", 1);
			}else{
				info.put("higerStoreServiceRate", df.format(higerStoreServiceRate));
			}			
			
			float dynamicStoreLogisticsScoreTotal=0;//物流总分
			SortedSetMeta dynamicStoreLogisticsScoreSorted=new SortedSetMeta(dynamicStoreLogisticsScoreFlag+"storeId:"+storeId);//物流分
			for (String dynamicStoreLogisticsScore : dynamicStoreLogisticsScoreSorted.zrangeByScore(date.getTime()+"", new Date().getTime()+"")) {
				dynamicStoreLogisticsScoreTotal+=Integer.valueOf(dynamicStoreLogisticsScore.substring(16));
			}	
			float dynamicStoreLogisticsScoreSortedSize=dynamicStoreServiceScoreSorted.zcard();
			float avgDynamicStoreLogisticsScore=0;
			if(dynamicStoreLogisticsScoreSortedSize!=0){
				avgDynamicStoreLogisticsScore=Float.valueOf(df.format((float)dynamicStoreLogisticsScoreTotal/dynamicStoreServiceScoreSorted.zcard()));
			}
			if(dynamicStoreLogisticsScoreTotal!=0){
				info.put("avgDynamicStoreLogisticsScore",avgDynamicStoreLogisticsScore);
			}else{
				info.put("avgDynamicStoreLogisticsScore",5);
			}
			//物流分高于百分比
			float higerStoreLogisticsRate=(avgDynamicStoreLogisticsScore-higherInfo.get("avgDynamicStoreLogisticsScore"))/(higherInfo.get("dynamicStoreLogisticsHighScore")-higherInfo.get("avgDynamicStoreLogisticsScore"));
			if(higerStoreLogisticsRate==0){
				info.put("higerStoreLogisticsRate", 1);
			}else{
				info.put("higerStoreLogisticsRate", df.format(higerStoreLogisticsRate));
			}
			
			Gson gson=new Gson();
			//设置动态评分并设置过期时间
//			if(dynamicStoreSocre.setex(86400-Calendar.HOUR*60*60-Calendar.MINUTE*60-Calendar.SECOND, gson.toJson(info)).equals("OK")){
				dss=gson.toJson(info);
//			}else{
//				return null;
//			}
//		}
		return dss;
	}
	
	/**
	 * 获取所有平均分
	 * @param withinDays 几天内
	 * @return
	 */
	public Map<String,Float> getHigerInfo(int withinDays){
		
		Map<String,Float> info=new HashMap<String,Float>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -withinDays);//起始时间
    	Date date = calendar.getTime();	
    	DecimalFormat df = new DecimalFormat("0.00");  
    	
    	//商品平均分计算
    	float dynamicStoreGoodsScoreTotal=0;//商品总分
    	Long dynamicStoreGoodsScoreSize=0L;//商品评价次数
    	float dynamicStoreGoodsHighScore=0;//记录最高分
		KeysMeta dynamicStoreGoodsScoreKm =new KeysMeta();
	    for (String dynamicStoreGoodsScoreKey : dynamicStoreGoodsScoreKm.srem(dynamicStoreGoodsScoreFlag+"storeId:*")) {
	    	SortedSetMeta dynamicStoreGoodsScoreSorted=new SortedSetMeta(dynamicStoreGoodsScoreKey);//商品
			for (String dynamicStoreGoodsScore : dynamicStoreGoodsScoreSorted.zrangeByScore(date.getTime()+"", new Date().getTime()+"")) {
				int temp=Integer.valueOf(dynamicStoreGoodsScore.substring(16));
				//最高分设定
				if(temp>dynamicStoreGoodsHighScore){
					dynamicStoreGoodsHighScore=temp;
				}
				dynamicStoreGoodsScoreTotal+=temp;
			}	
			dynamicStoreGoodsScoreSize+=dynamicStoreGoodsScoreSorted.zcard();
		}
	    if(dynamicStoreGoodsScoreTotal==0){
	    	info.put("avgDynamicStoreGoodsScore", Float.valueOf(5));
	    }else{
	    	info.put("avgDynamicStoreGoodsScore", Float.valueOf(df.format(dynamicStoreGoodsScoreTotal/dynamicStoreGoodsScoreSize)));
	    }
	    if(dynamicStoreGoodsHighScore==0){
	    	dynamicStoreGoodsHighScore=5;
	    }
	    info.put("dynamicStoreGoodsHighScore", dynamicStoreGoodsHighScore);
	    //服务平均分计算
	    float dynamicStoreServiceScoreTotal=0;//服务总分
    	Long dynamicStoreServiceScoreSize=0L;//服务评价次数
    	float dynamicStoreServiceHighScore=0;//记录最高分
		KeysMeta dynamicStoreServiceScoreKm =new KeysMeta();
	    for (String dynamicStoreServiceScoreKey : dynamicStoreServiceScoreKm.srem(dynamicStoreServiceScoreFlag+"storeId:*")) {
	    	SortedSetMeta dynamicStoreServiceScoreSorted=new SortedSetMeta(dynamicStoreServiceScoreKey);//服务
			for (String dynamicStoreServiceScore : dynamicStoreServiceScoreSorted.zrangeByScore(date.getTime()+"", new Date().getTime()+"")) {
				int temp=Integer.valueOf(dynamicStoreServiceScore.substring(16));
				if(temp>dynamicStoreServiceHighScore){
					dynamicStoreServiceHighScore=temp;
				}
				dynamicStoreServiceScoreTotal+=temp;
			}	
			dynamicStoreServiceScoreSize+=dynamicStoreServiceScoreSorted.zcard();
		}
	    if(dynamicStoreServiceScoreTotal==0){
	    	 info.put("avgDynamicStoreServiceScore", Float.valueOf(5));
	    }else{
	    	 info.put("avgDynamicStoreServiceScore", Float.valueOf(df.format(dynamicStoreServiceScoreTotal/dynamicStoreServiceScoreSize)));
	    }
	    if(dynamicStoreServiceHighScore==0){
	    	dynamicStoreServiceHighScore=5;
	    }
	    info.put("dynamicStoreServiceHighScore", dynamicStoreServiceHighScore);
	    //物流平均分计算
	    float dynamicStoreLogisticsScoreTotal=0;//物流总分
    	Long dynamicStoreLogisticsScoreSize=0L;//物流评价次数
    	float dynamicStoreLogisticsHighScore=0;//记录最高分
		KeysMeta dynamicStoreLogisticsScoreKm =new KeysMeta();
	    for (String dynamicStoreLogisticsScoreKey : dynamicStoreLogisticsScoreKm.srem(dynamicStoreLogisticsScoreFlag+"storeId:*")) {
	    	SortedSetMeta dynamicStoreLogisticsScoreSorted=new SortedSetMeta(dynamicStoreLogisticsScoreKey);//物流
			for (String dynamicStoreLogisticsScore : dynamicStoreLogisticsScoreSorted.zrangeByScore(date.getTime()+"", new Date().getTime()+"")) {
				int temp=Integer.valueOf(dynamicStoreLogisticsScore.substring(16));
				if(temp>dynamicStoreLogisticsHighScore){
					dynamicStoreLogisticsHighScore=temp;
				}
				dynamicStoreLogisticsScoreTotal+=temp;
			}	
			dynamicStoreLogisticsScoreSize+=dynamicStoreLogisticsScoreSorted.zcard();
		}
	    if(dynamicStoreLogisticsScoreTotal==0){
	    	info.put("avgDynamicStoreLogisticsScore", Float.valueOf(5));
	    }else{
	    	info.put("avgDynamicStoreLogisticsScore", Float.valueOf(df.format(dynamicStoreLogisticsScoreTotal/dynamicStoreLogisticsScoreSize)));
	    }
	    if(dynamicStoreLogisticsHighScore==0){
	    	dynamicStoreLogisticsHighScore=5;
	    }
	    info.put("dynamicStoreLogisticsHighScore", dynamicStoreLogisticsHighScore);
		return info;
				
	}
	
	/**
	 * 分页获取所有评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicAllInfo(String objId,String start,String rows){
		if (Utils.isEmpty(start)) {
			start = "0";
		}
		if (Utils.isEmpty(rows)) {
			rows = "10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Long.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicList=new ListMeta(dynamicListFlag+"objId:"+objId);//所有评论列表
		ListMeta lm=new ListMeta();		
		return lm.getHashDatasByListKeys(dynamicList.gets(Long.valueOf(start), end));
	}	

	/**
	 * 分页获取好评评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicGoodInfo(String objId,String start,String rows){
		if (Utils.isEmpty(start)) {
			start = "0";
		}
		if (Utils.isEmpty(rows)) {
			rows = "10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Long.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicGoodList=new ListMeta(dynamicGoodListFlag+"objId:"+objId);//好评评论列表
		ListMeta lm=new ListMeta();		
		return lm.getHashDatasByListKeys(dynamicGoodList.gets(Long.valueOf(start), end));
	}
	
	/**
	 * 分页获取中评评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicInInfo(String objId,String start,String rows){
		if (Utils.isEmpty(start)) {
			start = "0";
		}
		if (Utils.isEmpty(rows)) {
			rows = "10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Long.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicInList=new ListMeta(dynamicInListFlag+"objId:"+objId);//中评评论列表
		ListMeta lm=new ListMeta();		
		return lm.getHashDatasByListKeys(dynamicInList.gets(Long.valueOf(start), end));
	}
	
	/**
	 * 分页获取差评评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicBadInfo(String objId,String start,String rows){
		if (Utils.isEmpty(start)) {
			start = "0";
		}
		if (Utils.isEmpty(rows)) {
			rows = "10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Long.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicBadList=new ListMeta(dynamicBadListFlag+"objId:"+objId);//差评评论列表
		ListMeta lm=new ListMeta();		
		return lm.getHashDatasByListKeys(dynamicBadList.gets(Long.valueOf(start), end));
	}
	
	/**
	 * 分页获取带有图片评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getDynamicPictureInfo(String objId,String start,String rows){
		if (Utils.isEmpty(start)) {
			start = "0";
		}
		if (Utils.isEmpty(rows)) {
			rows = "10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Long.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicPictureList=new ListMeta(dynamicPictureListFlag+"objId:"+objId);//带有图片评论列表
		ListMeta lm=new ListMeta();		
		return lm.getHashDatasByListKeys(dynamicPictureList.gets(Long.valueOf(start), end));
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
	 * 分页获取追加评论信息
	 * @param objId 商品ID
	 * @param start 开始索引
	 * @param rows  每页显示条数
	 * @return
	 */
	public List<Object> getAdditionalCommentInfo(String objId,String start,String rows){
		if (Utils.isEmpty(start)) {
			start = "0";
		}
		if (Utils.isEmpty(rows)) {
			rows = "10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Long.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicAdditionalList=new ListMeta(dynamicAdditionalListFlag+"objId:"+objId);//追加评论列表
		ListMeta lm=new ListMeta();		
		return lm.getHashDatasByListKeys(dynamicAdditionalList.gets(Long.valueOf(start), end));
	}
	
	/**
	 * 卖家解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @param explain 解释内容
	 * @return
	 */
	public Map<String,String> setExplainComment(String objId,String cmtId,String userId,String explain){
		HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
		if(commentMap.get("explain")==null){
			commentMap.set("explain", explain);
			commentMap.set("explainTime", df.format(new Date()));
			info.put("Code", "200");
			info.put("Msg", "解释操作成功!");
		}else{
			info.put("Code", "204");
			info.put("Msg", "已解释!");
		}		
		return info;				 
	}

	/**
	 * 卖家追加解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @param additionalExplain 追加解释
	 * @return
	 */
	public Map<String,String> setAdditionalExplain(String objId,String cmtId,String userId,String additionalExplain){
		HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
		if(commentMap.get("additionalExplain")==null){
			commentMap.set("additionalExplain", additionalExplain);
			commentMap.set("additionalExplainTime", df.format(new Date()));
			info.put("Code", "200");
			info.put("Msg", "追加解释操作成功!");
		}else{
			info.put("Code", "204");
			info.put("Msg", "已追加解释!");
		}		
		return info;
	}
	
	/**
	 * 是否可解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isExplain(String objId,String cmtId,String userId){
		HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
		if(commentMap.get("explain")!=null){
			info.put("Code", "204");
			info.put("Msg", "不可解释!");
		}else{
			info.put("Code", "200");
			info.put("Msg", "可解释!");
		}
		return info;
	}

	/**
	 * 是否可追加解释
	 * @param objId 商品Id
	 * @param cmtId 评论Id
	 * @param userId 买家Id
	 * @return
	 */
	public Map<String,String> isAdditionalExplain(String objId,String cmtId,String userId){
		HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
		if(commentMap.get("additionalExplain")!=null||commentMap.get("additionalComment")==null){
			info.put("Code", "204");
			info.put("Msg", "不可追加解释!");
		}else{
			info.put("Code", "200");
			info.put("Msg", "可追加解释!");
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
}
