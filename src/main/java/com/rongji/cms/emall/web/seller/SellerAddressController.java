package com.rongji.cms.emall.web.seller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.entity.EmlAddress_;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.areas.AreasService;
import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.vo.Address;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/seller/address")
public class SellerAddressController extends CrudAndPageByJsonController<Address>  {	
	@Autowired
	private AddressService addressService; 
	
	@Autowired
	private AreasService areasService; 
	
	@Autowired
	private MessageModelService messageModelService; 
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("卖家发货、退款地址管理界面")
	public String index(Model model) {	
		String userId = getCurrUserId();
        model.addAttribute("userId", userId);
        return "/dsww/001/seller-address";
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	private static PageInfo PAGE_INFO = new PageInfo();
	static {
		PAGE_INFO.addColumn(EmlAddress_.id, false, false, false, false);	
	}

	@Override
	protected PageEntity<Address> getPage(DatatablesMetadata metadata) {
		String userId = getCurrUserId();
		//metadata.getSearchs().add(new String[]{EmlAddress_.userId.getName(), userId});
		return addressService.getByDatatablesByUserId(userId, metadata, "1");
	}

	@Override
	protected CommonService<Address, ?> getService() {
		return addressService;
	}

	@Override
	protected Address doSaveVo(Address vo) {
		String userId = getCurrUserId();
		vo.setUserId(userId);
		vo.setAreas(areasService.getAreaName(vo.getDistrict()));
		vo.setType("1");
		vo.setIsDefault("0");
		return super.doSaveVo(vo);
	}

	@RequestMapping(value="vo_setIsDefault")
	@ResponseBody
	public boolean setIsRead(String addressId){
		String userId = getCurrUserId();
		addressService.setDefaultAddress(addressId, userId, "1");
		return true;
	}
	
	
	@RequestMapping(value="vo_getAddress")
	@ResponseBody
	public Address getAddress(String addressId){
		Address address = addressService.getOne(addressId);
		return address;
	}

}
