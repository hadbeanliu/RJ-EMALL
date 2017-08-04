package com.rongji.cms.emall.service.company.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.company.CompanyDao;
import com.rongji.cms.emall.entity.EmlCompany;
import com.rongji.cms.emall.service.company.CompanyService;
import com.rongji.cms.emall.vo.Company;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class CompanyServiceImpl extends CommonServiceImpl<Company, EmlCompany, CompanyDao> implements CompanyService{

	@Override
	protected Company createVo() {
		return new Company();
	}

	@Override
	protected EmlCompany createEntity() {
		return new EmlCompany();
	}

	@Override
	public List<EmlCompany> getCompanyByUserId(String userId) {
		return getDao().getCompanyByUserId(userId);
	}

	@Override
	public boolean isPassForCompany(String userId) {
		List<EmlCompany> companieslList = getCompanyByUserId(userId);
		EmlCompany company = null;
		if(companieslList.size()>0){
			company=	companieslList.get(0);
		}
		
		if(company != null ){
			if("1".equals(company.getIsCompanyauthentication())) {
				
				return true;
				}
		}
		return false;
	}

	@Override
	public boolean isPassForService(String userId) {
		if(userId != null && getOne(userId)!= null){
			if(getOne(userId).getIsServiceauthentication() == "1") return true;
		}
		return false;
	}

}
