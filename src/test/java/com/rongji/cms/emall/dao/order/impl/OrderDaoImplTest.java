package com.rongji.cms.emall.dao.order.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.rongji.cms.emall.dao.order.OrderDao;
import com.rongji.cms.emall.entity.EmlOrder;
import com.rongji.cms.emall.entity.EmlOrder_;
import com.rongji.rjskeleton.test.SpringTransactionalTestCase;

public class OrderDaoImplTest extends SpringTransactionalTestCase {
	
	@Autowired
	private OrderDao orderDao;

	@Test
	public void test() {
		assertNotNull(orderDao);
		
		EmlOrder order = orderDao.findOne("00000001");
		assertNotNull(order);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(order.getOrderNo());
		System.out.println(order.getCreateTime());
		System.out.println();
		
	}
	
	@Test
	public void testSelect() {
		List<EmlOrder> list = orderDao.findAll(new Specification<EmlOrder>() {
			@Override
			public Predicate toPredicate(Root<EmlOrder> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// where id = '0001';
//				query.where(cb.equal(root.get(EmlOrder_.id), "00000001"));
				
				Predicate p = cb.equal(root.get(EmlOrder_.id), "00000001");
				Predicate p2 = cb.equal(root.get(EmlOrder_.id), "00000001");
				
				// where id='x' and title=""
				query.where(p, p2);
				
				return null;
			}
		});
		
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		System.out.println(list.size());
	}

	
}