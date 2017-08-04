package com.rongji.cms.emall.service.examine.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.examine.ExamineDao;
import com.rongji.cms.emall.entity.EmlExamine;
import com.rongji.cms.emall.service.examine.ExamineService;
import com.rongji.cms.emall.vo.Examine;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class ExamineServiceImpl extends CommonServiceImpl<Examine, EmlExamine, ExamineDao> implements ExamineService {

	@Override
	protected EmlExamine createEntity() {
		return new EmlExamine();
	}

	@Override
	protected Examine createVo() {
		return new Examine();
	}

	@Override
	public List<EmlExamine> getExamine(String userId,String authentications) {
		return getDao().getExamine(userId, authentications);
	}

	@Override
	public EmlExamine getOne(String userId,String status, String authentications) {
		return getDao().getOne(userId,status, authentications);
	}

}
