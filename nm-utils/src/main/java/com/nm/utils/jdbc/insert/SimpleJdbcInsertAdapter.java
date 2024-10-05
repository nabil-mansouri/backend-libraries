package com.nm.utils.jdbc.insert;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface SimpleJdbcInsertAdapter {
	public SimpleJdbcInsert build(JdbcTemplate template) throws Exception;
}
