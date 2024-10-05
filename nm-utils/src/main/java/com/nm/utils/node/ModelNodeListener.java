package com.nm.utils.node;

import java.math.BigInteger;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.FlushEvent;
import org.hibernate.event.spi.FlushEventListener;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.ModelTimeable;

/***
 * 
 * @author Nabil MANSOURI
 *
 */
public class ModelNodeListener {

	public static class MyListener implements PreInsertEventListener, PreDeleteEventListener, FlushEventListener {
		private static final long serialVersionUID = 1L;
		private Set<ModelTimeable> inserted = Sets.newHashSet();
		private Set<ModelTimeable> deleted = Sets.newHashSet();
		private DaoModelNode dao;

		public MyListener(DaoModelNode dao) {
			super();
			this.dao = dao;
		}

		public boolean onPreDelete(PreDeleteEvent event) {
			Object owner = event.getEntity();
			if (owner instanceof ModelTimeable) {
				deleted.add((ModelTimeable) owner);
			}
			return false;
		}

		public boolean onPreInsert(PreInsertEvent event) {
			Object owner = event.getEntity();
			if (owner instanceof ModelTimeable) {
				ModelTimeable entity = (ModelTimeable) owner;
				if (entity.getNodeId() == null) {
					final ModelNodeType type = dao.types(entity.getClass());
					String uuid = String.format("%s%s", Strings.padStart(type.getId() + "", 4, '0'),
							Strings.padStart(entity.getId() + "", 10, '0'));
					String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();
					Object[] state = event.getState();
					final BigInteger b = new BigInteger(uuid);
					setValue(state, propertyNames, "nodeId", b, entity);
					entity.setNodeId(b);
					inserted.add(entity);
				}

			}
			return false;
		}

		public void onFlush(FlushEvent event) throws HibernateException {
			try {
				for (ModelTimeable entity : inserted) {
					final ModelNodeType type = dao.types(entity.getClass());
					ModelNode n = new ModelNode();
					n.setId(entity.getNodeId());
					n.setType(type);
					dao.insert(n);
				}
				for (ModelTimeable entity : deleted) {
					if (entity.autoDeleteNode()) {
						dao.delete(entity.getNodeId());
					} else {
						try {
							ModelNode node = dao.get(entity.getNodeId());
							node.setDead(true);
							dao.update(node);
						} catch (NoDataFoundException e) {
						}
					}
				}
			} finally {
				inserted.clear();
				deleted.clear();
			}
		}

		void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value, Object entity) {
			int index = ArrayUtils.indexOf(propertyNames, propertyToSet);
			if (index >= 0) {
				currentState[index] = value;
			}
		}

		public boolean requiresPostCommitHanding(EntityPersister persister) {
			return true;
		}
	}

	public static MyListener build(SessionFactory sessionFactory) {
		EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
		MyListener l = new MyListener(new DaoModelNodeImpl(new HibernateTemplate(sessionFactory)));
		registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(l);
		registry.getEventListenerGroup(EventType.PRE_DELETE).appendListener(l);
		registry.getEventListenerGroup(EventType.FLUSH).appendListener(l);
		return l;
	}

}
