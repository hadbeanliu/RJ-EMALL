package com.rongji.cms.emall.web.freight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.entity.EmlFreightTemplate_;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.freight.CarryModeService;
import com.rongji.cms.emall.service.freight.FreightTemplateService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.CarryMode;
import com.rongji.cms.emall.vo.FreightTemplate;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

@Controller
@RequestMapping("/seller/freight")
public class FreightTemplateController extends CrudAndPageByJsonController<FreightTemplate> {

	private static PageInfo PAGE_INFO = new PageInfo();
	static{
		PAGE_INFO.setLength(5);
		PAGE_INFO.setCheckboxMode(true);
		PAGE_INFO.addColumn(EmlFreightTemplate_.id, true, true,true);
	}
	
	@Autowired
	private FreightTemplateService freightTemplateService;
	
	@Autowired
	private CarryModeService carryModeService;

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	
	@Override
	protected CommonService<FreightTemplate, ?> getService(){
		return freightTemplateService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return PAGE_INFO;
	}

	/**
	 * 入口页面。地址：/freight.htm
	 */
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("模版查看界面")
	public String index(Model model) {
		String storeId=storeService.getStoreByUserId(getCurrUserId()).getId();
		List<FreightTemplate> freights=freightTemplateService.getFreightTemps(storeId);
		Map<String, Map<String, Integer>> count=new HashMap<String, Map<String, Integer>>();
		Map<String, Integer> map=new HashMap<String, Integer>();
		int i;
		for(FreightTemplate ft : freights){
			i=0;
			for(CarryMode cm : ft.getCarryMode()){
				if("0".equals(cm.getIsDefault())){
					i++;
				}
			}
			map.put("0", i);
			map.put("1", ft.getCarryMode().size()-i);
			count.put(ft.getId(), map);
		}
		model.addAttribute("freights", freights);
		model.addAttribute("count", count);
		return "/dsww/001/seller-center-freight-tpl"; // 跳转到/freight.htm
	}

	@Override
	protected FreightTemplate doSaveVo(FreightTemplate vo) {
		String sellerId = getCurrUserId();
		Store store = storeService.getStoreByUserId(sellerId);
		vo.setStore(store);
		return freightTemplateService.addFreightTemplate(vo);
	}
	
	@Override
	@RequestMapping("/v_get/{voId}")
	@RequestMappingDescription("获取记录")
	@ResponseBody
	public FreightTemplate voGet(@PathVariable String voId) {
		FreightTemplate ft=freightTemplateService.getFreightTemp(voId);
		return ft;
	}
	
	@Override
	protected FreightTemplate doUpdateVo(FreightTemplate vo,
			MultiValueMap<String, Object> data) {
		if(vo.getId() == null){
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		String sellerId = getCurrUserId();
		Store store = storeService.getStoreByUserId(sellerId);
		vo.setStore(store);
		return freightTemplateService.updateFreightTemplate(vo);
	}
//	
//	@RequestMapping("/{tempId}")
//	public String carrys(@PathVariable String tempId,Model model){
//		FreightTemplate freightTemplate = freightTemplateService.getOne(tempId);
//		if(freightTemplate == null){
//			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
//		}
//		model.addAttribute("tempId",tempId);
//		return "freight/carrys";
//	}
	
	@RequestMapping("/addfreight")
	@RequestMappingDescription("添加运费模版")
	public String addfreight(Model model) {
		model.addAttribute("flag", 1);//用于判断是添加（1）还是修改（0）
		return "/dsww/001/seller-center-new-freight";
	}
	
	@RequestMapping("/edit/{voId}")
	@RequestMappingDescription("修改运费模版")
	public String editfreight(@PathVariable String voId,Model model) {
		FreightTemplate ft=freightTemplateService.getFreightTemp(voId);
		model.addAttribute("freight",ft);
		String url="/seller/freight/v_get/"+voId+".json";
		model.addAttribute("urljson",url);
		model.addAttribute("flag", 0);//用于判断是添加（1）还是修改（0）
		return "/dsww/001/seller-center-new-freight";
	}
	
	@RequestMapping("/addsave")
	@RequestMappingDescription("添加保存")
	public String addsave(FreightTemplate vo) {
		String sellerId = getCurrUserId();
		Store store = storeService.getStoreByUserId(sellerId);
		vo.setStore(store);
		freightTemplateService.addFreightTemplate(vo);
		return "/dsww/001/seller-center-freight-tpl"; 
	}
	
	@RequestMapping("/updatesave")
	@RequestMappingDescription("更新保存")
	public String updatesave(FreightTemplate vo) {
		if(vo.getId() == null){
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		String sellerId = getCurrUserId();
		Store store = storeService.getStoreByUserId(sellerId);
		vo.setStore(store);
		freightTemplateService.updateFreightTemplate(vo);
		return "/dsww/001/seller-center-freight-tpl"; 
	}
	
	@RequestMapping("/carrys")
	@RequestMappingDescription("获取运费方式")
	public List<CarryMode> carrys(AddressandStore vo){
		return freightTemplateService.getCarryMode(vo.getStoreId(), vo.getAddressId());
	}
	
	@RequestMapping("/carryfee")
	public CarryFee carryfee(CarryFee vo){
		Order order = orderService.getPreOrder(vo.getPreOrderId());
//		if(order==null){
//			vo.setPreOrderId(null);
//			return vo;
//		}
		int fee=freightTemplateService.calculateFee(order, vo.getCarryId());
//		int fee=freightTemplateService.getFreightCarryFee(Integer.parseInt(vo.getPice()), vo.getCarryId());
		vo.setFee(String.valueOf(fee));
		vo.setPrice(order.getPriceString());
		return vo;
	}
}
