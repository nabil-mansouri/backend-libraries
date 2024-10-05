package com.nm.utils.jdbc.orm.operations;

import java.lang.reflect.Field;

import org.springframework.jdbc.core.JdbcTemplate;

import com.nm.utils.ReflectionUtils;
import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.insert.SimpleJdbcInsertAdapter;
import com.nm.utils.jdbc.orm.JdbcOrmUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class OperationSaveUniq<T> extends OperationAbstract<T> {
	private final T entity;
	private final OperationSaveOrUpdate<T> insert;
	private final OperationUpdate<T> update;
	private final OperationGetUniq<T> uniq;

	public OperationSaveUniq(T entity, SimpleJdbcInsertAdapter adapter, JdbcTemplate template) {
		super(template);
		this.entity = entity;
		insert = new OperationSaveOrUpdate<T>(template, entity, adapter);
		update = new OperationUpdate<T>(entity, template);
		uniq = new OperationGetUniq<T>(template, entity);
	}

	public T operation() throws JdbcDaoException {
		try {
			Field[] idFields = JdbcOrmUtils.getIdsField(entity.getClass());
			// TRY TO GET THEN UPDATE
			T original = uniq.operation();
			for (Field f : idFields) {
				ReflectionUtils.transfer(f, original, entity);
			}
			return update.operation();
		} catch (Exception e) {
			// IF FAILED => INSERT (OR UPDATE IF ID EXISTS)
			return insert.operation();
		}
	}
}
