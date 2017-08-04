package com.rongji.cms.emall.service.activity;

import java.util.List;

import com.rongji.cms.emall.entity.EmlAuctionRecord;
import com.rongji.cms.emall.vo.AuctionRecord;
import com.rongji.rjskeleton.service.CommonService;

public interface AuctionRecordService extends CommonService<AuctionRecord, EmlAuctionRecord> {
	/**
	 * 获取用户拍卖记录
	 * @param userId 用户ID
	 * @param goodsId 商品ID
	 * @return
	 */
	public AuctionRecord getAuctionRecord(String userId,String goodsId);
	
	/**
	 * 获取商品拍卖记录
	 * @param goodsId 商品ID
	 * @return
	 */
    public List<AuctionRecord> getAuctionRecords(String goodsId);
}
