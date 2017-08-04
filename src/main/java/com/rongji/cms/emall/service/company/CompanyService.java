package com.rongji.cms.emall.service.company;

import java.util.List;

import com.rongji.cms.emall.entity.EmlCompany;
import com.rongji.cms.emall.vo.Company;
import com.rongji.rjskeleton.service.CommonService;

public interface CompanyService extends CommonService<Company, EmlCompany>{
	/**
	 * 通过用户编号获得Company数据列表
	 * @param userId 用户编号
	 * @return
	 */
	public List<EmlCompany> getCompanyByUserId(String userId);
	/**
	 * 判断企业认证是否通过
	 * @param userId 用户编号
	 * @return
	 */
	public boolean isPassForCompany(String userId);
	/**
	 * 判断服务认证是否通过
	 * @param userId 用户编号
	 * @return
	 */
	public boolean isPassForService(String userId);
}
