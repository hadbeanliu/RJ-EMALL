package com.rongji.cms.emall.service.areas.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.areas.AreasDao;
import com.rongji.cms.emall.entity.EmlAreas;
import com.rongji.cms.emall.service.areas.AreasService;
import com.rongji.cms.emall.vo.Areas;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class AreasServiceImpl extends CommonServiceImpl<Areas, EmlAreas, AreasDao> implements AreasService {

	@Override
	protected Areas createVo() {
		return new Areas();
	}

	@Override
	protected EmlAreas createEntity() {
		return new EmlAreas();
	}

	@Override
	@Transactional
	public void SaveAreas(String[][] areas) {
		getDao().deleteAll();
		List<EmlAreas> entities=new ArrayList<EmlAreas>();
		for(String[] row : areas)
		{
			EmlAreas vo=new EmlAreas();
			vo.setId(row[0]);
			vo.setAreaName(row[1]);
			vo.setAreaCode(row[0]);
			if(row[0].endsWith("0000")){
				vo.setParentCode("1");
			}else if(row[0].endsWith("00")){
//				if(row[1].equals("市辖区")||row[1].equals("县")){
//					continue;
//				}
				vo.setParentCode(row[0].substring(0, 2)+"0000");
			}else{
//				if(row[1].equals("市辖区")||row[1].equals("县")){
//					continue;
//				}
				vo.setParentCode(row[0].substring(0, 4)+"00");
			}
			entities.add(vo);
		}
		getDao().save(entities);
	}

	@Override
	public List<EmlAreas> getProvince() {
		List<EmlAreas> areasList=new ArrayList<EmlAreas>();
		Iterable<EmlAreas> list = getDao().findAll();
		for(EmlAreas area:list){
			if(area.getAreaCode().endsWith("0000"))
				areasList.add(area);
		}
		return areasList;
	}

	@Override
	public List<EmlAreas> getCityByPCode(String code) {
		List<EmlAreas> areasList=new ArrayList<EmlAreas>();
		Iterable<EmlAreas> list = getDao().findAll();
		for(EmlAreas area:list){
			if(area.getParentCode().equals(code))
				areasList.add(area);
		}
		return areasList;
	}
	
	@Override
	public List<EmlAreas> getDistrictByPCode(String code) {
		List<EmlAreas> areasList=new ArrayList<EmlAreas>();
		Iterable<EmlAreas> list = getDao().findAll();
		for(EmlAreas area:list){
			if(area.getParentCode().equals(code))
				areasList.add(area);
		}
		return areasList;
	}

	@Override
	public String getAreaName(String code) {
		Iterable<EmlAreas> list = getDao().findAll();
		int intcode=Integer.parseInt(code);
		String p_code="";
		String c_code="";
		String d_code="";
		if(intcode%10000==0){
			p_code=code;
		}else if(intcode%100==0){
			p_code=intcode/10000+"0000";
			c_code=code;
		}else{
			p_code=intcode/10000+"0000";
			c_code=intcode/100+"00";
			d_code=code;
		}
		String areasName="";
		for(EmlAreas area:list){
			if(p_code.equals(area.getAreaCode())){
				areasName = area.getAreaName();
			}
			if(area.getAreaCode().equals(c_code)){
				if(area.getAreaName().equals("市辖区") || area.getAreaName().equals("县")){
					for(EmlAreas a : list){
						if(a.getAreaCode().equals(area.getParentCode())){
							areasName = areasName+a.getAreaName().substring(0, a.getAreaName().length()-1);
							break;
						}
					}
				}else{
					areasName = areasName+area.getAreaName();
				}
			}
			if(area.getAreaCode().equals(d_code)){
				areasName = areasName+area.getAreaName();
			}
		}
		return areasName;
	}

	@Override
	public List<EmlAreas> getCity() {
		List<EmlAreas> areasList=new ArrayList<EmlAreas>();
		Iterable<EmlAreas> list = getDao().findAll();
		for(EmlAreas area:list){
			if(!area.getAreaCode().endsWith("0000")&&area.getAreaCode().endsWith("00"))
				areasList.add(area);
		}
		return areasList;
	}

	@Override
	public List<EmlAreas> getDistrict() {
		List<EmlAreas> areasList=new ArrayList<EmlAreas>();
		Iterable<EmlAreas> list = getDao().findAll();
		for(EmlAreas area:list){
			if(!area.getAreaCode().endsWith("0000")&&!area.getAreaCode().endsWith("00"))
				areasList.add(area);
		}
		return areasList;
	}
}
