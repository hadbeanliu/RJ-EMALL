package com.rongji.cms.emall.service.pay;

import java.util.List;

import com.rongji.cms.emall.entity.EmlPayment;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.Payment;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

public interface PaymentService extends CommonService<Payment, EmlPayment> {
	
	/**
	 * 根据商店获取支付方式
	 * 
	 * @param storeId 删掉标识
	 * @return 不存在数据时返回空的集合
	 */
	public List<Payment> getPaymentsByStore(String storeId);
	
	public List<Payment> getWebPaymentsByStore(String storeId);
	
	public List<Payment> getMobilePaymentsByStore(String storeId);
	
//	public Payment getPaymentByStoreAndPayProvider(String storeId, PayProvider payProvider);
	
	/**
	 * 根据支付提供商代号创建支付方式
	 * 
	 * @param userId 当前操作用户，必须是店铺所属人
	 * @param payment 支付提供商配置
	 * @return 若<code>code</code>不存在则返回{@literal null}
	 */
	public Payment createPaymentByPayCode(String userId, Payment payment);
	
	public Payment getByStoreAndCode(String storeId, String payCode);
	
	public Payment getByOrder(Order order);
	
	public PageEntity<Payment> getByDatatablesByStore(String storeId, DatatablesMetadata metadata);

}
