package com.nm.paiments.daos;

import java.util.Collection;

import com.nm.paiments.constants.PaymentType;
import com.nm.paiments.models.PaymentMean;
import com.nm.paiments.models.PaymentMeanAny;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoPaymentMeanImpl extends AbstractGenericDao<PaymentMean, Long>implements DaoPaymentMean {

	@Override
	protected Class<PaymentMean> getClassName() {
		return PaymentMean.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public PaymentMean getOrCreateAny(PaymentType type) {
		Collection<PaymentMean> any = this.findAny(type);
		if (any.isEmpty()) {
			PaymentMean tr = new PaymentMeanAny();
			tr.setPaymentType(type);
			getHibernateTemplate().save(tr);
			flush();
			return tr;
		} else {
			return (any.iterator().next());
		}
	}

	public Collection<PaymentMean> findAny(PaymentType type) {
		QueryPaymentMeanBuilder q1 = QueryPaymentMeanBuilder.getAny().withType(type);
		return find(q1.getQuery());
	}
}
