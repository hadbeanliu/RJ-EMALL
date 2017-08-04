package com.rongji.cms.emall.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.rongji.cms.emall.dao.order.OrderDao;
import com.rongji.cms.emall.entity.EmlAddress;
import com.rongji.cms.emall.entity.EmlLogistics;
import com.rongji.cms.emall.entity.EmlOrder;
import com.rongji.cms.emall.entity.EmlOrderGoods;
import com.rongji.cms.emall.entity.EmlOrderGoods_;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.cms.emall.entity.EmlStore;
import com.rongji.cms.emall.entity.EmlStore_;
import com.rongji.cms.emall.service.address.AddressService;
import com.rongji.cms.emall.service.freight.FreightTemplateService;
import com.rongji.cms.emall.service.inventory.InventoryService;
import com.rongji.cms.emall.service.logistic.LogisticService;
import com.rongji.cms.emall.service.message.MessageModelService;
import com.rongji.cms.emall.service.message.MessageModelSign;
import com.rongji.cms.emall.service.order.OrderGoodsService;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.service.order.OrderStatus;
import com.rongji.cms.emall.service.refund.RefundService;
import com.rongji.cms.emall.service.shopping.MyCartService;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Address;
import com.rongji.cms.emall.vo.Goods;
import com.rongji.cms.emall.vo.Inventory;
import com.rongji.cms.emall.vo.Logistics;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.cms.emall.vo.Refund;
import com.rongji.cms.emall.vo.Store;
import com.rongji.cms.emall.vo.User;
import com.rongji.dfish.cache.meta.StringMeta;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.support.db.query.QueryUtil;
import com.rongji.rjskeleton.support.id.LocalNewIdGenerator;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class OrderServiceImpl extends CommonServiceImpl<Order, EmlOrder, OrderDao> implements OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private String redisPre = "emall_pre_order:";

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private OrderGoodsService orderGoodsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private LogisticService logisticService;
	
	@Autowired
	private FreightTemplateService freightTemplateService;
	
	@Autowired
	private MyCartService myCartService;
	
	@Autowired
	private MessageModelService messageModelService;
	
	@Autowired
	private RefundService refundService;
	
	@Autowired
	private LocalNewIdGenerator idGenerator;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
	
	@Override
	protected Order createVo() {
		return new Order();
	}

	@Override
	protected EmlOrder createEntity() {
		return new EmlOrder();
	}
	
	@Override
	public Order convertToVo(EmlOrder entity) {
		
		if (entity == null) {
			return null;
		}
		
		Order vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "store", "payServiceProvider", "logistics", "address");
		
		Store store = storeService.convertToVo(entity.getStore());
		vo.setStore(store);
		
		Address address = addressService.convertToVo(entity.getAddress());
		vo.setAddress(address);
		
		User user = userService.getUserById(entity.getCreateUser());
		vo.setUser(user);
		
		Logistics logistics = logisticService.convertToVo(entity.getLogistics());
		vo.setLogistics(logistics);
		
		return vo;
	}

	@Override
	public EmlOrder convertToEntity(Order vo) {
		
		if (vo == null) {
			return null;
		}
		
		EmlOrder entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "store", "payServiceProvider", "logistics", "address");
		
		EmlStore store = storeService.convertToEntity(vo.getStore());
		entity.setStore(store);
		
		EmlAddress address = addressService.convertToEntity(vo.getAddress());
		entity.setAddress(address);
		
		EmlLogistics logistics = logisticService.convertToEntity(vo.getLogistics());
		entity.setLogistics(logistics);
		
		return entity;
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		orderGoodsService.deleteByOrderId(id);
		getDao().delete(id);
	}

	@Override
	public Order addOrder(String userId, List<OrderGoods> goods) {
		
		Order order = new Order();
		
		Store store = null;
		for (OrderGoods orderGoods : goods) {
			Store ns = storeService.getStoreByGoodsId(orderGoods.getGoodsId());
			if (ns == null) {
				logger.error("订单中商品[{}]不存在", orderGoods.getGoodsId());
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
			}
			
			if (store == null) {
				store = ns;
			} else if ( ! store.getId().equals(ns.getId()) ) {
				logger.error("订单中存在不同商家[{}]、[{}]", store.getId(), ns.getId());
				throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
			}
		}
		order.setStore(store);

		return addOrder(userId, order);
		
//		EmlOrder order = new EmlOrder();
//		order.setCreateTime(new Date());
//		order.setCreateUser(userId);
//		
//		order.setPrice(10L); // 测试数据
//		EmlStore store = new EmlStore();
//		store.setId("00000002");
//		order.setStore(store);
//		
//		Order od = convertToVo(getDao().save(order));
//		
//		for (OrderGoods orderGoods : goods) {
//			orderGoods.setOrder(od);
//			orderGoods.setCreateTime(new Date());
//		}
//		
//		orderGoodsService.save(goods);
//		
//		return od;
	}

	
	@Transactional
	@Override
	public Order addOrder(String userId, Order order) {
		
		if (order.getId() != null) {
			logger.error("新建订单不能存在ID[{}]", order.getId());
			throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_INFO);
		}
		
		order.setDealTime(new Date());
		order.setCreateUser(userId);
		order.setStatus(OrderStatus.NOT_PAID);
		
		// 设置运费
		int fee = freightTemplateService.calculateFee(order, order.getCarryId());
		order.setLogisticsFee(Long.valueOf(fee));
		
		Order od = save(order);
		
		List<OrderGoods> goods = order.getGoods();
		for (OrderGoods orderGoods : goods) {
			orderGoods.setOrder(od);
			orderGoods.setCreateTime(new Date());
			
			// 减库存
			inventoryService.sellInventory(orderGoods.getSkuId(), orderGoods.getPurchaseQuantity());
			
			// 减购物车
			if (order.isInCart()) {
				myCartService.deleteOrUpdateMyCart(orderGoods.getGoodsId(), order.getCreateUser(), orderGoods.getSkuId(), orderGoods.getPurchaseQuantity());
			}
		}
		orderGoodsService.save(goods);
		
		// 设置订单编号
		String[] orderN = idGenerator.parseId(od.getId());
		StringBuilder no = new StringBuilder();
		no.append(sdf.format(new Date(Long.valueOf(orderN[0]))));
		no.append(orderN[1].replace(".", ""));
		no.append(orderN[2]);
		od.setOrderNo(no.toString());
		
		updateByFilter(od, "orderNo");
		
		// 发送消息
		Map<String, String> data = new HashMap<String, String>();
		data.put("order.sellerName", order.getStore().getUser().getNickName());
		data.put("order.orderNo", od.getOrderNo());
		messageModelService.sendMessge(MessageModelSign.BUYER_NEW_ORDER, order.getStore().getUser().getId(), data, order.getCreateUser());
		
		return od;
	}

	@Override
	public Order getOrder(String orderId) {
		
//		Order order = getOne(orderId);
//		if (order != null) {
//			fillOrder(order);
//			return order;
//		}
//		
//		return null;
		return fillOrder( getOne(orderId) );
	}

	@Override
	public Order getOrderByNo(final String orderNo) {
		return fillOrder( convertToVo( getDao().findOne(new Specification<EmlOrder>() {
			@Override
			public Predicate toPredicate(Root<EmlOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(EmlOrder_.orderNo), orderNo);
			}
		}) ) );
	}
	
	@Override
	public Order getOrderByTradeNo(final String tradeNo) {
		return fillOrder( convertToVo( getDao().findOne(new Specification<EmlOrder>() {
			@Override
			public Predicate toPredicate(Root<EmlOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(EmlOrder_.tradeNo), tradeNo);
			}
		}) ) );
	}

