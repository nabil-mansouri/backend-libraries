package com.nm.utils.jdbc.orm.operations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.nm.utils.jdbc.JdbcDaoException;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class OperationAbstract<T> {
	protected NamedParameterJdbcTemplate template;
	protected JdbcTemplate jdbc;

	public OperationAbstract(JdbcTemplate template) {
		super();
		this.jdbc = template;
		this.template = new NamedParameterJdbcTemplate(jdbc);
	}

	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	public NamedParameterJdbcTemplate getTemplate() {
		return template;
	}

	public abstract T operation() throws JdbcDaoException;
}
