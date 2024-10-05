package com.rm.dao.discounts.impl;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rm.dao.discounts.DaoDiscountLifeCycleState;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleState;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoDiscountLifeCycleStateImpl extends AbstractGenericDao<DiscountLifeCycleState, Long>
		implements DaoDiscountLifeCycleState {

	@Override
	protected Class<DiscountLifeCycleState> getClassName() {
		return DiscountLifeCycleState.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public void updateDate(final Long id, final Date date) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			public Void doInHibernate(Session session) throws HibernateException {
				session.createQuery("UPDATE DiscountLifeCycleState SET created=:date WHERE id=:id")
						.setTimestamp("date", date).setParameter("id", id).executeUpdate();
				return null;
			}
		});
	}

}
