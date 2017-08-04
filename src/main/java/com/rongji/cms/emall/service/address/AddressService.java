package com.rongji.cms.emall.service.address;

import java.util.List;


import com.rongji.cms.emall.entity.EmlAddress;
import com.rongji.cms.emall.vo.Address;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

public interface AddressService extends CommonService<Address, EmlAddress>{
	
	/**
	 * 根据用户ID获取收货地址
	 * param：userId用户ID
	 * param：type地址类型 0收货地址 1发货、退款地址
	 */
	public List<Address> getAddressByUserId(String userId ,String type);
	
	/**
	 * 根据用户ID获取地址带条件过滤查询分页
	 * @param userId
	 * @param metadata
	 * @param type 地址类型 0收货地址 1发货、退款地址
	 * @return 如果userId或者metadata为{@literal null}，则返回null
	 */
	public PageEntity<Address> getByDatatablesByUserId(String userId, DatatablesMetadata metadata, String type);
	
	/**
	 * 根据收货地址ID设置默认收货地址
	 * @param addressId
	 */
	public void setDefaultAddress(String addressId,String userId, String type);
	
	/**
	 * 根据收货地址ID获取完整的收货地址
	 * @param addressId
	 */
	public String getFullAddressById(String addressId);
}
