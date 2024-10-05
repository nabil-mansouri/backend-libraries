package com.nm.utils.jdbc.orm.operations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.orm.JdbcOrmUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class OperationGetByExample<T> extends OperationAbstract<T> {
	private final T example;

	public OperationGetByExample(JdbcTemplate template, T example) {
		super(template);
		this.example = example;
	}

	@SuppressWarnings("unchecked")
	public T operation() throws JdbcDaoException {
		try {
			MapSqlParameterSource toRestrict = JdbcOrmUtils.getIdsValuesMapped(example);
			Class<T> clazz = (Class<T>) example.getClass();
			return (T) new OperationGetByMap<T>(jdbc, clazz, toRestrict).operation();
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}
}
