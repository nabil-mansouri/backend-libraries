package com.nm.orders.dao;

import java.util.Collection;

import com.nm.orders.models.OrderAccount;
import com.nm.orders.models.OrderAccountAny;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoOrderAccountImpl extends AbstractGenericDao<OrderAccount, Long>implements DaoOrderAccount {

	@Override
	protected Class<OrderAccount> getClassName() {
		return OrderAccount.class;
	}

	public Collection<OrderAccount> findAny() {
		QueryOrderAccountBuilder q1 = QueryOrderAccountBuilder.getAny();
		return find(q1.getQuery());
	}

	public OrderAccount getOrCreateAny() {
		Collection<OrderAccount> any = this.findAny();
		if (any.isEmpty()) {
			OrderAccount tr = new OrderAccountAny();
			getHibernateTemplate().save(tr);
			flush();
			return tr;
		} else {
			return (any.iterator().next());
		}
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
