package com.rongji.cms.emall.service.complaint;


import com.rongji.cms.emall.vo.Complaint;
import com.rongji.cms.emall.vo.MesAuditSearchQuery;
import com.rongji.rjskeleton.service.NoneDaoCommonService;

public interface ComplaintService extends NoneDaoCommonService<Complaint>{
	public MesAuditSearchQuery getdetailById(String id);
}
