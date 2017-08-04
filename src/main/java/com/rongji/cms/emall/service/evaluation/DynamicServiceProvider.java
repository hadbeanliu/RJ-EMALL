package com.rongji.cms.emall.service.evaluation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.vo.Dynamic;
import com.rongji.cms.emall.vo.DynamicScore;
import com.rongji.cms.emall.vo.Evaluation;
import com.rongji.cms.emall.vo.Order;
import com.rongji.dfish.base.Utils;
import com.rongji.dfish.cache.RedisServiceProvider;
import com.rongji.dfish.cache.meta.BaseMeta;
import com.rongji.dfish.cache.meta.HashMeta;
import com.rongji.dfish.cache.meta.ListMeta;
import com.rongji.dfish.cache.meta.SortedSetMeta;
import com.rongji.dfish.cache.meta.StringMeta;
import com.rongji.rjskeleton.service.NoneDaoCommonServiceImpl;

@Service
public class DynamicServiceProvider extends NoneDaoCommonServiceImpl<Evaluation> implements RedisServiceProvider {

	private String dynamicListFlag="dynamicList.";//动态列表标识	
	private String dynamicGoodListFlag="dynamicGoodList.";//商品好评评论列表;
	private String dynamicInListFlag="dynamicInList.";//商品中评评论列表;
	private String dynamicBadListFlag="dynamicBadList.";//商品差评评论列表;
	private String dynamicPictureListFlag="dynamicPictureList.";//商品有图片评论列表;
	private String dynamicAdditionalListFlag="dynamicAdditionalList.";//商品有追加评论列表;
	private String dynamicMapFlag="dynamicMap.";//用户动态信息标识
	private String dynamicUserListFlag="dynamicUserList.";//用户动态列表标识
	private String dynamicGoodsUserListFlag="dynamicGoodsUserList.";//商品评分用户标识
	private String dynamicDescriptionUserListFlag="dynamicDescriptionUserList.";//商品描述评分用户标识
	private String dynamicServiceUserListFlag="dynamicServiceUserList.";//商品服务评分用户标识
	private String dynamicLogisticsUserListFlag="dynamicLogisticsUserList.";//商品物流评分用户标识
	
	private String dynamicStoreDescriptionScoreFlag="dynamicStoreDescriptionScore.";//店铺描述评分
	private String dynamicStoreGoodsScoreFlag="dynamicStoreGoodsScore.";//店铺商品评分
	private String dynamicStoreServiceScoreFlag="dynamicStoreServiceScore.";//店铺服务评分
	private String dynamicStoreLogisticsScoreFlag="dynamicStoreLogisticsScore.";//店铺物流评分
	
	private String dynamicCommentMapFlag="dynamicCommentMap.";//动态评论内容
	
	private String dynamicStoreListFlag="dynamicStoreList.";//店铺所有评论列表标识	
	private String dynamicStoreGoodListFlag="dynamicStoreGoodList.";//店铺好评评论列表;
	private String dynamicStoreInListFlag="dynamicStoreInList.";//店铺中评评论列表;
	private String dynamicStoreBadListFlag="dynamicStoreBadList.";//店铺差评评论列表;
	
	private String dynamicGoodRateFlag="dynamicGoodRateSize.";//好评率标识
	private String usersFlag="users.";//用户信息标识
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	
	private BuyerEvaluationService buyerEvaluationService;
	
	private OrderService orderService;
	
	@Override
	public String getId() {
		return "dynamic";
	}

	@Override
	public Object creatService(HttpServletRequest request) {

		String struct = request.getParameter("struct");
		Dynamic dynamic=new Gson().fromJson(struct,Dynamic.class);
		return dynamic;
	}
	
