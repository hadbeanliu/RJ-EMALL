package com.rongji.cms.emall.service.pay.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.service.pay.PayServiceProviderService;
import com.rongji.cms.emall.service.pay.PaymentService;
import com.rongji.cms.emall.service.pay.provider.PayProvider;
import com.rongji.cms.emall.service.pay.provider.alipay.AlipayDirectPayProvider;
import com.rongji.cms.emall.service.pay.provider.alipay.AlipayMobileSecurityPayProvider;
import com.rongji.cms.emall.service.pay.provider.alipay.AlipayPartnerTradeProvider;
import com.rongji.cms.emall.service.pay.provider.wxpay.WeixinPayProvider;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.vo.PayServiceProvider;
import com.rongji.cms.emall.vo.Payment;
import com.rongji.rjskeleton.service.NoneDaoCommonServiceImpl;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

@Service
public class PayServiceProviderServiceImpl extends NoneDaoCommonServiceImpl<PayServiceProvider>
		implements PayServiceProviderService {
	
	@Autowired
	private PaymentService paymentService;
	
	private Map<String, PayProvider> payProviders;
//	private List<PayServiceProvider> payServiceProviders;
	
//	public PayServiceProviderServiceImpl() {
//		initProviders();
//	}

	@PostConstruct
	public void initProviders() {
		payProviders = new HashMap<String, PayProvider>();
		
		addProvider(new AlipayPartnerTradeProvider());
		addProvider(new AlipayDirectPayProvider());
		addProvider(new AlipayMobileSecurityPayProvider());
		
		addProvider(new WeixinPayProvider());
		
//		payServiceProviders = new ArrayList<PayServiceProvider>(payProviders.size());
//		for (PayProvider provider : payProviders.values()) {
//			payServiceProviders.add( createPayServiceProvider(provider) );
//		}
	}

	@Override
	public PageEntity<PayServiceProvider> getByDatatablesByStore(String storeId, DatatablesMetadata metadata) {
		
		metadata.getSearchs();
		List<Payment> payments = paymentService.getPaymentsByStore(storeId);
		
		List<PayServiceProvider> payServiceProviders = getPayServiceProvider(payments);
		
		PageEntity<PayServiceProvider> ppsp = new PageEntity<PayServiceProvider>(metadata.getDraw(),
				payServiceProviders.size(), payServiceProviders.size(), payServiceProviders);
		
		return ppsp;
	}
	
//	@Override
//	public PayServiceProvider getByCode(String code) {
//		return createPayServiceProvider(payProviders.get(code));
//	}

	@Override
	public PayProvider getProvider(String code) {
		return payProviders.get(code);
	}
	
	private void addProvider(PayProvider payProvider) {
		String name = payProvider.getServiceName();
		if (name == null) {
			throw new NullPointerException();
		}
		if (payProviders.containsKey(name)) {
			throw new EmallException(EmallStatusCode.ERROR_DATA_EXIST);
		}
		
		payProviders.put(name, payProvider);
	}
	
	private List<PayServiceProvider> getPayServiceProvider(List<Payment> payments) {
		
		List<String> codes = null;
		if (payments != null && !payments.isEmpty()) {
			codes = new ArrayList<String>(payments.size());
			for (Payment payment : payments) {
				if (payment == null) {
					continue;
				}
				codes.add(payment.getPayServiceProviderCode());
			}
		}
		
		List<PayServiceProvider> payServiceProviders = new ArrayList<PayServiceProvider>(payProviders.size());
		for (PayProvider provider : payProviders.values()) {
			PayServiceProvider payServiceProvider = createPayServiceProvider(provider);
			if (codes != null && codes.contains(provider.getServiceName())) {
				payServiceProvider.setUsed(true);
			}
			payServiceProviders.add( payServiceProvider );
		}
		return payServiceProviders;
	}
	
	private PayServiceProvider createPayServiceProvider(PayProvider provider) {
		if (provider == null) {
			return null;
		}
		PayServiceProvider psp = new PayServiceProvider();
		
		psp.setId(provider.getServiceName());
		psp.setCode(provider.getServiceName());
		psp.setTitle(provider.getTitle());
		psp.setDescription(provider.getDescription());
		
		return psp;
	}

}
