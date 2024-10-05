package com.nm.utils.hibernate.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TransactionWrapper {

	public static interface TransactionHandler<T> {
		public T process();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T requireNew(TransactionHandler<T> h) {
		return h.process();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public <T> T requireIfNotExists(TransactionHandler<T> h) {
		return h.process();
	}
}