	@Override
	public Object doService(String serviceId, String struct, HttpServletRequest request) {
		String dynamicStatus=Utils.getParameter(request, "dynamicStatus");//动态操作状态 0:删除 1:添加|修改
		Map<String,Object> info=new HashMap<String,Object>();
		Gson gson=new Gson();
		Date now= new Date();
		if(!Utils.isEmpty(dynamicStatus)){
			if(dynamicStatus.equals("1")){
				
				String storeId=Utils.getParameter(request, "storeId"); //店铺ID
				String objId=Utils.getParameter(request, "objId");//文章ID
				String userId=Utils.getParameter(request, "userId");//用户ID
				String cmtId=Utils.getParameter(request, "cmtId");//评论ID
				String anonymous=Utils.getParameter(request, "anonymous");//是否匿名 0:否1:是
				String skuId=Utils.getParameter(request, "skuId");//规格ID				
				String dynamicMapKey=null;//用户动态key
				ListMeta dynamicList=new ListMeta(dynamicListFlag+"objId:"+objId);//记录评论列表
				//是否新评论信息
				if(cmtId==null){
					cmtId=BaseMeta.generateId();//生成评论Id	
					dynamicMapKey=dynamicMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId;					
					dynamicList.lAdd(dynamicMapKey);
					ListMeta dynamicStoreList=new ListMeta(dynamicStoreListFlag+"storeId:"+storeId);//记录评论列表
					dynamicStoreList.lAdd(dynamicMapKey);
					ListMeta dynamicUserList=new ListMeta(dynamicUserListFlag+"userId:"+userId);
					dynamicUserList.lAdd(dynamicMapKey);
				}else{
					dynamicMapKey=dynamicMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId;
				}
		
				HashMeta dynamicMap=new HashMeta(dynamicMapKey);
				String score=Utils.getParameter(request, "score");//评分信息-json字符串
				if(dynamicMap.get("dynamicScore")==null&&score!=null){	
					DynamicScore dynamicScore=gson.fromJson(score, DynamicScore.class);		
					int goodsScore=dynamicScore.getGoods();//商品分
					int serviceScore=dynamicScore.getService();//服务分
					int logisticsScore=dynamicScore.getLogistics();//物流分
					//记录描述用户
					if(dynamicScore.getDescription()!=0){
						ListMeta dynamicDescriptionUserList=new ListMeta(dynamicDescriptionUserListFlag+"objId:"+objId);
						dynamicDescriptionUserList.lAdd(userId);
						SortedSetMeta dynamicStoreDescriptionScore=new SortedSetMeta(dynamicStoreDescriptionScoreFlag+"objId:"+objId);
						dynamicStoreDescriptionScore.zadd(new Date().getTime(),BaseMeta.generateId()+goodsScore);
						float description=dynamicScore.getDescription();//描述分
						//好评
						if(description==4||description==5){
							ListMeta dynamicGoodList=new ListMeta(dynamicGoodListFlag+"objId:"+objId);
							dynamicGoodList.lAdd(dynamicMapKey);
							ListMeta dynamicStoreGoodList=new ListMeta(dynamicStoreGoodListFlag+"storeId:"+storeId);
							dynamicStoreGoodList.lAdd(dynamicMapKey);
							//记录好评率用于排序
							StringMeta dynamicGoodRate=new StringMeta(dynamicGoodRateFlag+"objId:"+objId);
							float dgr=dynamicList.size()/dynamicGoodList.size();
							dynamicGoodRate.set(dgr+"");
						//中评	
						}else if(description==3||description==2){
							ListMeta dynamicInList=new ListMeta(dynamicInListFlag+"objId:"+objId);
							dynamicInList.lAdd(dynamicMapKey);
							ListMeta dynamicStoreInList=new ListMeta(dynamicStoreInListFlag+"storeId:"+storeId);
							dynamicStoreInList.lAdd(dynamicMapKey);
						//差评	
						}else{
							ListMeta dynamicBadList=new ListMeta(dynamicBadListFlag+"objId:"+objId);
							dynamicBadList.lAdd(dynamicMapKey);
							ListMeta dynamicStoreBadList=new ListMeta(dynamicStoreBadListFlag+"storeId:"+storeId);
							dynamicStoreBadList.lAdd(dynamicMapKey);
						}
					}
					//商品分
					if(goodsScore!=0){					
						ListMeta dynamicGoodsUserList=new ListMeta(dynamicGoodsUserListFlag+"objId:"+objId);
						dynamicGoodsUserList.lAdd(userId);
						SortedSetMeta dynamicStoreGoodsScore=new SortedSetMeta(dynamicStoreGoodsScoreFlag+"storeId:"+storeId);
						dynamicStoreGoodsScore.zadd(new Date().getTime(),BaseMeta.generateId()+goodsScore);
					}
					//服务分
					if(serviceScore!=0){					
						ListMeta dynamicServiceUserList=new ListMeta(dynamicServiceUserListFlag+"objId:"+objId);
						dynamicServiceUserList.lAdd(userId);
						SortedSetMeta dynamicStoreServiceScore=new SortedSetMeta(dynamicStoreServiceScoreFlag+"storeId:"+storeId);
						dynamicStoreServiceScore.zadd(new Date().getTime(),BaseMeta.generateId()+serviceScore);
					}
					//物流分
					if(logisticsScore!=0){					
						ListMeta dynamicLogisticsUserListList=new ListMeta(dynamicLogisticsUserListFlag+"objId:"+objId);
						dynamicLogisticsUserListList.lAdd(userId);
						SortedSetMeta dynamicStoreLogisticsScore=new SortedSetMeta(dynamicStoreLogisticsScoreFlag+"storeId:"+storeId);
						dynamicStoreLogisticsScore.zadd(new Date().getTime(),BaseMeta.generateId()+goodsScore);
					}			
					dynamicScore.setCreateTime(df.format(new Date()));
					dynamicMap.set("score", gson.toJson(dynamicScore));								
				}
				String sunCommentPicture=Utils.getParameter(request, "sunCommentPicture");//晒图信息-数组字符串
				if(sunCommentPicture!=null&&sunCommentPicture!=""){				
					ListMeta dynamicPictureList=new ListMeta(dynamicPictureListFlag+"objId:"+objId);//有图片评论
					dynamicPictureList.lAdd(dynamicMapKey);
				}
				String comment=Utils.getParameter(request, "comment");//评论内容				
			    if(dynamicMap.get("comment")==null&&comment!=null){ 
			    	HashMeta commentMap=new HashMeta(dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);
			    	commentMap.set("userId", userId);
			    	commentMap.set("storeId", storeId);
			    	commentMap.set("objId", objId);
			    	commentMap.set("cmtId", cmtId);
			    	commentMap.set("skuId", skuId);
			    	commentMap.set("comment", comment);
			    	String determineReceipt=Utils.getParameter(request, "determineReceipt");//确定收货时间
			    	commentMap.set("determineReceipt", determineReceipt);
			    	commentMap.set("sunCommentPicture", sunCommentPicture);
			    	commentMap.set("commentTime", df.format(now));
			    	dynamicMap.set("comment", dynamicCommentMapFlag+"objId:"+objId+"cmtId:"+cmtId+"userId:"+userId);			    	
			    }
			    //默认匿名评论
				if(anonymous==null){
					anonymous="0";
				}
			    dynamicMap.set("anonymous", anonymous);
			    dynamicMap.set("userId", userId);
			    HashMeta userMap=new HashMeta(usersFlag+"userId:"+userId);
			    String nickName=userMap.get("nick");
			    if(anonymous.equals("1")){
			    	String beginStr=nickName.substring(0,1);
			    	String endStr=null;
			    	if(nickName.length()>2){
			    		endStr="***"+nickName.substring(nickName.length()-1);
			    	}else{
			    		endStr="***";
			    	}
			    	 dynamicMap.set("nickName", beginStr+endStr);
			    }else{
			    	 dynamicMap.set("nickName", nickName);
			    }
			  dynamicMap.set("sellerName","卖家测试用户");
			  String goodsTitle=Utils.getParameter(request, "goodsTitle");//商品标题
			  String skuInfo=Utils.getParameter(request, "skuInfo");//商品规格
			  dynamicMap.set("goodsTitle", goodsTitle);
			  dynamicMap.set("skuInfo", skuInfo);
			}
			String id=Utils.getParameter(request, "id");//商品自定义ID 用于回传页面隐藏评论层
			info.put("id",id);
			String orderId=Utils.getParameter(request, "orderId");//订单ID
			String userId=Utils.getParameter(request, "userId");//订单ID
			Order orders=getBuyerEvaluationService().getEvaluationOrder(orderId,userId);
			//如果为空更新订单信息的点评时间
			if(orders.getGoods().size()==0){
				Order order=getOrderService().getOrder(orderId);			
				order.setRateTime(now);
				getOrderService().save(order);
			}
			info.put("Code", "200");
			info.put("Msg", "评价操作成功!");			
		}else{			
			info.put("Code", "404");
			info.put("Msg", "没有动态操作!");		
		}
		String callBack = Utils.getParameter(request, "callback");
		if (Utils.notEmpty(callBack)) {
			return callBack + "(" + gson.toJson(info) + ")";
		}
		return gson.toJson(info);
	}

