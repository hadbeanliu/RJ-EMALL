package com.rongji.cms.emall.service.shopping.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.shopping.MyBrowseDao;
import com.rongji.cms.emall.entity.EmlMyBrowse;
import com.rongji.cms.emall.service.shopping.MyBrowseService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.MyBrowse;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class MyBrowseImpl extends CommonServiceImpl<MyBrowse, EmlMyBrowse, MyBrowseDao> implements MyBrowseService {
	
	@Autowired
	private StoreService storeService;
	
	@Override
	protected EmlMyBrowse createEntity() {
		return new EmlMyBrowse();
	}

	@Override
	protected MyBrowse createVo() {
		return new MyBrowse();
	}

//	@Override
//	public EmlMyBrowse convertToEntity(MyBrowse vo) {
//		EmlMyBrowse entity=createEntity();
//		ConvertUtil.copyPropertiesByIgnore(vo, entity, true,"goods");
//		if(vo.getGoods()!=null){
//			entity.setGoodsId(vo.getGoods().getGoodsId());
//			entity.setGoodsCategory(storeService.getGoodsByGoodsId(entity.getGoodsId()).getGoodsCatalog());//商品分类
//		}				
//		return entity;
//	}

//	@Override
//	public MyBrowse convertToVo(EmlMyBrowse entity) {
//		MyBrowse vo=createVo();
//		ConvertUtil.copyPropertiesByIgnore(entity,vo, true,"goods");
//		if(entity.getGoodsId()!=null){
//			vo.setGoods(storeService.getGoodsByGoodsId(entity.getGoodsId()));
//			vo.setGoodsCategory(vo.getGoods().getGoodsCatalog());//商品分类
//		}
//		return vo;
//	}
	
	

	@Override
	public List<MyBrowse> getMyBrowseByUserId(String userId) {
		List<EmlMyBrowse> browses = getDao().getMyBrowseByUserId(userId);
		return convertToVos(browses);
	}

	@Override
	public List<EmlMyBrowse> getMyBrowse(String goodsId, String userId) {
		return getDao().getMyBrowse(goodsId, userId);
	}
	
}
