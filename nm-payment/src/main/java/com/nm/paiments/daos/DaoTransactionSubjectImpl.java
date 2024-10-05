package com.nm.paiments.daos;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;

import com.nm.paiments.models.TransactionSubject;
import com.nm.paiments.models.TransactionSubjectSame;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoTransactionSubjectImpl extends AbstractGenericDao<TransactionSubject, Long>
		implements DaoTransactionSubject {

	@Override
	protected Class<TransactionSubject> getClassName() {
		return TransactionSubject.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public TransactionSubject createSame() {
		@SuppressWarnings("unchecked")
		Collection<TransactionSubjectSame> same = (Collection<TransactionSubjectSame>) getHibernateTemplate()
				.findByCriteria(DetachedCriteria.forClass(TransactionSubjectSame.class));
		if (same.isEmpty()) {
			TransactionSubjectSame tr = new TransactionSubjectSame();
			getHibernateTemplate().save(tr);
			return tr;
		} else {
			return same.iterator().next();
		}
	}

}
