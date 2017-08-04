package com.rongji.cms.emall.service.refund.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rongji.cms.emall.dao.refund.RefundDao;
import com.rongji.cms.emall.entity.EmlOrder;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.entity.EmlRefund;
import com.rongji.cms.emall.entity.EmlRefund_;
import com.rongji.cms.emall.entity.EmlStore_;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.refund.RefundService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Refund;
import com.rongji.cms.emall.vo.ResultModel;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.support.exception.SkeletonException;
import com.rongji.rjskeleton.support.exception.SkeletonStatusCodes;
import com.rongji.rjskeleton.support.id.Sid;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;
@Service
public class RefundServiceImpl  extends CommonServiceImpl<Refund,EmlRefund,RefundDao>  implements RefundService{

	
private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Value("${rjskeleton.date.format}")
	private String dateFormat;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Override
	protected Refund createVo() {
		return new Refund();
	}

	@Override
	protected EmlRefund createEntity() {
	
		return new EmlRefund();
	}
	
	
	@Override
	public EmlRefund convertToEntity(Refund vo) {
		EmlRefund entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "order");
		EmlOrder order = orderService.convertToEntity(vo.getOrder());
		entity.setOrder(order);
		return entity;
	}
	@Override
	public Refund convertToVo(EmlRefund entity) {
		Refund vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "order");
		Order order = orderService.convertToVo(entity.getOrder());
		//vo.setUserId(user.getId());
		vo.setOrder(order);
		return vo;
	}

	@Override
	public PageEntity<Refund>  getUserfund(final String userId, final DatatablesMetadata metadata) {
		
		PageEntity < Refund> refunds= getByDatatables(metadata, new Specification<EmlRefund>() {
			@Override
			public Predicate toPredicate(Root<EmlRefund> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				
				// 多表查询方法 1
//				Join<EmlOrderGoods, EmlOrder> orderJoin = root.join(EmlOrderGoods_.order);
//				Predicate orderEq = cb.equal(orderJoin.get(EmlOrder_.orderNo), orderNo);
				
				// 多表查询方法 2
				Predicate orderEq = cb.equal(root.get(EmlRefund_.order).get(EmlOrder_.createUser), userId);
				
				// 对应的sql语句
				/*
				select emlordergo0_ as col_0_0_
				  from eml_order_goods emlordergo0_
				 inner join eml_order emlorder1_
				    on emlordergo0_.order_id = emlorder1_.id
				 where emlorder1_.order_no = ?
				 */
				
				
				Predicate[] where = createDatatablesQuery(metadata, root, query, cb);
				if (where != null && where.length > 0) {
					Predicate[] w = new Predicate[where.length+1];
					w[0] = orderEq;
					for (int i = 0; i < where.length; i++) {
						w[i+1] = where[i];
					}
					query.where(w);
					
				} else {
					query.where(orderEq);
				}
				
				return null;
			}
		});
		List<Refund> list = refunds.getData();
		List<Refund> newlist =new ArrayList<Refund>();
		for (Refund refund : list) {
			refund.setOrder(	orderService.getOrder(refund.getOrder().getId()));
			newlist.add(refund);
		}
		refunds.setData(newlist);
		
		return refunds;
		
		//return null;
		
		
	}



	@Override
	protected Predicate[] createDatatablesQuery(DatatablesMetadata metadata,
			Root<EmlRefund> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		PageInfo pageInfo = metadata.getPageInfo();
		ManagedType<?> managedType = pageInfo != null ? pageInfo.getManagedType() : null;
		
		if (managedType == null) {
			return null;
		}
		
		List<String[]> searchs = metadata.getSearchs();
		Predicate[] ws =null;
		if(searchs!=null){
		ws = new Predicate[searchs.size()];
		
		int i = 0;
		for (String[] search : searchs) {
			
			String name = search[0];
			Attribute<?, ?> attr = null;
			String value = search[1];
			if("userId".equals(name)||"storeId".equals(name)||"orderNo".equals(name)){
				if("userId".equals(name)){
					ws[i++] = 	cb.equal(root.get(EmlRefund_.order).get(EmlOrder_.createUser), value);
				}else if("storeId".equals(name)){
					ws[i++] = 	cb.equal(root.get(EmlRefund_.order).get(EmlOrder_.store).get(EmlStore_.storeId), value);
				}else if("orderNo".equals(name)){
					ws[i++] = 	cb.equal(root.get(EmlRefund_.order).get(EmlOrder_.orderNo), value);
				}
				
				continue;
			}else{
				try {
					attr = managedType.getAttribute(name);
				} catch (IllegalArgumentException e) {
					LOGGER.error("非法搜索字段：" + name + " in " + managedType.getJavaType(), e);
				}
				if (attr == null) {
					continue;
				}
			}
			
			
			Class<?> javaType = attr.getJavaType();
			
			if (String.class.equals(javaType)) {
				
					ws[i] = cb.like(root.<String>get(name), "%"+value+"%");
				
				
			} else if (Integer.class.equals(javaType)) {
				ws[i] = cb.equal(root.<Integer>get(name), Integer.valueOf(value));
				
			} else if (Float.class.equals(javaType)) {
				ws[i] = cb.equal(root.get(name), Float.valueOf(value));
				
			} else if (Date.class.equals(javaType)) {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				Date date = null;
				try {
					date = sdf.parse(value);
				} catch (ParseException e) {
					throw new SkeletonException(SkeletonStatusCodes.DATE_FORMAT_ERROR, e);
				}
				ws[i] = cb.equal(root.get(name), date);
				
			} else if (Sid.class.isAssignableFrom(javaType)) { // 一对一关联支持
				Sid en = (Sid) BeanUtils.instantiate(javaType);
				en.setId(value);
				ws[i] = cb.equal(root.get(name), en);
				
			} else {
				continue;
			}
			
			i++;
		}
		}
		return ws;
	}
	
	
	@Override
	public PageEntity<Refund> getStorefund(final String storeId,
			final DatatablesMetadata metadata) {
		
		PageEntity < Refund> refunds= getByDatatables(metadata, new Specification<EmlRefund>() {
			@Override
			public Predicate toPredicate(Root<EmlRefund> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				
				// 多表查询方法 1
//				Join<EmlOrderGoods, EmlOrder> orderJoin = root.join(EmlOrderGoods_.order);
//				Predicate orderEq = cb.equal(orderJoin.get(EmlOrder_.orderNo), orderNo);
				
				// 多表查询方法 2
				Predicate orderEq = cb.equal(root.get(EmlRefund_.order).get(EmlOrder_.store).get(EmlStore_.storeId), storeId);
				
				// 对应的sql语句
				/*
				select emlordergo0_ as col_0_0_
				  from eml_order_goods emlordergo0_
				 inner join eml_order emlorder1_
				    on emlordergo0_.order_id = emlorder1_.id
				 where emlorder1_.order_no = ?
				 */
				
				
				Predicate[] where = createDatatablesQuery(metadata, root, query, cb);
				if (where != null && where.length > 0) {
					Predicate[] w = new Predicate[where.length+1];
					w[0] = orderEq;
					for (int i = 0; i < where.length; i++) {
						w[i+1] = where[i];
					}
					query.where(w);
					
				} else {
					query.where(orderEq);
				}
				
				return null;
			}
		});
		List<Refund> list = refunds.getData();
		List<Refund> newlist =new ArrayList<Refund>();
		for (Refund refund : list) {
			refund.setOrder(	orderService.getOrder(refund.getOrder().getId()));
			newlist.add(refund);
		}
		refunds.setData(newlist);
		return refunds;
	}

	@Override
	public List<Refund> getfundByOrderId(String orderId) {
		List<EmlRefund> list=	getDao().getfundByOrderId(orderId);
		if(list!=null){
		List<Refund> volist=null;
		if(list!=null||list.size()>0){
			volist=new ArrayList<Refund>();
			for (EmlRefund refund : list) { 
				volist.add(convertToVo(refund));
			}
		}
		return volist;
		}
		return null;
	}
	@Transactional
	@Override
	public Refund sellRefund(String id, String type, String handlingSuggestion, String refundSum) {
		Refund refund = getOne(id);
		if("0".equals(refund.getStatus())){
		if("1".equals(type)){
			
			if(Double.parseDouble(refundSum)>refund.getOrder().getPrice()){
				return null;
			}
		
			refund.setRefundSum(Double.parseDouble(refundSum));
		}else if("2".equals(type)){
			refund.setStatus(type);
			refund.setHandlingSuggestion(handlingSuggestion);
		Order order = orderService.getOrder(refund.getOrder().getId());
		order.setStatus(refund.getOrderStatus());
		orderService.save(order);
			save(refund);
		}
		}
		return refund;
	}
	@Transactional
	@Override
	public ResultModel addrefund(String orderId, String refundReason,String refundStatus) {
		Order order = orderService.getOrder(orderId);
		ResultModel result=new ResultModel();
		if(order!=null){
		List<Refund> list =getfundByOrderId(orderId);
		boolean ishas = true;
	
		if(list!=null){
			for (Refund refund : list) {
				if("0".equals(refund.getStatus())){
					ishas=false;
					break;
				}
			}
		}
		if(list==null||list.size()==0){
				
		Refund refund=new Refund();
		refund.setOrder(order);
		refund.setStatus("0");
		refund.setRefundStatus(refundStatus);
		refund.setBuyerRefundReason(refundReason);
		refund.setOrderStatus(order.getStatus());
		order.setStatus("6");
		
		orderService.save(order);
		save(refund);
		result.setRatcode("0");
		
		result.setResult(new Gson().toJson(refund));
		
		//return"{\"ratcode\":\"0\",\"result\":\"/refund/user/"+getUriVar("userId")+"\"}";
		}else{
			result.setRatcode("2");
			result.setResult("该订单退款已存在，如果有不满意请走投诉流程");
			
			
		}
		}else{
			result.setRatcode("1");
			result.setResult("无此订单");
			
		}
		return result;
	}
	@Transactional
	@Override
	public Refund changeStatus(String refundId, String status) {
		Refund refund = getOne(refundId);
		if(refund!=null){
		refund.setStatus(status);

		return save(refund);}
		return null;
	}
	@Transactional
	@Override
	public Refund refundHandlingService(String refundId, String status,
			String handlingSuggestion) {
		Refund refund = getOne(refundId);
		
		refund.setStatus(status);
		refund.setHandlingSuggestion(handlingSuggestion);
		return save(refund);
	}
	@Transactional
	@Override
	public void refundSuccess(String refundId,double num) {
		try {
			Refund refund = getOne(refundId);
			refund.setStatus("1");
			refund.setRefundSum(num);
			save(refund);
			
		} catch (Exception e) {
			
		}
		
		
	}
	@Transactional
	@Override
	public void refundFailure(String refundId) {
		try {
			Refund refund = getOne(refundId);
			refund.setStatus("2");
			save(refund);
			
		} catch (Exception e) {
			
		}
		
	}

	

}
