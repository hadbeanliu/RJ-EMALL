package com.rongji.cms.emall.web.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rongji.cms.emall.entity.EmlStore_;
import com.rongji.cms.emall.service.company.CompanyService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.support.valid.FormFieldValidDefine;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/seller/store")
public class SellerStoreController extends CrudAndPageByJsonController<Store> {
	@Autowired
	private StoreService storeService;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Override
	protected CommonService<Store, ?> getService() {
		return storeService;
	}

	@Override
	protected PageInfo getPageInfo() {
		PageInfo pageInfo = new PageInfo();

		pageInfo.setLength(4);
		pageInfo.setCheckboxMode(true);
		pageInfo.setIdColumn(EmlStore_.storeId);
		// pageInfo.addColumn(EmlStore_.storeId, false, false, false, false);
		pageInfo.addColumn(EmlStore_.storeName, true, false, true, true);
		pageInfo.addColumn(EmlStore_.description, true, false, true, true);
		pageInfo.addColumn(EmlStore_.logo, true, false, true, true);
		pageInfo.addColumn(EmlStore_.status, true, false, true, true);
		// pageInfo.addColumn(EmlStore_.status, true, false, true, true);
		// pageInfo.addColumn(EmlStore_.status, true, false, true, true);

		return pageInfo;
	}


	@RequestMapping(method = RequestMethod.GET)
	@RequestMappingDescription("商铺后台界面")
	public void index(Model model) {

	}

	@RequestMapping(value = "/save-store", method = RequestMethod.POST)
	public String saveStore(Store vo) {

		vo.setUser(userService.getUserById(getCurrUserId()));
		Store store = storeService.getStoreByUserId(getCurrUserId());
		if (store == null) {
			storeService.saveStore(vo);
			return redirectTo("/seller/store/storeinfo");
		} else {
			return null;
		}

	}
	@RequestMapping("/getStoreBygoodsId")
	public Store getStoreBygoodsId() {
		
		return storeService.getStoreByGoodsId(getRequest().getParameter("goodsId"));
		
	}
	@RequestMapping("/store_validate")
	@RequestMappingDescription("获取验证表单的json数据")
	@ResponseBody
	public String getExamineVJson() {

		List<FormFieldValidDefine> list = getVoValidation(getValidClasses());
		String str = new Gson().toJson(list);
		return str;
	}

	@RequestMapping("/add")
	public String toAddStore(Store vo) {
		String id = getCurrUserId();
	boolean isPass = companyService.isPassForCompany(id);
		if(isPass){
		Store temp = storeService.getStoreByUserId(id);
		if (temp == null) {
			return "/dsww/001/seller-center-addstore";
		} else {
			return redirectTo("/seller/store/storeinfo");
		}
		}else{
			return redirectTo("/seller/authentication/company");
		}
	}

	@RequestMapping(value = "/update-store", method = RequestMethod.POST)
	public String updateStore(Store vo) {
		
		storeService.updateStore(vo);
		String id = getCurrUserId();
		Store temp = storeService.getStoreByUserId(id);
		Gson gson = new Gson();
		getRequest().setAttribute("vo", gson.toJson(temp));
		return redirectTo("/seller/store/storeinfo");

	}

	@RequestMapping("/storeinfo")
	public String toStore(Store vo) {
		String id = getCurrUserId();
		Store temp = storeService.getStoreByUserId(id);
		if (temp != null) {
			if ("0".equals(temp.getStatus())) {

				getRequest().setAttribute("ret", "1");
				return "/dsww/001/seller-store-wait";

			} else if("1".equals(temp.getStatus())){
				Gson gson = new Gson();
				getRequest().setAttribute("vo", gson.toJson(temp));

				return "/dsww/001/seller-center-editstore";
			}else{
				
				getRequest().setAttribute("ret", "2");
				getRequest().setAttribute("status", temp.getStatus());
				return "/dsww/001/seller-store-wait";
			}
		} else {
			getRequest().setAttribute("ret", "0");
			return "/dsww/001/seller-store-wait";
		}
	}
	@RequestMapping("/failure")
	public String failure(Store vo) {
		String id = getCurrUserId();
		Store temp = storeService.getStoreByUserId(id);
		if (temp != null) {
			if ("0".equals(temp.getStatus())) {

				getRequest().setAttribute("ret", "1");
				return "/dsww/001/seller-store-wait";

			} else if("1".equals(temp.getStatus())){
				Gson gson = new Gson();
				getRequest().setAttribute("vo", gson.toJson(temp));

				return "/dsww/001/seller-center-editstore";
			}else{
				
				Gson gson = new Gson();
				getRequest().setAttribute("vo", gson.toJson(temp));
				return "/dsww/001/seller-center-editstore";
			}
		} else {
			getRequest().setAttribute("ret", "0");
			return "/dsww/001/seller-store-wait";
		}
	}
	@Override
	protected Store doUpdateVo(Store vo, MultiValueMap<String, Object> data) {

		return storeService.updateStore(vo);
	}

	/*
	 * @RequestMapping(method=RequestMethod.GET)
	 * 
	 * @RequestMappingDescription("商铺添加界面")
	 */
	/*
	 * public void add(Model model) { model.addAttribute("store", model); }
	 * 
	 * /* @RequestMapping(value="/index5",method) public String index2(Model
	 * model) { // Pageable pageable = new PageRequest(0, pageSize); //
	 * Page<Store> page=storeService.getAll(pageable); //
	 * model.addAttribute("storeList", page.getContent()); //
	 * model.addAttribute("page", page); return "forword:index6"; }
	 */

}
