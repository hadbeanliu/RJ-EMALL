package com.rongji.cms.emall.dao.message;

import java.util.List;

import com.rongji.cms.emall.entity.EmlMessagePersonModel;
import com.rongji.rjskeleton.dao.CommonDao;

public interface MessagePersonModelDao extends CommonDao<EmlMessagePersonModel>{
	
	public List<EmlMessagePersonModel> getMessageModelByReceiverType(String userId);
	
	public EmlMessagePersonModel getActiveBySign(String sign, String userId);

}
