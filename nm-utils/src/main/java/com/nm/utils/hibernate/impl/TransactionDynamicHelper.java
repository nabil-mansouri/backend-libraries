package com.nm.utils.hibernate.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TransactionDynamicHelper {

	public <T> T doInTransaction(JdbcTemplate template, TransactionCallback<T> t) {
		TransactionTemplate tr = new TransactionTemplate();
		tr.setTransactionManager(new DataSourceTransactionManager(template.getDataSource()));
		tr.afterPropertiesSet();
		return tr.execute(t);
	}

	public <T> T doInTransaction(HibernateTemplate template, TransactionCallback<T> t) {
		TransactionTemplate tr = new TransactionTemplate();
		tr.setTransactionManager(new HibernateTransactionManager(template.getSessionFactory()));
		tr.afterPropertiesSet();
		return tr.execute(t);
	}

}
