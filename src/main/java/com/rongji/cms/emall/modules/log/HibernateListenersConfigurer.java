package com.rongji.cms.emall.modules.log;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateListenersConfigurer {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public void init() {
		System.out.println(entityManagerFactory.getClass());
		System.out.println();
		System.out.println();
		System.out.println();
	}

}
