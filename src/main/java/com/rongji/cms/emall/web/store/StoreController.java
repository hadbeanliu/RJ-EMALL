package com.rongji.cms.emall.web.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongji.cms.emall.entity.EmlStore_;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Store;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/admin/store")
public class StoreController extends CrudAndPageByJsonController<Store> {
	@Autowired
	private StoreService storeService;
	@Autowired
	private UserService userService;

	@Override
	protected CommonService<Store, ?> getService() {
		return storeService;
	}

	@Override
	protected PageInfo getPageInfo() {
		PageInfo pageInfo = new PageInfo();

		pageInfo.setLength(10);
		pageInfo.setCheckboxMode(true);
		pageInfo.setIdColumn(EmlStore_.storeId);
		// pageInfo.addColumn(EmlStore_.storeId, false, false, false, false);
		pageInfo.addColumn(EmlStore_.storeName, true, false, true, true);
		pageInfo.addColumn(EmlStore_.description, true, false, true, true);
		pageInfo.addColumn(EmlStore_.logo, true, false, true, true);
		pageInfo.addColumn(EmlStore_.status, true, false, true, true);
		//pageInfo.addColumn(EmlStore_.creatTime, true, false, true, true);
		// pageInfo.addColumn(EmlStore_.status, true, false, true, true);
		// pageInfo.addColumn(EmlStore_.status, true, false, true, true);

		return pageInfo;
	}

	private int pageSize = 5;

	@RequestMapping(method = RequestMethod.GET)
	@RequestMappingDescription("商铺后台界面")
	public String index(Model model) {
		// Store s = storeService.getStoreByGoodsId("2015070116000003");
		return "/dsww/001/admin-store";

	}

	@Override
	protected Store doSaveVo(Store vo) {

		return storeService.saveStore(vo);
	}

	@Override
	protected Store doUpdateVo(Store vo, MultiValueMap<String, Object> data) {
		// TODO Auto-generated method stub
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
