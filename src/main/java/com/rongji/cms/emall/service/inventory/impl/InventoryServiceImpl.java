package com.rongji.cms.emall.service.inventory.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.inventory.InventoryDao;
import com.rongji.cms.emall.entity.EmlInventory;
import com.rongji.cms.emall.service.inventory.InventoryService;
import com.rongji.cms.emall.vo.Inventory;
import com.rongji.cms.webservice.client.json.EMallClient;
import com.rongji.cms.webservice.client.json.impl.EMallClientImpl;
import com.rongji.cms.webservice.domain.emall.WsGoods;
import com.rongji.cms.webservice.domain.emall.WsStandRelationData;
import com.rongji.cms.webservice.domain.emall.WsStandard;
import com.rongji.cms.webservice.domain.emall.WsStandardData;
import com.rongji.dfish.cache.meta.HashMeta;
import com.rongji.rjskeleton.service.CommonServiceImpl;
@Service
public class InventoryServiceImpl  extends CommonServiceImpl<Inventory,EmlInventory,InventoryDao>  implements InventoryService{
	private     EMallClient client=new EMallClientImpl("http://192.168.14.202:9080/cms/"); 
	@Override
	public Inventory getInventoryByskuId(String skuId) {
		EmlInventory eml=	getDao().getInventoryByskuId(skuId);
		if(eml!=null){
	    Inventory vo = convertToVo(eml);
		return vo;
		}
		return null;
	}

	@Override
	public Inventory addGoodsInventory(String json) {
		HashMeta lm=new HashMeta("kucd."+"shopId:");
		lm.set("ff", "555");
		//lm.h
		return null;
	}
	@Transactional
	@Override
	public Inventory sellInventory(String skuId, int num) {
		Inventory vo = 	getInventoryByskuId(skuId);
		if(vo!=null){
		if(num>vo.getInventory()&&vo.getInventory()>0){
			return null;
		}else{
			num=vo.getInventory()-num;
			vo.setInventory(num);
			HashMeta map=new HashMeta("kucd."+vo.getGoodsId());
			map.set("kucd."+skuId, num+"");
			save(vo);
		}
		return vo;
		}
		return null;
	}
	@Transactional
	@Override
	public Inventory addInventory(String skuId, int num) {
		Inventory vo = 	getInventoryByskuId(skuId);
		if(vo!=null){
		num=vo.getInventory()+num;
		
		save(vo);
		HashMeta map=new HashMeta("kucd."+vo.getGoodsId());
		map.set("kucd."+skuId, num+"");
		}
		return vo;
	}

	@Override
	public long sumInventory(String goodsId) {
		long sum=0;
		try {
	/*	HashMeta map=new HashMeta("kucd."+goodsId);
		List<String> list = map.hvals();
		for (String string : list) {
			sum=sum+Long.parseLong(string);
		}*/
			sum=getDao().getsumByGoodsId(goodsId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	@Override
	public Inventory getOneInventory(String skuId,String goodsId) {
		EmlInventory inventory=null;
		
		
		/*HashMeta map=new HashMeta("kucd."+goodsId);
		Inventory =Long.parseLong(map.get("kucd."+inventorySearchId));*/
			inventory =	getDao().getInventoryByskuId(skuId);
//			Inventory vo=;
			if(inventory!=null){
			return convertToVo(inventory);
			}
		
		if(inventory==null){
			WsGoods goods = client.getWsGoodsById(goodsId);
			String key = skuId.replace(goodsId+"_", "S_bind_");
			WsStandRelationData temp = goods.getWsStandardData().getRelationData(key);
			Inventory vo=new Inventory();
			vo.setGoodsId(goodsId);
			vo.setcName(getNameByskuId(goodsId, skuId));
			vo.setSkuId(skuId);
			vo.setInventory(temp.getCount());
			
			 DecimalFormat df = new DecimalFormat("#.00");  

			 
			 
			vo.setGoodsPrice(String.format("%.2f", temp.getPrice()));
			return vo;
		}
		return null;
	}

	@Override
	public List<Inventory> getInventoryByGoodsId(String goodsId) {
	
		List<EmlInventory> eml = getDao().getInventoryByGoodsId(goodsId);
		List<Inventory> vo=new ArrayList<Inventory>();
		if(eml!=null&&eml.size()>0){
			for (EmlInventory inventory : eml) {
				vo.add(convertToVo(inventory));
			}
		}
		return vo;
	}
	
	
	@Override
	public  String getNameByskuId(String goodsId,String skuId){
		 String cname="";
		 skuId= skuId.replace(goodsId+"_", "");
		 WsStandardData vo = client.getStandardDataByGoodsId(goodsId);
		 List<WsStandard>  selectedStandards= vo.getSelectedStandards();
		 String[] inventorySearchIds = skuId.split("_");
		 for (int i = 0; i < selectedStandards.size(); i++) {
			 WsStandard selectedStandard = selectedStandards.get(i);
			 cname=cname+selectedStandard.getTitle()+":";
			 for (Map<String, String> item : selectedStandard.getItemList()) {
				 if(item.get("id").equals(inventorySearchIds[i])){
					 cname=cname+item.get("title")+" ";
					 break;
					 
				 }
				
			}
			
		}
		return cname;
		
	}
	
public static void main(String[] args) {
	 
	String inventorySearchId="2015061215000001_1400000053_1500000149_1500000175_1500000144";
	String goodsId="2015061215000001";
	System.out.println(new InventoryServiceImpl().getNameByskuId(goodsId, inventorySearchId));
	 
	
}
}
