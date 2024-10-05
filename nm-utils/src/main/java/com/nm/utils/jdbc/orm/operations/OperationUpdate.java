package com.nm.utils.jdbc.orm.operations;

import javax.persistence.Table;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.Assert;

import com.google.common.base.Strings;
import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.orm.JdbcOrmUtils;
import com.nm.utils.jdbc.update.SimpleJdbcUpdate;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class OperationUpdate<T> extends OperationAbstract<T> {
	private boolean ignoreNull;
	private final T entity;

	public OperationUpdate(T entity, JdbcTemplate template) {
		this(entity, template, false);
	}

	public OperationUpdate(T entity, JdbcTemplate template, boolean ignoreNull) {
		super(template);
		this.entity = entity;
		this.ignoreNull = ignoreNull;
	}

	public T operation() throws JdbcDaoException {
		try {
			Table table = entity.getClass().getAnnotation(Table.class);
			//
			MapSqlParameterSource toUpdate = JdbcOrmUtils.getNonIdsValuesMapped(entity, ignoreNull);
			MapSqlParameterSource toRestrict = JdbcOrmUtils.getIdsValuesMapped(entity);
			String[] updated = JdbcOrmUtils.getNonIdsColumns(entity, ignoreNull);
			String[] restricted = JdbcOrmUtils.getIdsColumns(entity);
			Assert.isTrue(restricted.length > 0, "Restricted must be not empty (update all)");
			// BUILD QUERY
			SimpleJdbcUpdate update = new SimpleJdbcUpdate(getJdbc());
			if (!Strings.isNullOrEmpty(table.name())) {
				update.withTableName(table.name());
			}
			if (!Strings.isNullOrEmpty(table.catalog())) {
				update.withCatalogName(table.catalog());
			}
			if (!Strings.isNullOrEmpty(table.schema())) {
				update.withSchemaName(table.schema());
			}
			update.restrictingColumns(restricted);
			update.updatingColumns(updated);
			update.execute(toUpdate, toRestrict);
			return entity;
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}
}
