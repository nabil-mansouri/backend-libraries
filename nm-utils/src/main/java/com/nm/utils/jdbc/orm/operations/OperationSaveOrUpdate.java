package com.nm.utils.jdbc.orm.operations;

import org.springframework.jdbc.core.JdbcTemplate;

import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.insert.SimpleJdbcInsertAdapter;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class OperationSaveOrUpdate<T> extends OperationAbstract<T> {
	private final T entity;
	private final SimpleJdbcInsertAdapter adapter;

	public OperationSaveOrUpdate(JdbcTemplate template, T entity, SimpleJdbcInsertAdapter adapter) {
		super(template);
		this.entity = entity;
		this.adapter = adapter;
	}

	public T operation() throws JdbcDaoException {
		try {
			// TRY TO GET THEN UPDATE
			new OperationGetByExample<T>(jdbc, entity).operation();
			return new OperationUpdate<T>(entity, jdbc).operation();
		} catch (Exception e) {
			// IF FAILED => INSERT
			return new OperationInsert<T>(entity, adapter, jdbc).operation();
		}
	}
}
