package com.nm.permissions.acl.temp;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreCollectionUpdateEvent;
import org.hibernate.event.spi.PreCollectionUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;

import com.nm.utils.dtos.ModelSecured;

/**
 * DELETE IT?
 * @author Nabil MANSOURI
 *
 */
public class AclModelListener {
	public static class MyListener implements PreCollectionUpdateEventListener, SaveOrUpdateEventListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void saveACL(ModelSecured m) {

		}

		public void onPreUpdateCollection(PreCollectionUpdateEvent event) {
			Object owner = event.getAffectedOwnerOrNull();
			if (owner instanceof ModelSecured) {
				// ModelSecured entity = (ModelSecured) owner;

			}
		}

		public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
			Object owner = event.getEntity();
			if (owner instanceof ModelSecured) {
				// ModelSecured entity = (ModelSecured) owner;

			}
		}

	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry()
				.getService(EventListenerRegistry.class);
		MyListener l = new MyListener();
		registry.getEventListenerGroup(EventType.SAVE).appendListener(l);
		registry.getEventListenerGroup(EventType.UPDATE).appendListener(l);
	}

}
