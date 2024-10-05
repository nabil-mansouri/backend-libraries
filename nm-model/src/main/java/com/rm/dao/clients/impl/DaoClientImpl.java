package com.rm.dao.clients.impl;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.joda.time.MutableDateTime;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rm.dao.clients.DaoClient;
import com.rm.model.clients.Client;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoClientImpl extends AbstractGenericDao<Client, Long>implements DaoClient {

	@Override
	protected Class<Client> getClassName() {
		return Client.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public void updateSubscribedDate(final Long id, final Date date) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			public Void doInHibernate(Session session) throws HibernateException {
				MutableDateTime d = new MutableDateTime(date);
				session.createQuery(
						"UPDATE Client SET subscribed=:date, subscribedDay=:day, subscribedMonth=:month WHERE id=:id")
						.setInteger("day", d.getDayOfMonth()).setInteger("month", d.getMonthOfYear())
						.setDate("date", date).setParameter("id", id).executeUpdate();
				return null;
			}
		});
	}
}
