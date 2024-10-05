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
public class OperationGetByMap<T> extends OperationAbstract<T> {
	private final Class<T> clazz;
	private final MapSqlParameterSource id;

	public OperationGetByMap(JdbcTemplate template, Class<T> clazz, MapSqlParameterSource id) {
		super(template);
		this.clazz = clazz;
		this.id = id;
	}

	public T operation() throws JdbcDaoException {
		try {
			String fullTableName = JdbcOrmUtils.getFullTableName(clazz);
			String[] idName = JdbcOrmUtils.getIdsColumns(clazz);
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
