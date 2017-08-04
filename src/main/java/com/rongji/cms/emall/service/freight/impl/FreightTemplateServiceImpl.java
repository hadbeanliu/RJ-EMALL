package com.rongji.cms.emall.service.freight.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongji.cms.emall.dao.freight.FreightTemplateDao;
import com.rongji.cms.emall.entity.EmlCarryMode;
import com.rongji.cms.emall.entity.EmlFreightTemplate;
import com.rongji.cms.emall.entity.EmlStore;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.freight.CarryModeService;
import com.rongji.cms.emall.service.freight.FreightTemplateService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.Address;
import com.rongji.cms.emall.vo.CarryMode;
import com.rongji.cms.emall.vo.FreightTemplate;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class FreightTemplateServiceImpl extends CommonServiceImpl<FreightTemplate, EmlFreightTemplate, FreightTemplateDao> implements FreightTemplateService {

	@Autowired
	private CarryModeService carryModeService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	
	@Override
	protected FreightTemplate createVo() {
		return new FreightTemplate();
	}

	@Override
	protected EmlFreightTemplate createEntity() {
		return new EmlFreightTemplate();
	}
	
	@Override
	public FreightTemplate convertToVo(EmlFreightTemplate entity) {
		FreightTemplate vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "store");
		Store store = storeService.convertToVo(entity.getStore());
		vo.setStore(store);
		return vo;
	}
	
	@Override
	public EmlFreightTemplate convertToEntity(FreightTemplate vo) {
		EmlFreightTemplate entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "store");
		EmlStore store = storeService.convertToEntity(vo.getStore());
		entity.setStore(store);
		return entity;
	}
	
	

	@Override
	public PageEntity<FreightTemplate> getByDatatables(
			DatatablesMetadata metadata) {
		PageEntity<FreightTemplate> pe=super.getByDatatables(metadata);
		List<FreightTemplate> fts=pe.getData(),ffts=new ArrayList<FreightTemplate>();
		
		for(FreightTemplate ft : fts){
			ft.setCarryMode(carryModeService.getListByFreightId(ft.getId()));
			ffts.add(ft);
		}
		pe.setData(ffts);
		return pe;
	}

	@Override
	@Transactional
	public FreightTemplate addFreightTemplate(FreightTemplate vo) {
		EmlFreightTemplate eft = convertToEntity(vo);
		if(eft.getStatus().equals("1")){
			for(EmlFreightTemplate ft:getDao().findAll()){
				if(ft.getStatus().equals("1")){
					ft.setStatus("0");
					getDao().saveAndFlush(ft);
				}
			}
		}
		EmlStore store=storeService.convertToEntity(vo.getStore());
		eft.setStore(store);
		List<CarryMode> list=new ArrayList<CarryMode>();
		FreightTemplate od = convertToVo(getDao().save(eft));
		getDao().flush();
		for (CarryMode cm : vo.getCarryMode()) {
			if(cm.getServiceType()!=""){
				if(cm.getFee()!="" && cm.getAddedFee()!=""){
					cm.setFreightTemplate(od);
					list.add(cm);
				}
			}
		}
		if(list!=null){
			carryModeService.save(list);
		}
		
		return od;
	}

	@Override
	public List<FreightTemplate> getFreightTemps(String storeId) {
		Iterable<EmlFreightTemplate> lists=getDao().findAll();
		List<FreightTemplate> fts=new ArrayList<FreightTemplate>();
		for(EmlFreightTemplate eft:lists){
			if(eft.getStore().getStoreId().equals(storeId)){
				fts.add(convertToVo(eft));
			}
		}
		for(FreightTemplate ft : fts){
			ft.setCarryMode(carryModeService.getListByFreightId(ft.getId()));
		}
		return fts;
	}

	@Override
	public FreightTemplate getFreightTemp(String voId) {
		Iterable<EmlFreightTemplate> efts= getDao().findAll();
		EmlFreightTemplate ft = new EmlFreightTemplate();
		for(EmlFreightTemplate eft : efts){
			if(eft.getId().equals(voId)){
				ft=eft;
			}
		}
		List<EmlCarryMode> cms = carryModeService.getCarryModesByTempId(ft.getId());
		FreightTemplate ftemp=convertToVo(ft); 
		List<CarryMode> cmlist =new ArrayList<CarryMode>();
		for(EmlCarryMode cm :cms){
			cmlist.add(carryModeService.convertToVo(cm));
		}
		ftemp.setCarryMode(cmlist);
		return ftemp;
	}

	@Override
	@Transactional
	public FreightTemplate updateFreightTemplate(FreightTemplate vo) {
		EmlFreightTemplate eft = convertToEntity(vo);
		if("1".equals(eft.getStatus())){
			for(EmlFreightTemplate ft:getDao().findAll()){
				if(ft.getStatus().equals("1")){
					ft.setStatus("0");
					getDao().saveAndFlush(ft);
				}
			}
		}

		EmlStore store=storeService.convertToEntity(vo.getStore());
		eft.setStore(store);
		Iterable<EmlFreightTemplate> ftlist=getDao().findAll();
		for(EmlFreightTemplate e : ftlist){
			if(e.getId().equals(vo.getId())){
				getDao().delete(e);
			}
		}
		carryModeService.delCarrysByTempId(vo.getId());
		List<CarryMode> list=new ArrayList<CarryMode>();
		FreightTemplate od = convertToVo(getDao().saveAndFlush(eft));
		for (CarryMode cm : vo.getCarryMode()) {
			if(cm.getFee()!="" && cm.getAddedFee()!=""){
				cm.setFreightTemplate(od);
				list.add(cm);
			}
		}
		if(list!=null){
			carryModeService.save(list);
		}
		return od;
	}

	/*
	 * 根据运送方式编号和订单件数来计算运费
	 * 
	 */
	@Override
	public int getFreightCarryFee(int piece, String carryId) {
		CarryMode cm=carryModeService.getOne(carryId);
		int fee=(int) (Float.parseFloat(cm.getFee())*100);
		fee=fee+(int) (Float.parseFloat(cm.getAddedFee())*100*(piece-1));
		return fee;
	}

	@Override
	@Transactional
	public void delete(String id) {
		carryModeService.delCarrysByTempId(id);
		super.delete(id);
	}

	@Override
	public List<CarryMode> getCarryMode(String storeId) {
		List<FreightTemplate> ftl=getFreightTemps(storeId);
		List<CarryMode> carrylist=new ArrayList<CarryMode>();
		for(FreightTemplate ft : ftl){
			if("1".equals(ft.getStatus())){
				carrylist.addAll(ft.getCarryMode());
			}
		}
		return carrylist;
	}

	/*
	 * 根据订单和运送方式编号计算运费
	 */
	@Override
	public int calculateFee(Order order, String carryId) {
//		Order order = orderService.getPreOrder(OrderId);
		List<OrderGoods> goods = order.getGoods();
		int count = 0;
		for(OrderGoods gs : goods){
			count=count+gs.getPurchaseQuantity();
		}
		return getFreightCarryFee(count,carryId);
	}

	@Override
	public List<CarryMode> getCarryMode(String storeId, String addressId) {
		int flag = 0;
		Address address = addressService.getOne(addressId);
		List<CarryMode> uselist = getCarryMode(storeId);
		List<CarryMode> list = new ArrayList<CarryMode>();
		for(CarryMode cm : uselist){
			if("1".equals(cm.getIsDefault())){
				String[] codesstr=cm.getAreascodes().split("_");
				for(String str : codesstr){
					String code = address.getDistrict().substring(0, 2)+"0000";
					if(str.equals(code)){
						flag = 1;
						break;
					}
				}
			}
		}
		for(CarryMode cm : uselist){
			if(flag == 1)
			{
				if("1".equals(cm.getIsDefault()))
					list.add(cm);
			}else{
				if("0".equals(cm.getIsDefault()))
					list.add(cm);
			}
		}
		return list;
	}	
}
