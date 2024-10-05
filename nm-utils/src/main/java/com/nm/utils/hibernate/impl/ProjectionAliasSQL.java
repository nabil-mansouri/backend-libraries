package com.nm.utils.hibernate.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Projection;
import org.hibernate.type.Type;

/**
 * 
 * @author Nabil
 * 
 */
public class ProjectionAliasSQL implements Projection {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Projection projection;

	public ProjectionAliasSQL(Projection projection) {
		this.projection = projection;
	}

	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery) throws HibernateException {
		return applyAliases(projection.toSqlString(criteria, position, criteriaQuery), criteria, criteriaQuery);
	}

	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		return applyAliases(projection.toGroupSqlString(criteria, criteriaQuery), criteria, criteriaQuery);
	}

	private String applyAliases(String sql, Criteria criteria, CriteriaQuery criteriaQuery) {
		StringBuilder res = new StringBuilder();
		int cnt = sql.length();
		int i = 0;
		while (i < cnt) {
			int l = sql.indexOf('{', i);
			if (l == -1) {
				break;
			}
			String before = sql.substring(i, l);
			res.append(before);
			int r = sql.indexOf('}', l);
			String alias = sql.substring(l + 1, r);
			if (alias.length() <= 0 || "alias".equals(alias)) { // root alias
				res.append(criteriaQuery.getSQLAlias(criteria));
			} else {
				String columns = criteriaQuery.getSQLAlias(criteria, alias);
				if (columns == null)
					throw new HibernateException("SQLAliasedCriterion not found: " + alias);
				res.append(columns);
			}
			i = r + 1;
		}
		String after = sql.substring(i, cnt);
		res.append(after);

		return res.toString();
	}

	public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		return projection.getTypes(criteria, criteriaQuery);
	}

	public Type[] getTypes(String alias, Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		return projection.getTypes(alias, criteria, criteriaQuery);
	}

	public String[] getColumnAliases(int position) {
		return projection.getColumnAliases(position);
	}

	public String[] getColumnAliases(String alias, int position) {
		return projection.getColumnAliases(alias, position);
	}

	public String[] getAliases() {
		return projection.getAliases();
	}

	public boolean isGrouped() {
		return projection.isGrouped();
	}

}
