package com.rongji.cms.emall.service.areas;

import java.util.List;

import com.rongji.cms.emall.entity.EmlAreas;
import com.rongji.cms.emall.vo.Areas;
import com.rongji.rjskeleton.service.CommonService;

public interface AreasService extends CommonService<Areas, EmlAreas> {
	public void SaveAreas(String[][] areas);
	public List<EmlAreas> getProvince();
	public List<EmlAreas> getCityByPCode(String code);
	public List<EmlAreas> getCity();
	public List<EmlAreas> getDistrictByPCode(String code);
	public List<EmlAreas> getDistrict();
	
	/**
	 * 根据地区区号获取完整地址中文
	 * @param code 地区区号
	 * @return 完整地址中文
	 */
	public String getAreaName(String code);
}
