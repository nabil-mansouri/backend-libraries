package com.rm.dao.clients.impl;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rm.dao.clients.DaoSponsorship;
import com.rm.model.clients.Sponsorship;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoSponsorshipImpl extends AbstractGenericDao<Sponsorship, Long>implements DaoSponsorship {

	@Override
	protected Class<Sponsorship> getClassName() {
		return Sponsorship.class;
	}

	public void updateDate(final Long id, final Date date) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			public Void doInHibernate(Session session) throws HibernateException {
				session.createQuery("UPDATE Sponsorship SET created=:date WHERE id=:id").setDate("date", date)
						.setParameter("id", id).executeUpdate();
				return null;
			}
		});
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
