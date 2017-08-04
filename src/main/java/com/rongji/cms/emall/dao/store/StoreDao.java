package com.rongji.cms.emall.dao.store;

import com.rongji.cms.emall.entity.EmlStore;
import com.rongji.rjskeleton.dao.CommonDao;

public interface StoreDao extends CommonDao<EmlStore>{

	EmlStore getStoreByUserId(String userId);
	EmlStore getStoreByCmsId(String cmsId);

}
