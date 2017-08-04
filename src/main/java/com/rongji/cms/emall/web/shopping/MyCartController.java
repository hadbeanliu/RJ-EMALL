package com.rongji.cms.emall.web.shopping;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongji.cms.emall.entity.EmlMyCart_;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.support.mvc.RequestMappingDescription;
import com.rongji.rjskeleton.web.controller.CrudAndPageByJsonController;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.cms.emall.service.shopping.MyCartService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.MyCart;

@Controller
@RequestMapping("/buyer/mycart")
public class MyCartController extends CrudAndPageByJsonController<MyCart> {

	
	@RequestMapping(method=RequestMethod.GET)
	@RequestMappingDescription("我的购物车")
	public String index(Model model) {
	   return "/dsww/002/buyer-mycart";	
	}
	
	/**
	 * 设置datatables显示的列表
	 */
	private static PageInfo PAGE_INFO = new PageInfo();
	static {		
		PAGE_INFO.setIdColumn(EmlMyCart_.id);
	}
	
	@Autowired
	private MyCartService myCartService;
	
	@Autowired 
	private StoreService storeService;

	@Override
	protected CommonService<MyCart, ?> getService() {
		return myCartService;
	}
	
	@Override
	protected PageInfo getPageInfo() {
		return null;
	}
    
    @RequestMapping(value="getShopIds")
	@ResponseBody
	@RequestMappingDescription("获取添加到购物车的店铺ID")
	public List<String> getShopIds() throws Exception {
    	String userId = getCurrUserId();
    	List<MyCart> myCartList=myCartService.getShopIdsByUserId(userId);
    	//添加测试数据    	
    	List<String> shopIdList=new ArrayList<String>();
    	for (MyCart myCart : myCartList) {
    		String shopId=myCart.getStore().getStoreId(); 
    		if(shopIdList.indexOf(shopId)==-1){
    			shopIdList.add(shopId);
    		} 
		}
		return  shopIdList;
	}

	@Override
	protected MyCart doSaveVo(MyCart vo) {			
		String userId = getCurrUserId();
		MyCart myCart=myCartService.getMyCart(vo.getGoods().getGoodsId(),userId,vo.getSkuId());		
	    vo.setUserId(userId);
		if(myCart!=null){	
			int buyNumber=myCart.getBuyNumber()+vo.getBuyNumber();			
			vo=myCart;
			vo.setBuyNumber(buyNumber);
		}
		return super.doSaveVo(vo);
	}
	
	@RequestMapping("/update")
	@RequestMappingDescription("更新我的购物车记录")
	@ResponseBody
	public String voUpdate(@RequestParam String cartId,@RequestParam int num) {		
		MyCart myCart=myCartService.getOne(cartId);
		int goodsNum=storeService.getGoodsByGoodsId(myCart.getGoods().getGoodsId()).getGoodsNum();
		if(goodsNum>=num){
			myCart.setBuyNumber(num);
			super.doSaveVo(myCart);
			return "{\"Code\":\"200\",\"Msg\":\"更新成功\"}";
		}else{
			return "{\"Code\":\"404\",\"Msg\":\"更新失败\",\"maxNum\":"+goodsNum+"}";
		}			
	}
	
	@RequestMapping(value="/save")
	@RequestMappingDescription("添加我的购物车记录")
	public String voSave(@RequestParam int num,@RequestParam String goodsId,@RequestParam String skuId) {	
		MyCart myCart=myCartService.getMyCart(goodsId,getCurrUserId(),skuId);
		//是否已添加
		if(myCart!=null){
			int buyNumber=myCart.getBuyNumber()+num;	
			myCart.setBuyNumber(buyNumber);
		}else{
			myCart=new MyCart();
			myCart.setBuyNumber(Integer.valueOf(num));		
			myCart.setStore(storeService.getStoreByGoodsId(goodsId));			
			myCart.setGoods(storeService.getGoodsByGoodsId(goodsId));
			myCart.setSkuId(skuId);	
		}
		myCart.setUserId(getCurrUserId());
		super.doSaveVo(myCart);
		return redirectTo("/buyer/mycart");
	}
}
