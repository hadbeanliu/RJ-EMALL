package com.rongji.cms.emall.service.shopping.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.shopping.MyAttentionDao;
import com.rongji.cms.emall.entity.EmlMyAttention;
import com.rongji.cms.emall.service.shopping.MyAttentionService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.MyAttention;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class MyAttentionImpl extends CommonServiceImpl<MyAttention, EmlMyAttention, MyAttentionDao> implements MyAttentionService {

	@Autowired
	private StoreService storeService;
	
	@Override
	protected EmlMyAttention createEntity() {
		return new EmlMyAttention();
	}

	@Override
	protected MyAttention createVo() {
		return new MyAttention();
	}

//	@Override
//	@Transactional
//	public <S extends MyAttention> S save(S vo) {
//		if (vo != null && vo.getId() == null) {
//			vo.setCreateTime(new Date());
//		}
//		return super.save(vo);
//	}

	@Override
	public EmlMyAttention convertToEntity(MyAttention vo) {
		EmlMyAttention entity=createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true,"goods","store");
		if(vo.getGoods()!=null){
			entity.setGoodsId(vo.getGoods().getGoodsId());			
		}
		if(vo.getStore()!=null){
			entity.setStoreId(vo.getStore().getStoreId());
		}
		return entity;
	}

	@Override
	public MyAttention convertToVo(EmlMyAttention entity) {
		MyAttention vo=createVo();		
		ConvertUtil.copyPropertiesByIgnore(entity,vo, true,"goods","store");
		if(entity.getStoreId()!=null){
			vo.setStore(storeService.getOne(entity.getStoreId()));
		}
		if(entity.getGoodsId()!=null){
			vo.setGoods(storeService.getGoodsByGoodsId(entity.getGoodsId()));
		}	
		return vo;
	}

	@Override
	public EmlMyAttention getMyAttentionByGoodsId(String userId, String goodsId) {
		return getDao().getMyAttentionByGoodsId(userId, goodsId);
	}

	@Override
	public EmlMyAttention getMyAttentionByStoreId(String userId, String storeId) {
		return getDao().getMyAttentionByStoreId(userId, storeId);
	}
 
}
