package com.nm.utils.graphs.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.event.spi.PreCollectionUpdateEvent;
import org.hibernate.event.spi.PreCollectionUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;

/***
 * 
 * @author Nabil MANSOURI
 *
 */
public class GraphOptimizedListener {

	public static class MyListener
			implements PostLoadEventListener, PreCollectionUpdateEventListener, SaveOrUpdateEventListener {
		//
		// /**
		// *
		// */
		private static final long serialVersionUID = 1L;

		public void onPostLoad(PostLoadEvent event) {
			if (event.getEntity() instanceof GraphOptimizedModel) {
				@SuppressWarnings("rawtypes")
				GraphOptimizedModel entity = (GraphOptimizedModel) event.getEntity();
				entity.buildFromState();
			}
		}

		public void onPreUpdateCollection(PreCollectionUpdateEvent event) {
			Object owner = event.getAffectedOwnerOrNull();
			if (owner instanceof GraphOptimizedModel) {
				@SuppressWarnings("rawtypes")
				GraphOptimizedModel entity = (GraphOptimizedModel) owner;
				entity.buildState();
			}
		}

		public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
			Object owner = event.getEntity();
			if (owner instanceof GraphOptimizedModel) {
				@SuppressWarnings("rawtypes")
				GraphOptimizedModel entity = (GraphOptimizedModel) owner;
				entity.buildState();
			}
		}

	}

	public static MyListener build(SessionFactory sessionFactory) {
		EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry()
				.getService(EventListenerRegistry.class);
		MyListener l = new MyListener();
		registry.getEventListenerGroup(EventType.SAVE).appendListener(l);
		registry.getEventListenerGroup(EventType.UPDATE).appendListener(l);
		registry.getEventListenerGroup(EventType.POST_LOAD).appendListener(l);
		return l;
	}

}
