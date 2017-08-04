package com.rongji.cms.emall.service.shopping.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongji.cms.emall.dao.shopping.MyCartDao;
import com.rongji.cms.emall.entity.EmlMyCart;
import com.rongji.cms.emall.entity.EmlMyCart_;
import com.rongji.cms.emall.service.inventory.InventoryService;
import com.rongji.cms.emall.service.shopping.MyCartService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.Goods;
import com.rongji.cms.emall.vo.Inventory;
import com.rongji.cms.emall.vo.MyCart;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;


@Service
public class MyCartImpl extends CommonServiceImpl<MyCart, EmlMyCart, MyCartDao> implements MyCartService {

	@Autowired
	private StoreService storeService;
	
	@Autowired 
	private InventoryService inventoryService;
	
	@Override
	protected EmlMyCart createEntity() {
		return new EmlMyCart();
	}

	@Override
	protected MyCart createVo() {
		return new MyCart();
	}

	@Override
	@Transactional
	public <S extends MyCart> S save(S vo) {
		if (vo != null && vo.getId() == null) {
			vo.setCreateTime(new Date());
		}		
		return super.save(vo);
	}

	@Override
	public List<MyCart> getShopIdsByUserId(String userId) {
		return convertToVos(getDao().getMyCartsByUserId(userId));
	}

	@Override
	public MyCart convertToVo(EmlMyCart entity) {
		MyCart vo=createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true,"goods","store");
		if(entity!=null){			
			vo.setStore(storeService.getOne(entity.getStoreId())); 
			Inventory sku = inventoryService.getOneInventory(entity.getSkuId(), entity.getGoodsId());			
			vo.setGoods(storeService.getGoodsByGoodsId(entity.getGoodsId()));
			vo.setSkuInfo(inventoryService.getNameByskuId(entity.getGoodsId(),entity.getSkuId()));						
			vo.setSku(sku);
		}
		return vo;
	}

	@Override
	public EmlMyCart convertToEntity(MyCart vo) {
		EmlMyCart entity=createEntity();
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
	public MyCart getMyCart(final String goodsId,final String userId,final String skuId) {
		EmlMyCart myCart = getDao().findOne(new Specification<EmlMyCart>() {
			@Override
			public Predicate toPredicate(Root<EmlMyCart> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				Predicate p1 = cb.equal(root.get(EmlMyCart_.goodsId), goodsId);
				Predicate p2 = cb.equal(root.get(EmlMyCart_.userId), userId);
				Predicate p3 = cb.equal(root.get(EmlMyCart_.skuId), skuId);
				return cb.and(p1, p2, p3);
			}
		});
		return myCart == null ? null : convertToVo(myCart);
//		return convertToVo(getDao().getMyCartByGoodsId(goodsId,userId,inventorySearchId));
	}

	@Override
	public PageEntity<MyCart> getByDatatables(final String userId,final DatatablesMetadata metadata) {
		return getByDatatables(metadata, new Specification<EmlMyCart>() {
			@Override
			public Predicate toPredicate(Root<EmlMyCart> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			
				Predicate p1 = cb.equal(root.get(EmlMyCart_.userId), userId);			
				
				Predicate[] where = createDatatablesQuery(metadata, root, query, cb);
				if (where != null && where.length > 0) {
					Predicate[] w = new Predicate[where.length+1];
					w[0] = p1;			
					for (int i = 0; i < where.length; i++) {
						w[i+1] = where[i];
					}
					query.where(w);
					
				} else {
					query.where(p1);
				}
				
				return null;
			}
	  });
	}

	@Override
	@Transactional
	public void deleteOrUpdateMyCart(String goodsId, String userId,String skuId,int num) {
		MyCart myCart=getMyCart(goodsId,userId,skuId);	
		int surplus=myCart.getBuyNumber()-num;//剩余库存	
		if(surplus>0){
			myCart.setBuyNumber(surplus);
			super.save(myCart);		
		}else{
			super.delete(myCart);
		}	
	}

	@Override
	@Transactional
	public void deleteOrUpdateMyCartByCartId(String cartId, int num) {
		MyCart myCart=getOne(cartId);
		int surplus=myCart.getBuyNumber()-num;//剩余库存	
		if(surplus>0){
			myCart.setBuyNumber(surplus);
			super.save(myCart);		
		}else{
			super.delete(myCart);
		}			
	}	
}
