package com.nm.paiments.daos;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;

import com.nm.paiments.models.Payer;
import com.nm.paiments.models.PayerAny;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoPayerImpl extends AbstractGenericDao<Payer, Long>implements DaoPayer {

	@Override
	protected Class<Payer> getClassName() {
		return Payer.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public Payer getOrCreateAny() {
		Collection<Payer> any = this.findAny();
		if (any.isEmpty()) {
			Payer tr = new PayerAny();
			getHibernateTemplate().save(tr);
			return tr;
		} else {
			return (any.iterator().next());
		}
	}

	public Collection<Payer> findAny() {
		return find(DetachedCriteria.forClass(PayerAny.class));
	}
}
