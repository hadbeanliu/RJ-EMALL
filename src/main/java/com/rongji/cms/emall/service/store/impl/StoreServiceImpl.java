package com.rongji.cms.emall.service.store.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.rongji.cms.emall.dao.store.StoreDao;
import com.rongji.cms.emall.entity.EmlStore;
import com.rongji.cms.emall.entity.EmlUser;
import com.rongji.cms.emall.service.store.StoreService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.vo.Goods;
import com.rongji.cms.emall.vo.Store;
import com.rongji.cms.emall.vo.User;
import com.rongji.cms.webservice.client.json.EMallClient;
import com.rongji.cms.webservice.client.json.impl.EMallClientImpl;
import com.rongji.cms.webservice.domain.emall.WsGoods;
import com.rongji.cms.webservice.domain.emall.WsShop;
import com.rongji.dfish.base.Utils;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.support.db.query.QueryOper;
import com.rongji.rjskeleton.support.db.query.QueryUtil;
import com.rongji.rjskeleton.support.exception.SkeletonException;
import com.rongji.rjskeleton.support.exception.SkeletonStatusCodes;
import com.rongji.rjskeleton.web.controller.datatables.PageInfo;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;
//import com.rongji.cms.webservice.domain.emall.WsGoods;
//import com.rongji.cms.webservice.domain.emall.WsShop;

@Service
public class StoreServiceImpl extends
		CommonServiceImpl<Store, EmlStore, StoreDao> implements StoreService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CommonServiceImpl.class);
	private EMallClient client = new EMallClientImpl(
			"http://192.168.14.202:9080/cms/");
	@Value("${rjskeleton.date.format}")
	private String dateFormat;

	@Autowired
	private UserService userService;

	@Override
	protected EmlStore createEntity() {

		return new EmlStore();
	}

	@Override
	public Store convertToVo(EmlStore entity) {
		Store vo = createVo();

		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "user");
		User user = userService.convertToVo(entity.getUser());
		vo.setUser(user);
		return vo;

	}

	@Override
	protected Predicate[] createDatatablesQuery(DatatablesMetadata metadata,
			Root<EmlStore> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		PageInfo pageInfo = metadata.getPageInfo();
		ManagedType<?> managedType = pageInfo != null ? pageInfo
				.getManagedType() : null;

		if (managedType == null) {
			return null;
		}

		List<String[]> searchs = metadata.getSearchs();
		if (searchs == null) {
			return null;
		}
		List<String[]> ns = new ArrayList<String[]>(searchs.size() + 1);
		for (String[] search : searchs) {
			String name = search[0];
			if ("status".equals(name)) {
				String value = search[2];
				if ("all".equals(value)) {
					search[1] = QueryOper.NOT_EQUAL;

				} else if ("1".equals(value) || "0".equals(value)) {

				} else {
					search[1] = QueryOper.NOT_EQUAL;
					search[2] = "1";
					ns.add(new String[] { name, QueryOper.NOT_EQUAL, "0" });
				}
			}
			ns.add(search);
		}

		Predicate[] ws = QueryUtil.createSearches(ns, root, cb);

		return ws;
	}

	@Override
	public EmlStore convertToEntity(Store vo) {
		EmlStore entity = createEntity();

		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "user");
		EmlUser user = userService.convertToEntity(vo.getUser());
		entity.setUser(user);
		return entity;
	}

	@Override
	protected Store createVo() {

		return new Store();
	}

	@Override
	public Store getStoreByUserId(String userId) {
		EmlStore emlStore = getDao().getStoreByUserId(userId);
		if (emlStore != null) {

			return convertToVo(emlStore);
		}
		return null;
	}

	@Transactional
	@Override
	public Store saveStore(Store vo) {

		if (vo == null) {

			throw new SkeletonException(SkeletonStatusCodes.NULL_ERROR);

		}
		String[] rusult = null;
		String userId = vo.getUser().getId();
		client.setOperUserId(userId);
		User user = userService.getUserById(userId);
		vo.setUser(user);
		/*if (vo.getCmsSiteId() == null) {
			if ("1".equals(vo.getStatus())) {
				WsShop shop = new WsShop();
				shop.setShopName(vo.getStoreName());
				shop.setShopAlias(vo.getStoreName());
				shop.setShopAlias(Utils.getUID());
				shop.setCreateUser(userId);

				shop.setMainSiteId("140005");
				rusult = client.addOrUpdateShop(shop);
				if ("0".equals(rusult[0])) {
					vo.setCmsSiteId(rusult[2]);
					;
				}

			}
		} else {
			WsShop shop = new WsShop();
			shop.setShopName(vo.getStoreName());
			shop.setShopId(vo.getCmsSiteId());
			rusult = client.addOrUpdateShop(shop);
		}*/

		vo.setStoreId(null);

		return save(vo);
	}

	@Override
	@Transactional
	public Store updateStore(Store vo) {

		Store store = getOne(vo.getId());

		/* vo.setUserId(user.getId()); */
		String[] rusult = null;
		if (store.getCmsSiteId() == null) {
			if ("1".equals(vo.getStatus())) {
				WsShop shop = new WsShop();
				shop.setShopName(vo.getStoreName());
				shop.setShopAlias(vo.getStoreName());
				shop.setShopAlias(Utils.getUID());
				shop.setCreateUser(store.getUser().getId());

				shop.setMainSiteId("140005");
				rusult = client.addOrUpdateShop(shop);
				if ("0".equals(rusult[0])) {
					vo.setCmsSiteId(rusult[2]);
				}
			}
		}
		if (vo != null) {
			vo.setUser(store.getUser());
			if(store.getCmsSiteId()!=null){
			vo.setCmsSiteId(store.getCmsSiteId());
			// store.setStoreDescription(vo.getStoreDescription());
			}
		}
		return update(vo);
	}

	@Override
	@Transactional
	public void delete(String id) {
		Store vo = getOne(id);

		String[] rs = client.delShop(vo.getCmsSiteId());

		super.delete(id);

	}

	@Override
	public Goods getSimpleGoodsByGoodsId(String goodsId) {
		WsGoods goods = client.getWsGoodsById(goodsId);
		if (goods != null) {
			Gson gosn = new Gson();
			goods.setGoodsDesc("");
			// System.out.println(gosn.toJson(goods));
			Goods g = new Goods();
			ConvertUtil.copyPropertiesByIgnore(goods, g, true);
			return g;
		}
		return null;
	}

	@Override
	public Goods getGoodsByGoodsId(String goodsId) {
		WsGoods goods = client.getWsGoodsById(goodsId);

		Gson gosn = new Gson();
		// goods.setGoodsDesc("");
		// System.out.println(gosn.toJson(goods));
		if (goods != null) {
			Goods g = new Goods();
			ConvertUtil.copyPropertiesByIgnore(goods, g, true);
			return g;
		}
		return null;
	}

	@Override
	public Store getStoreByGoodsId(String goodsId) {
		WsShop cmsite = client.getShopByGoodsId(goodsId);

		EmlStore store = getDao().getStoreByCmsId(cmsite.getShopId());
		if (store != null) {
			return convertToVo(store);
		}
		return null;
	}

	public static void main(String[] args) {
		new StoreServiceImpl().getStoreByGoodsId("2015070116000003");
	}

}
