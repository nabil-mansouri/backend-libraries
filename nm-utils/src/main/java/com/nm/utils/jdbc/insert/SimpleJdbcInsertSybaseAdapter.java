package com.nm.utils.jdbc.insert;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.metadata.TableMetaDataContext;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.nm.utils.ReflectionUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class SimpleJdbcInsertSybaseAdapter implements SimpleJdbcInsertAdapter {

	public SimpleJdbcInsert build(JdbcTemplate template) throws Exception {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		TableMetaDataContext context = new TableMetaDataContextSybase();
		ReflectionUtils.setRecursively(insert, "tableMetaDataContext", context);
		return insert;
	}

}
