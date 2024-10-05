package com.rm.dao.clients.impl;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rm.dao.clients.DaoClientAction;
import com.rm.model.clients.ClientActions;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoClientActionImpl extends AbstractGenericDao<ClientActions, Long>implements DaoClientAction {

	@Override
	protected Class<ClientActions> getClassName() {
		return ClientActions.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public void updateDate(final Long id, final Date date) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			public Void doInHibernate(Session session) throws HibernateException {
				session.createQuery("UPDATE ClientActions SET created=:date WHERE id=:id").setDate("date", date)
						.setParameter("id", id).executeUpdate();
				return null;
			}
		});
	}
}
