package com.nm.utils.jdbc;

import java.io.Serializable;

/***
 * 
 * @author Nabil MANSOURI
 *
 */
public interface IDialect extends Serializable {
	public void afterWhere(JdbcQueryBuilderSelect query, StringBuilder builder);

	public void afterQuery(JdbcQueryBuilderSelect query, StringBuilder builder);
}