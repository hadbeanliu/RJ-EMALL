package com.rongji.cms.emall.web.activity;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlMyBond_;
import com.rongji.cms.emall.service.activity.AuctionRecordService;
import com.rongji.cms.emall.service.activity.MyBondService;
import com.rongji.cms.emall.vo.AuctionRecord;
import com.rongji.cms.emall.vo.MyBond;
import com.rongji.dfish.base.Utils;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/buyer/auction/bond")
public class MyBondController extends CrudAndPageByJsonController<MyBond> {
	
	
	@Autowired
	private MyBondService myBondService;
	
	@Autowired
	private AuctionRecordService auctionRecordService;

	@Override
	protected CommonService<MyBond, ?> getService() {
		return myBondService;
	}

	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("我的保证金")
	public String index(Model model) {
		return "/dsww/001/buyer-myauction";	
	}		
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {	
		PAGE_INFO.addColumn(EmlMyBond_.id, false, false, false, false);
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	@Override
	protected PageEntity<MyBond> getPage(DatatablesMetadata metadata) {
		String userId=getCurrUserId();
		metadata.addSearch(EmlMyBond_.userId,userId);
		return super.getPage(metadata);
	}

	@Transactional
	@ResponseBody
	@RequestMapping(value = "/saveBondAndRecord",method=RequestMethod.GET)
	@RequestMappingDescription("保存记录")
	public String saveBondAndRecord(MyBond vo) {
	
		if (getCurrUserId() != null) {					
			if(myBondService.getMyBond(getCurrUserId(), vo.getGoods().getGoodsId())==null){			
				vo.setUserId(getCurrUserId());
				String callBack = Utils.getParameter(getRequest(), "callback");
			     Gson gson=new Gson();
				AuctionRecord auctionRecord=new AuctionRecord();
				auctionRecord.setShootingTime(vo.getShootingTime());
				auctionRecord.setEndTime(vo.getEndTime());
				MyBond mb=new MyBond();
				mb=myBondService.save(vo);
				auctionRecord.setUserId(getCurrUserId());
				auctionRecord.setMyBond(mb);
				auctionRecord.setGoods(mb.getGoods());
				auctionRecord.setAuctionQuantity(mb.getGoods().getGoodsNum());
				auctionRecord.setCurrentPrice(mb.getGoods().getGoodsPrice());
				auctionRecordService.save(auctionRecord);
				return callBack + "(" + gson.toJson(mb) + ")";
			}
		}
		return null;
	}	
}
