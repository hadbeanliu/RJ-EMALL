package com.rongji.cms.emall.service.freight.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.freight.CarryModeDao;
import com.rongji.cms.emall.entity.EmlCarryMode;
import com.rongji.cms.emall.entity.EmlCarryMode_;
import com.rongji.cms.emall.entity.EmlFreightTemplate;
import com.rongji.cms.emall.entity.EmlFreightTemplate_;
import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.service.freight.CarryModeService;
import com.rongji.cms.emall.service.freight.FreightTemplateService;
import com.rongji.cms.emall.vo.CarryMode;
import com.rongji.cms.emall.vo.FreightTemplate;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class CarryModeServiceImpl extends
		CommonServiceImpl<CarryMode, EmlCarryMode, CarryModeDao> implements
		CarryModeService {

	@Autowired
	private FreightTemplateService freightTemplateService;
	
	@Override
	public CarryMode convertToVo(EmlCarryMode entity) {
		CarryMode vo=createVo();
		ConvertUtil.copyPropertiesByIgnore(entity ,vo, true, "freightTemplate");
		FreightTemplate freightTemplate = freightTemplateService.convertToVo(entity.getEmlFreightTemplate());
		vo.setFreightTemplate(freightTemplate);
		return vo;
	}

	@Override
	public EmlCarryMode convertToEntity(CarryMode vo) {
		EmlCarryMode entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "freightTemplate");
		EmlFreightTemplate freightTemplate=freightTemplateService.convertToEntity(vo.getFreightTemplate());
		entity.setEmlFreightTemplate(freightTemplate);
		return entity;
	}

	@Override
	protected CarryMode createVo() {
		return new CarryMode();
	}

	@Override
	protected EmlCarryMode createEntity() {
		return new EmlCarryMode();
	}

	@Override
	public List<EmlCarryMode> getCarryModesByTempId(String tempId) {
		Iterable<EmlCarryMode> ecms= getDao().findAll();
		List<EmlCarryMode> list=new ArrayList<EmlCarryMode>();
		for(EmlCarryMode ecm : ecms){
			if(ecm.getEmlFreightTemplate().getId().equals(tempId))
			{
				list.add(ecm);
			}
		}
		return list;
	}

	@Override
	public PageEntity<CarryMode> getByDatatables(final String tempId,final DatatablesMetadata metadata) {
		// TODO Auto-generated method stub
		return getByDatatables(metadata,new Specification<EmlCarryMode>() {
			
			@Override
			public Predicate toPredicate(Root<EmlCarryMode> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate carryEq = cb.equal(root.get(EmlCarryMode_.emlFreightTemplate).get(EmlFreightTemplate_.id), tempId);
				Predicate[] where = createDatatablesQuery(metadata, root, query, cb);
				if (where != null && where.length > 0) {
					Predicate[] w = new Predicate[where.length+1];
					w[0] = carryEq;
					for (int i = 0; i < where.length; i++) {
						w[i+1] = where[i];
					}
					query.where(w);
					
				} else {
					query.where(carryEq);
				}
				return null;
			}
		});
	}

	@Override
	public List<CarryMode> getListByFreightId(String tempId) {
		Iterable<EmlCarryMode> ecms= getDao().findAll();
		List<CarryMode> list=new ArrayList<CarryMode>();
		for(EmlCarryMode ecm : ecms){
			if(ecm.getEmlFreightTemplate().getId().equals(tempId))
			{
				list.add(convertToVo(ecm));
			}
		}
		return list;
	}

	@Override
	public void delCarrysByTempId(String tempId) {
		// TODO Auto-generated method stub
		Iterable<EmlCarryMode> lists=getDao().findAll();
		for(EmlCarryMode li : lists){
			if(li.getEmlFreightTemplate().getId().equals(tempId)){
				getDao().delete(li);
			}
		}
	}

	@Override
	public EmlCarryMode addCarry(CarryMode cm) {
		// TODO Auto-generated method stub
		EmlCarryMode cv = getDao().save(convertToEntity(cm));
		return cv;
	}
	
}
