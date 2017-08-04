package com.rongji.cms.emall.service.activity.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.dao.activity.AuctionRecordDao;
import com.rongji.cms.emall.entity.EmlAuctionRecord;
import com.rongji.cms.emall.entity.EmlAuctionRecord_;
import com.rongji.cms.emall.service.activity.AuctionRecordService;
import com.rongji.cms.emall.service.activity.MyBondService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.vo.AuctionRecord;
import com.rongji.cms.emall.vo.MyBond;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;

@Service
public class AuctionRecordServiceImpl extends CommonServiceImpl<AuctionRecord, EmlAuctionRecord, AuctionRecordDao> implements AuctionRecordService {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private MyBondService myBondService;
	
	@Autowired
	private OrderService orderService;

	@Override
	protected EmlAuctionRecord createEntity() {
		return new EmlAuctionRecord();
	}
	
	@Override
	protected AuctionRecord createVo() {
		return new AuctionRecord();
	}
	
	@Override
	public AuctionRecord convertToVo(EmlAuctionRecord entity) {
		AuctionRecord vo=createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true,"goods","myBond");
		if(entity!=null){	
			vo.setGoods(storeService.getGoodsByGoodsId(entity.getGoodsId()));
			if(entity.getBondId()!=null){
				MyBond myBond=myBondService.getOne(entity.getBondId());
				myBond.setOrder(orderService.getOne(myBond.getOrder().getId()));
				vo.setMyBond(myBond);
			}
		}		
		return vo;
	}

	@Override
	public EmlAuctionRecord convertToEntity(AuctionRecord vo) {
		EmlAuctionRecord entity=createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true,"goods","myBond");
		if(vo.getGoods()!=null){
			entity.setGoodsId(vo.getGoods().getGoodsId());			
		}
		if(vo.getMyBond()!=null){
			entity.setBondId(vo.getMyBond().getId());
		}
		return entity;
	}

	@Override
	public AuctionRecord getAuctionRecord(final String userId, final String goodsId) {
		EmlAuctionRecord auctionRecord = getDao().findOne(new Specification<EmlAuctionRecord>() {
			@Override
			public Predicate toPredicate(Root<EmlAuctionRecord> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				Predicate p1 = cb.equal(root.get(EmlAuctionRecord_.goodsId), goodsId);
				Predicate p2 = cb.equal(root.get(EmlAuctionRecord_.userId), userId);
				
				return cb.and(p1, p2);
			}
		});
		return auctionRecord == null ? null : convertToVo(auctionRecord);	
	}

	@Override
	public List<AuctionRecord> getAuctionRecords(final String goodsId) {
		List<EmlAuctionRecord> auctionRecords = getDao().findAll(new Specification<EmlAuctionRecord>() {
			@Override
			public Predicate toPredicate(Root<EmlAuctionRecord> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get(EmlAuctionRecord_.goodsId), goodsId);
				return p1;
			}
		});
		
		return convertToVos(auctionRecords);
	}

}
