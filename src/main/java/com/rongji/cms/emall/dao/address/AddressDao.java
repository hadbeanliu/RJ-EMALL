package com.rongji.cms.emall.dao.address;

import java.util.List;

import com.rongji.cms.emall.entity.EmlAddress;
import com.rongji.rjskeleton.dao.CommonDao;

public interface AddressDao extends CommonDao<EmlAddress>{
	
	public List<EmlAddress> getAddressByUserId(final String userId, final String type);
	

}
