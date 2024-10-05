package com.nm.orders.dao;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.nm.orders.models.OrderState;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoOrderStateImpl extends AbstractGenericDao<OrderState, Long>implements DaoOrderState {

	@Override
	protected Class<OrderState> getClassName() {
		return OrderState.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public void updateDate(final Long id, final Date date) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			public Void doInHibernate(Session session) throws HibernateException {
				session.createQuery("UPDATE OrderState SET created=:date WHERE id=:id").setDate("date", date)
						.setParameter("id", id).executeUpdate();
				return null;
			}
		});
	}

}
