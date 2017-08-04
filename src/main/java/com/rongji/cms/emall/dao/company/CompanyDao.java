package com.rongji.cms.emall.dao.company;

import java.util.List;

import com.rongji.cms.emall.entity.EmlCompany;
import com.rongji.rjskeleton.dao.CommonDao;

public interface CompanyDao extends CommonDao<EmlCompany>{
	public List<EmlCompany> getCompanyByUserId(String userId);
}
