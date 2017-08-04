package com.rongji.cms.emall.service.order;

import com.rongji.cms.emall.dao.order.SellerOrderGoodsDao;
import com.rongji.cms.emall.entity.EmlOrderGoods;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

public interface SellerOrderGoodsService extends CommonService<OrderGoods, EmlOrderGoods>{
	
	public PageEntity<OrderGoods> getSoldGoodsByStoreId(String storeId, DatatablesMetadata metadata);

}
