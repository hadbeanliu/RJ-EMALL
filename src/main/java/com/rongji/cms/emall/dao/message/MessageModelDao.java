package com.rongji.cms.emall.dao.message;

import java.util.List;

import com.rongji.cms.emall.entity.EmlMessageModel;
import com.rongji.rjskeleton.dao.CommonDao;

public interface MessageModelDao extends CommonDao<EmlMessageModel> {
	
	public EmlMessageModel getMessageModelBySign(String sign);
	
	public List<EmlMessageModel> getMessageModelByRecevierType(String recevierType);
	
}
