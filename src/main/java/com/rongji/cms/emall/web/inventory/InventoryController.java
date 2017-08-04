package com.rongji.cms.emall.web.inventory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongji.cms.emall.service.inventory.InventoryService;
import com.rongji.cms.emall.vo.Inventory;
import com.rongji.rjskeleton.service.BaseService;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;

@Controller
@RequestMapping("/inventory")
public class InventoryController extends CrudAndPageByJsonController<Inventory>{
	@Autowired
	private InventoryService inventoryService;
	
	@Override
	protected PageInfo getPageInfo() {
	
		return null;
	}

	@Override
	protected BaseService<Inventory, String> getService() {	
		return inventoryService;
	}
	@RequestMapping("/sum")
	public void getSum( Model model) {		
		String goodsId = getRequest().getParameter("goodsId");
		Map<String, String> map=new HashMap<String, String>();
		long sum = inventoryService.sumInventory(goodsId);
		map.put("sum", sum+"");	
		model.addAllAttributes(map);
	}
	@RequestMapping("/inventoryNum")
	public void getInventoryNum( Model model) {		
		String skuId = getRequest().getParameter("skuId");	
		String goodsId = getRequest().getParameter("goodsId");	
		Inventory vo = inventoryService.getOneInventory(skuId, goodsId);
		Map<String, Inventory> map=new HashMap<String, Inventory>();
		map.put("inventory", vo);	
		model.addAllAttributes(map);	
	}
/*	@RequestMapping("/getgoods")
	public void	getgoodsById( Model model){
		Map<String, WsGoods> map=new HashMap<String, WsGoods>();
		String goodsId = getRequest().getParameter("goodsId");	
		map.put("goods", inventoryService.getGoodsByGoodsId(goodsId));	
		model.addAllAttributes(map);	
		
	}*/
	@RequestMapping("/add")
	public void add( Model model) {		
		String json = getRequest().getParameter("json");
		inventoryService.addGoodsInventory(json);
	}
	
}
