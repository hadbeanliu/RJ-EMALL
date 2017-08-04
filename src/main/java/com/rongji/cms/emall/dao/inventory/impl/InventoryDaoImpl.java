package com.rongji.cms.emall.dao.inventory.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.rongji.cms.emall.dao.inventory.InventoryDao;
import com.rongji.cms.emall.entity.EmlInventory;
import com.rongji.cms.emall.entity.EmlInventory_;
import com.rongji.rjskeleton.dao.CommonDaoImpl;
@Repository
public class InventoryDaoImpl extends CommonDaoImpl<EmlInventory> implements InventoryDao{

	

	@Override
	public EmlInventory getInventoryByskuId(final String skuId) {
		EmlInventory eml =  findOne(new Specification<EmlInventory>() {
			@Override
			public Predicate toPredicate(Root<EmlInventory> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {	
				
				query.where(cb.equal(root.get(EmlInventory_.skuId) ,skuId));
				return null;
			}
		});
		return eml;
	}

	@Override
	public long getsumByGoodsId(String goodsId) {
		long sum= 0;
		try {
			
	
			String jpql="select sum(a.inventory) from EmlInventory  a where a.goodsId=?0";
			List<Long> i = this.getQueryList(jpql, Long.class, goodsId);
			
			if(i!=null&&i.size()>0){
				sum=i.get(0);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

	@Override
	public List<EmlInventory> getInventoryByGoodsId(final String goodsId) {
		List<EmlInventory> eml =  findAll(new Specification<EmlInventory>() {
			@Override
			public Predicate toPredicate(Root<EmlInventory> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {	
				
				query.where(cb.equal(root.get(EmlInventory_.goodsId) ,goodsId));
				return null;
			}
		});
		return eml;
	
	}
	

}
