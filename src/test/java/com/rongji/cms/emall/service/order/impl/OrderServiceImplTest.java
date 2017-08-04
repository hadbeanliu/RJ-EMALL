package com.rongji.cms.emall.service.order.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongji.cms.emall.service.order.OrderService;
import com.rongji.rjskeleton.test.SpringTransactionalTestCase;

public class OrderServiceImplTest extends SpringTransactionalTestCase {
	
	@Autowired
	private OrderService orderService;

	@Test
	public void test() {
		System.out.println(orderService);
		assertNotNull(orderService);
	}

}