//	@Override
//	public String showPay(String userId, String orderNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String doPay(String userId, String orderNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Transactional
	@Override
	public void confirmGoods(String userId, String orderId) {
		EmlOrder order = getDao().findOne(orderId);
		if (order != null) {
			if (OrderStatus.SEND.equals(order.getStatus())) {
				order.setStatus(OrderStatus.SUCCESS);
				order.setDoneTime(new Date());
				getDao().save(order);
				
				// 发送消息
				Map<String, String> data = new HashMap<String, String>();
				data.put("order.sellerName", order.getStore().getUser().getNickName());
				data.put("order.orderNo", order.getOrderNo());
				User user = userService.getUserById(order.getCreateUser());
				data.put("order.buyerName", user.getNickName());
				messageModelService.sendMessge(MessageModelSign.BUYER_ORDER_CONFIRM, order.getStore().getUser().getId(), data, order.getCreateUser());

			}
		}
	}

	@Transactional
	@Override
	public void sendGoods(String userId, String orderId, Logistics logistics) {
		EmlOrder order = getDao().findOne(orderId);
		if (order != null) {
			if (OrderStatus.PAID.equals(order.getStatus())) {
				order.setStatus(OrderStatus.SEND);
				order.setLogistics(logisticService.convertToEntity(logistics));
				getDao().save(order);
				
				// 发送消息
				Map<String, String> data = new HashMap<String, String>();
				data.put("order.storeName", order.getStore().getStoreName());
				data.put("order.orderNo", order.getOrderNo());
				User user = userService.getUserById(order.getCreateUser());
				data.put("order.buyerName", user.getNickName());
				messageModelService.sellerSendMessge(MessageModelSign.SELLER_ORDER_SEND, order.getCreateUser(), data, order.getStore().getUser().getId());
			}
		}
	}

	@Transactional
	@Override
	public void editPrice(String userId, String orderId, long cutPrice, long logisticsFee) {
		EmlOrder order = getDao().findOne(orderId);
		if (order != null) {
			order.setPrice(order.getPrice() - cutPrice);
			order.setLogisticsFee(logisticsFee);
			getDao().save(order);
		}
	}

	@Override
	public void doComment(String userId, String orderNo, String commentId,
			Map<String, String> goodsCommentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageEntity<Order> getByDatatablesByUser(final String userId,
			final DatatablesMetadata metadata) {
		
		if (metadata.getSort() == null) {
			metadata.setSort( new Sort(new Sort.Order(Direction.DESC, EmlOrder_.createTime.getName())) );
		}
		
		return fillOrderGoods( getByDatatables(metadata, new Specification<EmlOrder>() {
			@Override
			public Predicate toPredicate(Root<EmlOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate userEq = cb.equal(root.get(EmlOrder_.createUser), userId);
//				Predicate[] where = QueryUtil.createSearchesAndAddPredicate(metadata.getSearchs(), root, cb, userEq);
//				if (where != null && where.length > 0) {
//					query.where(where);
//				}
				
				doOrderSearch(root, query, cb, metadata.getSearchs(), userEq);
				
//				Subquery<EmlOrder> subQuery = query.subquery(EmlOrder.class);
//				Root<EmlOrderGoods> fromOg = subQuery.from(EmlOrderGoods.class);
//				subQuery.select(fromOg.get(EmlOrderGoods_.order));
//				subQuery.where(cb.and(cb.equal(fromOg.get(EmlOrderGoods_.order), root), cb.like(fromOg.get(EmlOrderGoods_.goodsTitle), "%Nexus%")));
//
//				query.where(userEq, cb.in(root).value(subQuery));
				
				return null;
			}
		}) );
	}

	@Override
	public PageEntity<Order> getByDatatablesByStore(final String storeId,
			final DatatablesMetadata metadata) {
		
		if (metadata.getSort() == null) {
			metadata.setSort( new Sort(new Sort.Order(Direction.DESC, EmlOrder_.createTime.getName())) );
		}
		
		return fillOrderGoods( getByDatatables(metadata, new Specification<EmlOrder>() {
			@Override
			public Predicate toPredicate(Root<EmlOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate userEq = cb.equal(root.get(EmlOrder_.store).get(EmlStore_.storeId), storeId);
//				Predicate[] where = QueryUtil.createSearchesAndAddPredicate(metadata.getSearchs(), root, cb, userEq);
//				if (where != null && where.length > 0) {
//					query.where(where);
//				}
				
				doOrderSearch(root, query, cb, metadata.getSearchs(), userEq);
				
				return null;
			}
		}) );
	}
	
	@Override
	public String savePreOrder(Order order) {
		String preOrderId = idGenerator.generate();
		order.setPreOrderId(preOrderId);
		
		StringMeta pre = new StringMeta(redisPre + preOrderId);
		pre.setex(15 * 60, new Gson().toJson(order));
		
		return preOrderId;
	}

	@Override
	public Order getPreOrder(String preOrderId) {
		StringMeta pre = new StringMeta(redisPre + preOrderId);
		String v = pre.get();
		if (v == null || v.isEmpty()) {
			return null;
		}
		return new Gson().fromJson(v, Order.class);
	}

	@Override
	public List<Order> createPreOrders(String userId, OrderGoods... ogds) {
		
		User user = userService.getUserById(userId);
		if (user == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_USER_NOT_FOUND);
		}
		
		if (ogds != null && ogds.length > 0) {
			Map<Store, List<OrderGoods>> ors = new HashMap<Store, List<OrderGoods>>();
			for (OrderGoods orderGoods : ogds) {
				if (orderGoods.getGoodsId() == null || orderGoods.getGoodsId().trim().isEmpty() ) {
					logger.error("订单中商品Id不能为空");
					throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
				}
				Store store = storeService.getStoreByGoodsId(orderGoods.getGoodsId());
				if (store == null) {
					logger.error("订单中商店[{}]不存在", orderGoods.getGoodsId());
					throw new EmallException(EmallStatusCode.ERROR_DATA_STORE_NOT_FOUND);
				}
				
				if (orderGoods.getSkuId() == null || orderGoods.getSkuId().trim().isEmpty() ) {
					logger.error("订单中商品[{}]的型号[skuId]不能为空", orderGoods.getGoodsId());
					throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
				}
				
				Inventory sku = inventoryService.getOneInventory(orderGoods.getSkuId(), orderGoods.getGoodsId());
				if (sku == null) {
					logger.error("订单中商品[{}]的型号[{}]不存在", orderGoods.getGoodsId(), orderGoods.getSkuId());
					throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
				}
				
				if (orderGoods.getPurchaseQuantity() <= 0) {
					logger.error("订单中商品[{}]型号[{}]的购买数量不能为空", orderGoods.getGoodsId(), orderGoods.getSkuId());
					throw new EmallException(EmallStatusCode.ERROR_DATA_ORDER_GOODS_INFO);
				}
				
				Goods goods = storeService.getSimpleGoodsByGoodsId(orderGoods.getGoodsId());
				orderGoods.setGoodsImage(goods.getGoodsFaceThumb());
//				orderGoods.setGoodsPrice(Double.valueOf(goods.getGoodsPrice()).longValue() * 100);
				orderGoods.setGoodsPrice(Double.valueOf(Double.valueOf(sku.getGoodsPrice()) * 100).longValue()); // 商品以元为单位，需要转换为项目中的分
				
				orderGoods.setPurchasePrice(orderGoods.getGoodsPrice()); // FIXME 计算优惠
				
				orderGoods.setGoodsTitle(goods.getGoodsTitle());
				orderGoods.setGoodsUrl("http://service.rongji.com/devcms/ar/" + orderGoods.getGoodsId()); // FIXME 地址需修改
				
				orderGoods.setSku(inventoryService.getNameByskuId(orderGoods.getGoodsId(), orderGoods.getSkuId()));
				
				List<OrderGoods> gds = ors.get(store);
				if (gds == null) {
					gds = new ArrayList<OrderGoods>();
					ors.put(store, gds);
				}
				gds.add(orderGoods);
				
			}
			
			List<Order> orders = new ArrayList<Order>(ors.size());
			for (Map.Entry<Store, List<OrderGoods>> en : ors.entrySet()) {
				
				if (en.getValue() == null || en.getValue().isEmpty()) {
					continue;
				}
				
				Order order = new Order();
				order.setGoods(en.getValue());
				order.setStore(en.getKey());
				order.setConfirmUser(userId);
				order.setCreateTime(new Date());
				
				Long price = 0L;
				for (OrderGoods ods : en.getValue()) {
					price += ods.getPurchasePrice() * ods.getPurchaseQuantity();
				}
				order.setPrice(price);
				
				orders.add(order);
			}
			
			return orders;
		}
		return Collections.emptyList();
	}

	@Transactional
	@Override
	public void refundSuccess(String refundId, long momey) {
		Refund refund = refundService.getOne(refundId);
		if (refund != null) {
			EmlOrder order = getDao().findOne(refund.getOrder().getId());
			if (order != null && OrderStatus.REFUNDING.equals(order.getStatus())) {
//				refundService.changeStatus(refundId, "2"); // 退款成功
				refundService.refundSuccess(refundId, momey);
				order.setStatus(OrderStatus.DROP);
				getDao().save(order);
				
				// 发送消息
				Map<String, String> data = new HashMap<String, String>();
				data.put("order.storeName", order.getStore().getStoreName());
				data.put("order.orderNo", order.getOrderNo());
				User user = userService.getUserById(order.getCreateUser());
				data.put("order.buyerName", user.getNickName());
				messageModelService.sellerSendMessge(MessageModelSign.SELLER_AGREE_REFUND, order.getCreateUser(), data, order.getStore().getUser().getId());

			}
		}
	}
	
	private void doOrderSearch(Root<EmlOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<String[]> searchs, Predicate... others) {
		
		if (searchs != null && !searchs.isEmpty()) {
			List<Integer> idx = new ArrayList<Integer>();
			int i = 0;
			List<Predicate> p = new ArrayList<Predicate>();
			for (String[] search : searchs) {
				if ("goods.goodsTitle".equals(search[0])) {
					
					
					Subquery<EmlOrder> subQuery = query.subquery(EmlOrder.class);
					Root<EmlOrderGoods> fromOg = subQuery.from(EmlOrderGoods.class);
					
					search[0] = "goodsTitle";
					Predicate tp = QueryUtil.createSearch(search, fromOg, cb);
					if (tp != null) {
						subQuery.select(fromOg.get(EmlOrderGoods_.order));
						subQuery.where(cb.and(cb.equal(fromOg.get(EmlOrderGoods_.order), root), tp));
						
						p.add( cb.in(root).value(subQuery) );
						idx.add(i++);
					}
					
				}
			}
			
			if ( !idx.isEmpty() ) {
				for (int integer : idx) {
					searchs.remove(integer);
				}
				
				Predicate[] ps = new Predicate[i + (others != null ? others.length : 0)];
				if (others != null && others.length > 0) {
					System.arraycopy(others, 0, ps, 0, others.length);
					i = others.length;
				} else {
					i = 0;
				}
				for (Predicate predicate : p) {
					ps[i++] = predicate;
				}
				others = ps;
			}
		}
		
		Predicate[] where = QueryUtil.createSearchesAndAddPredicate(searchs, root, cb, others);
		if (where != null && where.length > 0) {
			query.where(where);
		}
	}

	private PageEntity<Order> fillOrderGoods(PageEntity<Order> page) {
		if (page != null) {
			fillOrderGoods(page.getData());
		}
		return page;
	}
	private Order fillOrder(Order order) {
		if (order != null) {
			List<OrderGoods> gds = orderGoodsService.getGoodsByOrder(order.getId());
			if (gds != null && ! gds.isEmpty()) {
				order.setGoods( gds );
				for (OrderGoods goods : gds) {
					goods.setSku( inventoryService.getNameByskuId(goods.getGoodsId(), goods.getSkuId()) );
				}
			}
			if (OrderStatus.DROP.equals(order.getStatus()) || OrderStatus.REFUNDING.equals(order.getStatus())) {
				List<Refund> refund = refundService.getfundByOrderId(order.getId());
				if (refund != null && !refund.isEmpty()) {
					order.setRefund(refund.get(0));
				}
			}
			order.setStatusDesc(OrderStatus.getDesc(order.getStatus()));
		}
		return order;
	}
	private void fillOrderGoods(List<Order> orders) {
		if (orders != null) {
			for (Order or : orders) {
				fillOrder(or);
			}
		}
	}

}
