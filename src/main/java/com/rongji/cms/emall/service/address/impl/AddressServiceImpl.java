package com.rongji.cms.emall.service.address.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.rongji.cms.emall.dao.address.AddressDao;
import com.rongji.cms.emall.entity.EmlAddress;
import com.rongji.cms.emall.entity.EmlAddress_;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.areas.AreasService;
import com.rongji.cms.emall.vo.Address;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class AddressServiceImpl extends CommonServiceImpl<Address, EmlAddress, AddressDao> implements AddressService {

	@Autowired
	protected AreasService areasService;
	
	@Override
	protected Address createVo() {
		return new Address();
	}

	@Override
	protected EmlAddress createEntity() {
		return new EmlAddress();
	}
	


	@Override
	protected Predicate[] createDatatablesQuery(DatatablesMetadata metadata,
			Root<EmlAddress> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<String[]> searchs = metadata.getSearchs();
		Predicate[] ws = new Predicate[searchs.size()];
		int i = 0;
		for (String[] search : searchs) {
			if (search[1].matches("\\d+")) {
				Path<Object> searchName = root.get(search[0]);
				ws[i] = cb.equal(searchName,
						Integer.valueOf(search[1]));
			} else {
				ws[i] = cb.like(root.<String>get(search[0]), "%"+search[1]+"%");
			}
			i++;
		}
		query.where(ws);
		//super.createDatatablesQuery(metadata, root, query, cb);
		return ws;
	}

	public List<Address> getAddressByUserId(String userId, String type){	
		return convertToVos(getDao().getAddressByUserId(userId,type));
	}

    @Transactional
	public void setDefaultAddress(String addressId,String userId, String type) {
    	for(Address vo : convertToVos(getDao().getAddressByUserId(userId,type))){
    		if(vo.getId().equals(addressId)){
    			vo.setIsDefault("1");
    			super.save(vo);
    		}else{
    			vo.setIsDefault("0");
    			super.save(vo);
    		}
			
		}
	}

	@Override
	@Transactional
	public <S extends Address> S updateByFilter(S vo, String... attrs) {
		String code=vo.getDistrict();
		if(code == null){
			code = vo.getCity();
		}
		if(code == null){
			code=vo.getProvince();
		}
		vo.setAreas(areasService.getAreaName(code));
		Address address=getOne(vo.getId());
		vo.setDistrict(code);
		vo.setType(address.getType());
		vo.setUserId(address.getUserId());
		vo.setIsDefault(address.getIsDefault());
		vo.setIsDel(address.getIsDel());
		return save(vo);
	}

	@Override
	public PageEntity<Address> getByDatatablesByUserId(final String userId,
			final DatatablesMetadata metadata, final String type) {
		return getByDatatables(metadata, new Specification<EmlAddress>() {
			@Override
			public Predicate toPredicate(Root<EmlAddress> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlAddress_.userId), userId);
				Predicate p2 = cb.equal(root.get(EmlAddress_.type), type);
				//Predicate addressEq = cb.equal(root.get(EmlAddress_.userId), userId);
				//Predicate[] where = createDatatablesQuery(metadata, root, query, cb);
				query.where(p1,p2);
				
				return null;
			}
		});
	}

	public String getFullAddressById(String addressId) {
		EmlAddress address = getDao().findOne(addressId);
		String fulladdress=null;
		if(address!=null){
			fulladdress = address.getAreas()+address.getAddress();
		}
		return fulladdress;
	}

}
