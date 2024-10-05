package com.nm.utils.jdbc.orm;

import java.util.Collection;
import java.util.List;

import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.google.common.collect.Lists;
import com.nm.utils.jdbc.select.RowMapperObject;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class JdbcOrmAssociationProcessor {
	public void process(Object o, NamedParameterJdbcTemplate template) throws Exception {
		Collection<JdbcOrmAssociationContext> assoc = JdbcOrmUtils.associations(o);
		for (JdbcOrmAssociationContext a : assoc) {
			if (isOk(a)) {
				Table table = a.getTarget().getAnnotation(Table.class);
				// BUILD QUERY
				List<String> ands = Lists.newArrayList();
				for (String s : a.getMap().getValues().keySet()) {
					ands.add(String.format("%s = :%s", s, s));
				}
				String where = StringUtils.join(ands, " AND ");
				String sql = String.format("SELECT * FROM %s WHERE %s", table.name(), where);
				@SuppressWarnings("unchecked")
				Class<Object> cl = (Class<Object>) a.getTarget();
				RowMapperObject<Object> mapper = new RowMapperObject<Object>(cl);
				List<Object> founded = template.query(sql, a.getMap(), mapper);
				onFoundedList(o, founded, a);
			}
		}
	}

	protected abstract boolean isOk(JdbcOrmAssociationContext a) throws Exception;

	protected abstract void onFoundedList(Object root, List<Object> founded, JdbcOrmAssociationContext context) throws Exception;

}
