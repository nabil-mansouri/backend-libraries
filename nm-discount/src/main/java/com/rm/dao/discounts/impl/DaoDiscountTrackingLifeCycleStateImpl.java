package com.rm.dao.discounts.impl;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rm.dao.discounts.DaoDiscountTrackingLifeCycleState;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleState;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoDiscountTrackingLifeCycleStateImpl extends AbstractGenericDao<DiscountTrackingLifeCycleState, Long>
		implements DaoDiscountTrackingLifeCycleState {

	@Override
	protected Class<DiscountTrackingLifeCycleState> getClassName() {
		return DiscountTrackingLifeCycleState.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public void updateDate(final Long id, final Date date) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			public Void doInHibernate(Session session) throws HibernateException {
				session.createQuery("UPDATE DiscountTrackingLifeCycleState SET created=:date WHERE id=:id")
						.setDate("date", date).setParameter("id", id).executeUpdate();
				return null;
			}
		});
	}

}
