package com.rongji.cms.emall.web.user;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlAddress_;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.areas.AreasService;
import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.vo.Address;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.support.valid.FormFieldValidDefine;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Controller
@RequestMapping("/user/address")
public class AddressController extends CrudAndPageByJsonController<Address>  {	
	@Autowired
	private AddressService addressService; 
	
	@Autowired
	private AreasService areasService; 
	
	@Autowired
	private MessageModelService messageModelService; 
	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("用户收货地址管理界面")
	public String index(Model model) {	
		String userId = getCurrUserId();
        model.addAttribute("userId", userId);
//		Map<String, String> tmp = new HashMap<String, String>();
//		tmp.put("order.buyerName", userId);
//		tmp.put("order.sellerName", userId);
//		tmp.put("orderId", "00000001");
//		String result = messageModelService.sendSmsMessge(MessageModelSign.BUYER_NEW_ORDER, "18259194308", tmp,userId);
//		System.out.println(result);
        return "/dsww/001/user-address";
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
		return addressService.getByDatatablesByUserId(userId, metadata,"0");
	}

	@Override
	protected CommonService<Address, ?> getService() {
		return addressService;
	}

	@Override
	protected Address doSaveVo(Address vo) {
		String code=vo.getDistrict();
		if(code == null){
			code = vo.getCity();
		}
		if(code == null){
			code=vo.getProvince();
		}
		String userId = getCurrUserId();
		vo.setUserId(userId);
		vo.setAreas(areasService.getAreaName(code));
		vo.setDistrict(code);
		vo.setType("0");
		vo.setIsDefault("0");
		return super.doSaveVo(vo);
	}
	
	@Override
	@RequestMapping("/v_get/{voId}")
	@RequestMappingDescription("获取记录")
	@ResponseBody
	public Address voGet(@PathVariable String voId) {
		// TODO Auto-generated method stub
		return super.voGet(voId);
	}

	@RequestMapping(value="vo_setIsDefault")
	@ResponseBody
	public boolean setIsRead(String addressId){
		String userId = getCurrUserId();
		addressService.setDefaultAddress(addressId, userId, "0");
		return true;
	}
	
	@RequestMapping(value="vo_saveAddress")
	@ResponseBody
	public List<Address> saveAddress(Address vo){
		String code=vo.getDistrict();
		vo.setAreas(vo.getProvince()+vo.getCity()+vo.getDistrict());
		if(vo.getDistrict().isEmpty()){
			code = vo.getCity();
		}
		if(code.isEmpty()){
			code=vo.getProvince();
		}
		String fullAreas = areasService.getAreaName(code);
		vo.setDistrict(code);
		vo.setAreas(fullAreas);
		vo.setType("0");
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			vo.setUserId(getCurrUserId());
			super.doSaveVo(vo);
		}else{
			String userId = getCurrUserId();
			List<Address> addressList = addressService.getAddressByUserId(userId,"0");
			if(addressList!=null&&addressList.size()>0){
				vo.setIsDefault("0");
			}else{
				vo.setIsDefault("1");
			}
			vo.setUserId(userId);
			super.doSaveVo(vo);
		}
		List<Address> address = addressService.getAddressByUserId(vo.getUserId(),"0");
		return address;
	}
	
	@RequestMapping(value="vo_getAddress")
	@ResponseBody
	public Address getAddress(String addressId){
		Address address = addressService.getOne(addressId);
		return address;
	}

	@RequestMapping(value="vo_getUserAddress")
	@ResponseBody
	public List<Address> getUserAddress(){
		String userId = getCurrUserId();
		List<Address> address = addressService.getAddressByUserId(userId,"0");
		return address;
	}
	
//	@RequestMapping(value="vo_delAddress")
//	@ResponseBody
//	public void delAddress(String addressId){
//		return super.voDelete(addressId);
//		
//	}
	
	@RequestMapping("/address_validate")
	@RequestMappingDescription("获取校验表单的json数据")
	@ResponseBody
	public String getAddressVJson() {
		List<FormFieldValidDefine> list = getVoValidation(Address.class);
		String str = new Gson().toJson(list);
		return str;
	}
}
