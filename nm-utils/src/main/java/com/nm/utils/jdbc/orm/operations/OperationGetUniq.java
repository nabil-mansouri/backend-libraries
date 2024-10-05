package com.nm.utils.jdbc.orm.operations;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.orm.JdbcOrmUtils;
import com.nm.utils.jdbc.select.RowMapperObject;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class OperationGetUniq<T> extends OperationAbstract<T> {
	private final T entity;

	public OperationGetUniq(JdbcTemplate template, T entity) {
		super(template);
		this.entity = entity;
	}

	public T operation() throws JdbcDaoException {
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) entity.getClass();
			String fullTableName = JdbcOrmUtils.getFullTableName(clazz);
			String[] idName = JdbcOrmUtils.getUniqueColumns(clazz);
			MapSqlParameterSource id = JdbcOrmUtils.getUniqueValues(entity, true);
			Assert.isTrue(idName.length > 0, "MUST HAVE IDS authorized");
			// BUILD QUERY
			List<String> ands = Lists.newArrayList();
			for (String s : idName) {
				ands.add(String.format("%s = :%s", s, s));
			}
			String where = StringUtils.join(ands, " AND ");
			String sql = String.format("SELECT * FROM %s WHERE %s", fullTableName, where);
			return getTemplate().queryForObject(sql, id, new RowMapperObject<T>(clazz));
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}
}
