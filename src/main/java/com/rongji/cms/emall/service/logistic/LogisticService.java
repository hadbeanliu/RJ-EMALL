package com.rongji.cms.emall.service.logistic;
import com.rongji.cms.emall.entity.EmlLogistics;
import com.rongji.cms.emall.vo.Logistics;
import com.rongji.cms.emall.vo.Order;
import com.rongji.rjskeleton.service.CommonService;

public interface LogisticService extends CommonService<Logistics, EmlLogistics>{
	public Logistics sendGoods(String userId,Logistics vo);
	public Order getOrder(String orderId);
}
