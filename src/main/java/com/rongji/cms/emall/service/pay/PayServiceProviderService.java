package com.rongji.cms.emall.service.pay;

import com.rongji.cms.emall.service.pay.provider.PayProvider;
import com.rongji.cms.emall.vo.PayServiceProvider;
import com.rongji.rjskeleton.service.NoneDaoCommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 支付服务提供商
 *
 * @since 2015-6-12
 * @author rjf
 *
 */
public interface PayServiceProviderService extends NoneDaoCommonService<PayServiceProvider> {
	
//	/**
//	 * 根据代号获取提供商
//	 * 
//	 * @param code 代号
//	 * @return 不存在返回{@literal null}
//	 */
//	public PayServiceProvider getByCode(String code);
	
	/**
	 * 根据代号获取提供商
	 * 
	 * @param code 代号
	 * @return 不存在返回{@literal null}
	 */	
	public PayProvider getProvider(String code);
	
	public PageEntity<PayServiceProvider> getByDatatablesByStore(String storeId, DatatablesMetadata metadata);

}
