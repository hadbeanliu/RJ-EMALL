package com.rongji.cms.emall.service.freight;

import java.util.List;

import com.rongji.cms.emall.entity.EmlCarryMode;
import com.rongji.cms.emall.vo.CarryMode;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

public interface CarryModeService extends CommonService<CarryMode, EmlCarryMode> {
	public List<EmlCarryMode> getCarryModesByTempId(String tempId);
	
	public List<CarryMode> getListByFreightId(String tempId);
	
	public PageEntity<CarryMode> getByDatatables(final String tempId,final DatatablesMetadata metadata);
	
	public EmlCarryMode addCarry(CarryMode cm);
	//删除运送方式
	public void delCarrysByTempId(String tempId);
}
