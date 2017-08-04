package com.rongji.cms.emall.web.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlAuctionRecord_;
import com.rongji.cms.emall.service.activity.AuctionRecordService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.AuctionRecord;
import com.rongji.cms.emall.vo.AuctionRecordList;
import com.rongji.dfish.base.Utils;
import com.rongji.dfish.cache.meta.DoubleMeta;
import com.rongji.dfish.cache.meta.ListMeta;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/buyer/myauction")
public class AuctionRecordController extends CrudAndPageByJsonController<AuctionRecord> {
	
	private String auctionHighestPriceFlag="auctionHighestPrice.";//最高价标识
	private String auctionRecordListFlag="auctionRecordList.";//拍卖记录列表标识
	@Autowired
	private AuctionRecordService auctionRecordService;
	@Autowired
	private StoreService storeService;

	@Override
	protected CommonService<AuctionRecord, ?> getService() {
		return auctionRecordService;
	}

	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("拍卖记录信息")
	public String index(Model model) {
		  return "/dsww/001/buyer-myauction";	
	}		
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.addColumn(EmlAuctionRecord_.id, false, false, false, false);
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<AuctionRecord> getPage(DatatablesMetadata metadata) {
		String userId=getCurrUserId();
		if(userId!=null){
			metadata.addSearch(EmlAuctionRecord_.userId, userId);
		}
		return super.getPage(metadata);
	}

	@ResponseBody
	@RequestMapping(value="/addPrice")
	@RequestMappingDescription("拍卖出价")
	protected String addPrice(AuctionRecord vo) {
		String callBack = Utils.getParameter(getRequest(), "callback");
		String json=null;
		synchronized (this) {
			DoubleMeta dm=new DoubleMeta(auctionHighestPriceFlag+"objId:"+vo.getGoods().getGoodsId());
			Double auctionPrice=Double.valueOf(vo.getMyHighestPrice());//出价		
			//出价高于最大值时
			if(auctionPrice>dm.get()){				
				dm.plus(auctionPrice);
				double currentPrice=dm.get();
				//添加记录
				ListMeta auctionRecordList=new ListMeta(auctionRecordListFlag+"objId:"+vo.getGoods().getGoodsId());
				Gson gson=new Gson();
				AuctionRecordList arl=new AuctionRecordList();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				arl.setCreateTime(sd.format(new Date()));
				arl.setUserId(getCurrUserId());
				arl.setPrice(currentPrice);
				auctionRecordList.add(gson.toJson(arl));
				//更新最高价格
				AuctionRecord ar=auctionRecordService.getAuctionRecord(getCurrUserId(), vo.getGoods().getGoodsId());
				ar.setMyHighestPrice(Float.valueOf(currentPrice+""));
				super.doSaveVo(ar);
				for (AuctionRecord AuctionRecord : auctionRecordService.getAuctionRecords(vo.getGoods().getGoodsId())) {
					AuctionRecord.setCurrentPrice(currentPrice);
					auctionRecordService.save(AuctionRecord);
				}				
				json= "{\"Code\":\"202\",\"Msg\":\"出价成功!\"}";
			}else{
				json= "{\"Code\":\"404\",\"Msg\":\"出价失败!\"}";
			}	
			if (Utils.notEmpty(callBack)) {
				return callBack + "(" + json + ")";
			}
		}		
		return null;
	}	
	
	@ResponseBody
	@RequestMapping(value="/getHighestPriceByGoodsId")
	@RequestMappingDescription("获取最高价")
	public String getHighestPriceByGoodsId(){
		String objId=Utils.getParameter(getRequest(), "objId");
		String callBack = Utils.getParameter(getRequest(), "callback");
		String json=null;
		if(objId!=null){
			DoubleMeta dm=new DoubleMeta(auctionHighestPriceFlag+"objId:"+objId);
			json= "{\"Code\":\"200\",\"highestPrice\":"+dm.get()+"}";
		}else{
			json= "{\"Code\":\"404\",\"Msg\":\"商品ID不许为空!\"}";
		}
		if (Utils.notEmpty(callBack)) {
			return callBack + "(" + json + ")";
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getAuctionRecordsByGoodsId")
	@RequestMappingDescription("获取商品拍卖记录")
	public String getAuctionRecordsByGoodsId(){
		String objId=Utils.getParameter(getRequest(), "objId");
		String callBack = Utils.getParameter(getRequest(), "callback");
		String json=null;
		if(objId!=null){
			ListMeta auctionRecordList=new ListMeta(auctionRecordListFlag+"objId:"+objId);
			json= "{\"Code\":\"200\",\"data\":"+auctionRecordList.gets(0, -1)+"}";
		}else{
			json= "{\"Code\":\"404\",\"Msg\":\"商品ID不许为空!\"}";
		}
		if (Utils.notEmpty(callBack)) {
			return callBack + "(" + json + ")";
		}
		return null;
	}
}