	@Override
	public Object getService(String serviceId, String struct, HttpServletRequest request) {
		Map<String,Object> info=new HashMap<String,Object>();
		Gson gson=new Gson();
		String objId=Utils.getParameter(request, "objId");//文章ID	
		if(objId==null){		
		   objId = (String) request.getAttribute("objId");
		}
		if(objId==null||objId=="")
		{   info.put("Code", "404");
			info.put("Msg", "商品参数为空!");
			return info;
		}
		String start = Utils.getParameter(request, "start"); //  开始位置
		String rows = Utils.getParameter(request, "rows"); // 每页显示数
		if(Utils.isEmpty(start)){
			start="0";
		}
		if(Utils.isEmpty(rows)){
			rows="10";
		}
		Long currPage=Long.valueOf(start)/Long.valueOf(rows)+1;//第几页
		Long end=Integer.valueOf(rows)*currPage-1;//结束
		ListMeta dynamicList=new ListMeta(dynamicListFlag+"objId:"+objId);//记录评论列表
		Long dynamicSize=dynamicList.size();
		List<Object> commentList=new ArrayList<Object>();
		for (String  dynamicMapKey: dynamicList.gets(Long.valueOf(start),end)) {
			HashMeta dynamicMap=new HashMeta(dynamicMapKey);
			HashMeta dynamicCommentMap=new HashMeta(dynamicMap.get("comment"));		
			Map<String, String> resultMap=dynamicMap.getAll();
//			Map<String, Object> resultMap=new HashMap<String,Object>();
//		    for(java.util.Map.Entry<String, String> entry: dynamicMap.getAll().entrySet()){
//		    	resultMap.put(entry.getKey(), entry.getValue());
//		    }
			resultMap.put("comment", gson.toJson(dynamicCommentMap.getAll()));
			commentList.add(resultMap);
		}
		info.put("data", commentList);//评论列表
		info.put("allCommentSize", dynamicSize);//所有评论大小
		DecimalFormat df = new DecimalFormat("0.00");   
		
		//好评
		ListMeta dynamicGoodList=new ListMeta(dynamicGoodListFlag+"objId:"+objId);
		Long dynamicGoodSize=dynamicGoodList.size();//好评大小
		info.put("dynamicGoodSize", dynamicGoodSize);
		if(dynamicGoodSize==0){
			info.put("dynamicGoodRate", 0);//好评率
		}else{
			info.put("dynamicGoodRate", df.format((float)dynamicGoodSize/dynamicSize*100));//好评率
		}
		
		//中评
		ListMeta dynamicInList=new ListMeta(dynamicInListFlag+"objId:"+objId);
		Long dynamicInSize=dynamicInList.size();//中评大小
		info.put("dynamicInSize", dynamicInSize);
		if(dynamicInSize==0){
			info.put("dynamicInRate", 0);//中评率
		}else{
			info.put("dynamicInRate", df.format((float)dynamicInSize/dynamicSize*100));//中评率
		}		
		//差评
		ListMeta dynamicBadList=new ListMeta(dynamicBadListFlag+"objId:"+objId);
		Long dynamicBadSize=dynamicBadList.size();//差评大小
		info.put("dynamicBadSize", dynamicBadSize);
		if(dynamicBadSize==0){
			info.put("dynamicBadRate", 0);//差评率
		}else{
			info.put("dynamicBadRate", df.format((float)dynamicBadSize/dynamicSize*100));//差评率
		}	
		//有图片
		ListMeta dynamicPictureList=new ListMeta(dynamicPictureListFlag+"objId:"+objId);
		info.put("dynamicPictureSize", dynamicPictureList.size());//带图片大小
		//追评
		ListMeta dynamicAdditionalList=new ListMeta(dynamicAdditionalListFlag+"objId:"+objId);
		info.put("dynamicAdditionalSize", dynamicAdditionalList.size());//追加评论大小
		info.put("Code", "200");
		info.put("Msg", "获取商品详情页评分相关信息!");	

		return info;
	}

	public BuyerEvaluationService getBuyerEvaluationService() {
		return buyerEvaluationService;
	}

	public void setBuyerEvaluationService(
			BuyerEvaluationService buyerEvaluationService) {
		this.buyerEvaluationService = buyerEvaluationService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
}
