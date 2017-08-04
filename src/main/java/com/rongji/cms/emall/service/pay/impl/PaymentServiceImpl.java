package com.rongji.cms.emall.service.pay.impl;

import java.util.ArrayList;
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

import com.rongji.cms.emall.dao.pay.PaymentDao;
import com.rongji.cms.emall.dao.store.StoreDao;
import com.rongji.cms.emall.entity.EmlPayment;
import com.rongji.cms.emall.entity.EmlPayment_;
import com.rongji.cms.emall.entity.EmlStore;
import com.rongji.cms.emall.entity.EmlStore_;
import com.rongji.cms.emall.service.pay.PayServiceProviderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.pay.provider.PayProvider;
import com.rongji.cms.emall.service.pay.provider.wxpay.WeixinPayProvider;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;
import com.rongji.rjskeleton.service.CommonServiceImpl;
import com.rongji.rjskeleton.support.convert.ConvertUtil;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class PaymentServiceImpl extends CommonServiceImpl<Payment, EmlPayment, PaymentDao> implements PaymentService {
	
//	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
	
	@Autowired
	private PayServiceProviderService payServiceProviderService;
	
//	@Autowired
//	private StoreService storeService;
	
	@Autowired
	private StoreDao storeDao;
	
	 // 用于加密存储商家的支付配置信息
//	private String configSignKey = "WmYpIcdSpx1cQDFtZvhSM1NGTY59dYbsYHbRk13Ia032p1JhAwBhceGDs6HR32Ea1BowV";
	
	@Override
	public Payment convertToVo(EmlPayment entity) {
		Payment vo = createVo();
		ConvertUtil.copyPropertiesByIgnore(entity, vo, true, "store", "payServiceProvider");
//		vo.setPayServiceProvider(payServiceProviderService.getByCode(entity.getPayServiceProviderCode()));
		return vo;
	}

	@Override
	public EmlPayment convertToEntity(Payment vo) {
		EmlPayment entity = createEntity();
		ConvertUtil.copyPropertiesByIgnore(vo, entity, true, "store", "payServiceProvider");
		return entity;
	}
	
	@Override
	public PageEntity<Payment> getByDatatablesByStore(final String storeId,
			DatatablesMetadata metadata) {
		return getByDatatables(metadata, new Specification<EmlPayment>() {
			@Override
			public Predicate toPredicate(Root<EmlPayment> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				return cb.equal(root.get(EmlPayment_.store).get(EmlStore_.storeId), storeId);
			}
		});
	}

	@Override
	public List<Payment> getPaymentsByStore(final String storeId) {
//		return getAll();
		List<EmlPayment> payments = getDao().findAll(new Specification<EmlPayment>() {
			@Override
			public Predicate toPredicate(Root<EmlPayment> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
//				EmlStore store = new EmlStore();
//				store.setId(storeId);
//				Predicate p1 = cb.equal(root.get(EmlPayment_.store), store);
				Predicate p1 = cb.equal(root.get(EmlPayment_.store).get(EmlStore_.storeId), storeId);
				return p1;
			}
		});
		
		return convertToVos(payments);
	}
	
	@Override
	public List<Payment> getWebPaymentsByStore(String storeId) {
		List<Payment> payments = getPaymentsByStore(storeId);
		if (payments != null && ! payments.isEmpty()) {
			List<Payment> reslut = new ArrayList<Payment>(payments.size());
			for (Payment p : payments) {
				PayProvider payProvider = payServiceProviderService.getProvider(p.getPayServiceProviderCode());
				if (payProvider != null && ! payProvider.isMobile() ) {
					reslut.add(p);
					p.setConfig(null);
				}
			}
			return reslut;
		}
		return payments;
	}

	@Override
	public List<Payment> getMobilePaymentsByStore(String storeId) {
		List<Payment> payments = getPaymentsByStore(storeId);
		if (payments != null && ! payments.isEmpty()) {
			List<Payment> reslut = new ArrayList<Payment>(payments.size());
			for (Payment p : payments) {
				PayProvider payProvider = payServiceProviderService.getProvider(p.getPayServiceProviderCode());
				if (payProvider != null) {
					if (payProvider.isMobile() 
							|| WeixinPayProvider.class.isAssignableFrom(payProvider.getClass())) {
						reslut.add(p);
						p.setConfig(null);
					}
				}
			}
			return reslut;
		}
		return payments;
	}
	
//	@Override
//	public Payment getPaymentByStoreAndPayProvider(final String storeId,
//			final PayProvider payProvider) {
//		if (storeId != null && payProvider != null) {
//			EmlPayment payment = getDao().findOne(new Specification<EmlPayment>() {
//				@Override
//				public Predicate toPredicate(Root<EmlPayment> root, CriteriaQuery<?> query,
//						CriteriaBuilder cb) {
//					Predicate p1 = cb.equal(root.get(EmlPayment_.store).get(EmlStore_.storeId), storeId);
//					Predicate p2 = cb.equal(root.get(EmlPayment_.payServiceProviderCode), payProvider.getServiceName());
//					return cb.and(p1, p2);
//				}
//			});
//			
//			return payment != null ? convertToVo(payment) : null;
//		}
//		return null;
//	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public <S extends Payment> S updateByFilter(S vo, String... attrs) {
//		Payment ovo = getOne(vo.getId());
		EmlPayment entity = getDao().findOne(vo.getId());
		if (entity == null || entity.getPayServiceProviderCode() == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
		}
		
//		if ( ! ovo.getPayServiceProviderCode().equals(vo.getPayServiceProviderCode()) ) {
//			throw new EmallException(EmallStatusCode.ERROR_DATA_NOT_CHANGE);
//		}
		
//		ConvertUtil.copyPropertiesByIgnore(vo, ovo, true, "store");
		
		PayProvider provider = payServiceProviderService.getProvider(entity.getPayServiceProviderCode());
		if (provider == null) {
			throw new EmallException(EmallStatusCode.ERROR_PAY_PROVIDER_NOT_FOUND);
		}
//		ovo.setPayServiceProviderCode(provider.getServiceName());
//		ovo.setPayServiceProviderDesc(provider.getDescription());
//		ovo.setPayServiceProviderTitle(provider.getTitle());
		
//		vo.setConfig(entity.getConfig());
		entity.setConfig( provider.convertConfig(vo) );
		
		entity.setModifyTime(new Date());
		
		return (S) convertToVo(getDao().save(entity));
	}

	@Override
	@Transactional
	public Payment createPaymentByPayCode(String userId, Payment payment) {
		
		EmlPayment entity = new EmlPayment();

		EmlStore store = storeDao.getStoreByUserId(userId);
		if (store == null) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_STORE_NOT_FOUND);
		}
		entity.setStore(store);
		
		PayProvider provider = payServiceProviderService.getProvider(payment.getPayServiceProviderCode());
		if (provider == null) {
			throw new EmallException(EmallStatusCode.ERROR_PAY_PROVIDER_NOT_FOUND);
		}
		entity.setPayServiceProviderCode(provider.getServiceName());
		entity.setPayServiceProviderDesc(provider.getDescription());
		entity.setPayServiceProviderTitle(provider.getTitle());
		
		entity.setConfig( provider.convertConfig(payment) );
		
		entity.setCreateUser(userId);
		entity.setCreateTime(new Date());
		
		return convertToVo(getDao().save(entity));
	}

	@Override
	public Payment getByStoreAndCode(final String storeId, final String payCode) {
		EmlPayment payment = getDao().findOne(new Specification<EmlPayment>() {
			@Override
			public Predicate toPredicate(Root<EmlPayment> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				EmlStore store = new EmlStore();
				store.setId(storeId);
				Predicate p1 = cb.equal(root.get(EmlPayment_.store), store);
				Predicate p2 = cb.equal(root.get(EmlPayment_.payServiceProviderCode), payCode);
				return cb.and(p1, p2);
			}
		});
		return payment == null ? null : convertToVo(payment);
	}

	@Override
	public Payment getByOrder(Order order) {
		return getByStoreAndCode(order.getStore().getId(), order.getPayCode());
	}
	
//    private static byte[] getContentBytes(String content, String charset) {
//        if (charset == null || "".equals(charset)) {
//            return content.getBytes();
//        }
//        try {
//            return content.getBytes(charset);
//        } catch (UnsupportedEncodingException e) {
//        	logger.error("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:{}", charset);
//        	throw new EmallException(EmallStatusCode.ERROR_DATA_UNSUPPORT_EDENCODING);
//        }
//    }
	
}
