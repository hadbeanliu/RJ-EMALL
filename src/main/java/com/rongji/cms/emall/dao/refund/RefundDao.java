package com.rongji.cms.emall.dao.refund;

import java.util.List;

import com.rongji.cms.emall.entity.EmlRefund;
import com.rongji.rjskeleton.dao.CommonDao;

public interface RefundDao extends CommonDao<EmlRefund>{
	public List<EmlRefund> getfundByOrderId(String orderId);
}
