package com.rongji.cms.emall.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongji.cms.emall.service.evaluation.BuyerEvaluationService;
import com.rongji.cms.emall.service.evaluation.DynamicServiceProvider;
import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.cms.emall.support.EmallConfig;
import com.rongji.dfish.cache.RedisManage;
import com.rongji.dfish.cache.RedisProvider;
import com.rongji.dfish.cache.services.vote.VoteServiceProvider;
import com.rongji.rjskeleton.service.NoneDaoCommonServiceImpl;

@Service
public class RedisSeriveImpl extends NoneDaoCommonServiceImpl<RedisVo> implements RedisService {
	
	@Autowired
	private BuyerEvaluationService buyerEvaluationService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	public void setRedisServiceProviders(EmallConfig config) {
			
		RedisProvider.addServer(EmallConfig.getRedisUrl(), EmallConfig.getRedisPort());
		
		RedisManage.clearServiceProvider();
		
		DynamicServiceProvider dsp=new DynamicServiceProvider();		
		dsp.setBuyerEvaluationService(buyerEvaluationService);
		dsp.setOrderService(orderService);
		RedisManage.registeServiceProvider(dsp);
		
		RedisManage.registeServiceProvider(new VoteServiceProvider());

	}
	
}
