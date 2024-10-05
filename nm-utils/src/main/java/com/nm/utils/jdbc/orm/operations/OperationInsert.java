package com.nm.utils.jdbc.orm.operations;

import java.lang.reflect.Field;

import javax.persistence.Table;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.google.common.base.Strings;
import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.insert.SimpleJdbcInsertAdapter;
import com.nm.utils.jdbc.orm.JdbcOrmUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class OperationInsert<T> extends OperationAbstract<T> {
	private final SimpleJdbcInsertAdapter adapter;
	private final T entity;

	public OperationInsert(T entity, JdbcTemplate template) {
		super(template);
		this.entity = entity;
		adapter = new SimpleJdbcInsertAdapter() {

			public SimpleJdbcInsert build(JdbcTemplate template) throws Exception {
				return new SimpleJdbcInsert(template);
			}
		};
	}

	public OperationInsert(T entity, SimpleJdbcInsertAdapter adapter, JdbcTemplate template) {
		super(template);
		this.entity = entity;
		this.adapter = adapter;
	}

	public T operation() throws JdbcDaoException {
		try {
			Table table = entity.getClass().getAnnotation(Table.class);
			// BUILD QUERY
			SimpleJdbcInsert insert = adapter.build(getJdbc());
			if (!Strings.isNullOrEmpty(table.name())) {
				insert.withTableName(table.name());
			}
			if (!Strings.isNullOrEmpty(table.catalog())) {
				insert.withCatalogName(table.catalog());
			}
			if (!Strings.isNullOrEmpty(table.schema())) {
				insert.withSchemaName(table.schema());
			}
			//
			String[] generated = JdbcOrmUtils.isGeneratedId(entity);
			if (generated.length > 0) {
				MapSqlParameterSource toUpdate = JdbcOrmUtils.getNonIdsValuesMapped(entity, false);
				String[] restricted = JdbcOrmUtils.getIdsColumns(entity);
				String[] updated = JdbcOrmUtils.getNonIdsColumns(entity, false);
				Field[] idField = JdbcOrmUtils.getIdsField(entity);
				insert.usingColumns(updated);
				insert.usingGeneratedKeyColumns(restricted);
				Assert.isTrue(generated.length == 1, "Only one generated column");
				Assert.isTrue(restricted.length == 1, "Only one id column");
				KeyHolder id = insert.executeAndReturnKeyHolder(toUpdate);
				com.nm.utils.ReflectionUtils.set(entity, idField[0], id.getKey());
			} else {
				MapSqlParameterSource toUpdate = JdbcOrmUtils.getAllValuesMapped(entity, false);
				String[] updated = JdbcOrmUtils.getAllColumns(entity, false);
				insert.usingColumns(updated);
				Assert.isTrue(updated.length > 0, "Must have columns");
				insert.execute(toUpdate);
			}
			return entity;
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}
}
