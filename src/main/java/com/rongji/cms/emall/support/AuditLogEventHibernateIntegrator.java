package com.rongji.cms.emall.support;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import com.rongji.cms.emall.support.listerners.AuditLogEventListener;
import com.rongji.rjskeleton.support.db.HibernateIntegrator;

@HibernateIntegrator
public class AuditLogEventHibernateIntegrator implements Integrator {

	@Override
	public void integrate(Configuration configuration,
			SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		EventListenerRegistry registry = serviceRegistry.getService(EventListenerRegistry.class);
		
		AuditLogEventListener listeners = new AuditLogEventListener();
		registry.appendListeners(EventType.PRE_INSERT, listeners);
		registry.appendListeners(EventType.POST_COMMIT_INSERT, listeners);
		registry.appendListeners(EventType.PRE_UPDATE, listeners);
		registry.appendListeners(EventType.POST_COMMIT_UPDATE, listeners);
		registry.appendListeners(EventType.PRE_DELETE, listeners);
		registry.appendListeners(EventType.POST_COMMIT_DELETE, listeners);
	}

	@Override
	public void integrate(MetadataImplementor metadata,
			SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		
	}

}
