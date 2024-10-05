package com.nm.utils.jdbc;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class IDialectDBF implements IDialect {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void afterQuery(JdbcQueryBuilderSelect query, StringBuilder builder) {

	}

	public void afterWhere(JdbcQueryBuilderSelect query, StringBuilder builder) {
		if (query.hasLimitOffset()) {
			builder.append(" ");
			if (query.criterias.isEmpty()) {
				builder.append("WHERE ");
			}
			builder.append(String.format("_rowid_ > %s", query.offset));
			builder.append(" ");
		}
	}

}
