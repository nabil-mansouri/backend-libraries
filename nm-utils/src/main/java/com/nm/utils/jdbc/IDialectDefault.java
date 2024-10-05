package com.nm.utils.jdbc;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class IDialectDefault implements IDialect {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void afterQuery(JdbcQueryBuilderSelect query, StringBuilder builder) {
		if (query.hasLimitOffset()) {
			builder.append(" ");
			builder.append(String.format("LIMIT %s, %s", query.limit, query.offset));
			builder.append(" ");
		}
	}

	public void afterWhere(JdbcQueryBuilderSelect query, StringBuilder builder) {

	}

}
