package com.rongji.cms.emall.service.crowdfunding.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongji.cms.emall.dao.crowdfunding.CrowdfundingDao;
import com.rongji.cms.emall.entity.EmlCrowdfunding;
import com.rongji.cms.emall.entity.EmlCrowdfundingSponsor;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingService;
import com.rongji.cms.emall.service.crowdfunding.CrowdfundingSponsorService;
import com.rongji.cms.emall.vo.Crowdfunding;
import com.rongji.cms.emall.vo.CrowdfundingSponsor;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class CrowdfundingServiceImpl extends CommonServiceImpl<Crowdfunding, EmlCrowdfunding, CrowdfundingDao> implements CrowdfundingService {
	
	@Value("${rjskeleton.date.format}")
	private String dateFormat;
	
	@Autowired
	private CrowdfundingSponsorService crowdfundingSponsorService;

	@Override
	protected EmlCrowdfunding createEntity() {		
		
		return new EmlCrowdfunding();
	}	

	@Override
	protected Crowdfunding createVo() {
		return new Crowdfunding();
	}

	@Override
	public EmlCrowdfunding convertToEntity(Crowdfunding vo) {
		EmlCrowdfunding entity=createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true,"crowdfundingSponsor","projectPictureFile");
		if(vo.getCrowdfundingSponsor()!=null){
			EmlCrowdfundingSponsor crowdfundingSponsor=crowdfundingSponsorService.convertToEntity(vo.getCrowdfundingSponsor());
			entity.setCrowdfundingSponsor(crowdfundingSponsor);
		}		
		return entity;
	}

	@Override
	public Crowdfunding convertToVo(EmlCrowdfunding entity) {
		Crowdfunding vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true,"crowdfundingSponsor","projectPictureFile");
		if(entity!=null){
			if(entity.getCrowdfundingSponsor()!=null){
				CrowdfundingSponsor crowdfundingSponsor=crowdfundingSponsorService.convertToVo(entity.getCrowdfundingSponsor());
				vo.setCrowdfundingSponsor(crowdfundingSponsor);
			}		
		}
		return vo;
	}
	
	@Transactional
	@Override
	public <S extends Crowdfunding> S save(S vo) {
		if (vo != null && vo.getId() == null) {
			vo.setCreateTime(new Date());
		}
		return super.save(vo);
	}

	@Override
	public Boolean getCrowdfunding(String userId, String crowdfundingId) {
	    Crowdfunding crowdfunding=convertToVo(getDao().getCrowdfunding(userId, crowdfundingId));
		if(crowdfunding.getId()!=null){
			return true;
		}
		return false;	
	}
}
