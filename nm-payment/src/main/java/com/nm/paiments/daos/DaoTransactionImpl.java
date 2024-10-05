package com.nm.paiments.daos;

import com.nm.paiments.models.Transaction;
import com.nm.paiments.models.TransactionSubject;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoTransactionImpl extends AbstractGenericDao<Transaction, Long>implements DaoTransaction {

	@Override
	protected Class<Transaction> getClassName() {
		return Transaction.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public Transaction findBySubject(TransactionSubject owner) throws NoDataFoundException {
		return findFirst(QueryTransactionBuilder.get().withRoot(true).withSubject(owner));
	}
}
