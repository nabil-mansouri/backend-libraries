package com.nm.utils.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.utils.dates.DateUtilsExt;

/**
 * 
 * @author Mansouri Nabil
 *
 */
public abstract class JdbcQueryBuilderSelect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(JdbcQueryBuilderSelect.class);

	protected Map<ICriteria, JdbcCriteria> criterias = new HashMap<ICriteria, JdbcCriteria>();
	protected Collection<ITable> tables = Sets.newLinkedHashSet();
	protected Map<ITable, JdbcQueryBuilderSelect> subTables = new HashMap<ITable, JdbcQueryBuilderSelect>();
	protected Collection<ISelect> selects = Sets.newLinkedHashSet();
	protected Collection<ISelect> groups = Sets.newLinkedHashSet();
	protected Collection<ISelect> groupsOriginal = Sets.newLinkedHashSet();
	protected OrderList orders = new OrderList();
	protected Collection<IParameter> parameters = new ArrayList<IParameter>();
	protected Long limit;
	protected Long offset;
	protected IDialect dialect = new IDialectDefault();

	public JdbcQueryBuilderSelect withGroupsOriginal(ISelect group) {
		groupsOriginal.add(group);
		return this;
	}

	public Map<ICriteria, JdbcCriteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(Map<ICriteria, JdbcCriteria> criterias) {
		this.criterias = criterias;
	}

	public Collection<ISelect> getSelects() {
		return selects;
	}

	@SuppressWarnings("unchecked")
	public <T extends JdbcQueryBuilderSelect> T clone(Class<T> c) {
		return (T) SerializationUtils.clone(this);
	}

	public JdbcQueryBuilderSelect clone() {
		return SerializationUtils.clone(this);
	}

	public Collection<ITable> getTables() {
		return tables;
	}

	public void setTables(Collection<ITable> tables) {
		this.tables = tables;
	}

	public JdbcQueryBuilderSelect withSelects(Collection<ISelect> s) {
		this.selects.addAll(s);
		return this;
	}

	public JdbcQueryBuilderSelect withSelects(ISelect s) {
		this.selects.add(s);
		return this;
	}

	public JdbcQueryBuilderSelect withTables(ITable s) {
		this.tables.add(s);
		return this;
	}

	public JdbcQueryBuilderSelect withOrders(ISelect s) {
		this.orders.add(s);
		return this;
	}

	public JdbcQueryBuilderSelect withOrders(ISelect s, OrderBy order) {
		this.orders.put(s, order);
		return this;
	}

	public JdbcQueryBuilderSelect withGroups(Collection<ISelect> s) {
		this.groups.addAll(s);
		return this;
	}

	public JdbcQueryBuilderSelect withGroups(ISelect s) {
		this.groups.add(s);
		return this;
	}

	protected String getSubtable(ITable ss, String query) {
		return null;
	}

	public final String build() throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		if (selects.isEmpty()) {
			builder.append(" * ");
		} else {
			List<String> all = Lists.newArrayList();
			for (ISelect s : selects) {
				all.add(String.format("%s as %s", getSelectName(s), getSelectAlias(s)));
			}
			builder.append(StringUtils.join(all, ","));
		}
		builder.append(" FROM ");
		{
			List<String> all = Lists.newArrayList();
			all.add(getTableNameDefault());
			for (ITable s : tables) {
				all.add(getTableName(s));
			}
			for (ITable s : subTables.keySet()) {
				JdbcQueryBuilderSelect query = subTables.get(s);
				all.add(getSubtable(s, query.build()));
				this.push(query);
			}
			builder.append(StringUtils.join(all, " "));
		}
		if (!criterias.isEmpty()) {
			builder.append(" WHERE ");
			List<String> all = Lists.newArrayList();
			for (ICriteria s : criterias.keySet()) {
				all.add("(" + getWhere(s, criterias.get(s)) + ")");
			}
			builder.append(StringUtils.join(all, " AND "));
		}
		dialect.afterWhere(this, builder);
		builder.append(" ");
		if (!groups.isEmpty() || !groupsOriginal.isEmpty()) {
			builder.append(" GROUP BY ");
			List<String> all = Lists.newArrayList();
			for (ISelect s : groups) {
				all.add(getSelectAlias(s));
			}
			for (ISelect s : groupsOriginal) {
				all.add(getSelectName(s));
			}
			builder.append(StringUtils.join(all, " , "));
		}
		builder.append(" ");
		if (!orders.isEmpty()) {
			builder.append(" ORDER BY ");
			List<String> all = Lists.newArrayList();
			for (ISelect s : orders.keySet()) {
				all.add(String.format("%s %s", getSelectAlias(s), orders.get(s).name()));
			}
			builder.append(StringUtils.join(all, " , "));
		}
		builder.append(" ");
		dialect.afterQuery(this, builder);
		String s = builder.toString();
		//
		log.debug("Generated query : " + s);
		return s;
	}

	protected void push(JdbcQueryBuilderSelect p) {
		this.parameters.addAll(p.parameters);
	}

	public boolean hasLimitOffset() {
		return limit != null && offset != null;
	}

	public JdbcQueryBuilderSelect withLimit(Long limit) {
		this.limit = limit;
		return this;
	}

	public JdbcQueryBuilderSelect withOffset(Long offset) {
		this.offset = offset;
		return this;
	}

	public JdbcQueryBuilderSelect withDialect(IDialect dialect) {
		this.dialect = dialect;
		return this;
	}

	public final PreparedStatementCreator buildStatement() throws SQLException {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(build());
				int i = 1;
				for (IParameter p : parameters) {
					p.set(statement, i);
					i++;
				}
				return statement;
			}
		};

		return creator;
	}

	public final PreparedStatementCreator buildStatement(final int fetchSize) throws SQLException {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(build());
				statement.setFetchSize(fetchSize);
				int i = 1;
				for (IParameter p : parameters) {
					p.set(statement, i);
					i++;
				}
				return statement;
			}
		};

		return creator;
	}

	protected int getParamIndex() {
		return this.parameters.size();
	}

	protected void push(IParameter p) {
		this.parameters.add(p);
	}

	protected String buildCunjunction(List<String> l) {
		return String.format("(%s)", StringUtils.join(l, " AND "));
	}

	protected String buildDisjunction(List<String> l) {
		return String.format("(%s)", StringUtils.join(l, " OR "));
	}

	protected <T> String buildIn(List<T> l) {
		List<String> copy = new ArrayList<String>();
		for (final T ll : l) {
			copy.add("?");
			push(new IParameter() {

				private static final long serialVersionUID = 1L;

				public void set(PreparedStatement p, int index) throws SQLException {
					if (ll instanceof String) {
						p.setString(index, (String) ll);
					} else if (ll instanceof Integer) {
						p.setInt(index, (Integer) ll);
					} else if (ll instanceof Long) {
						p.setLong(index, (Long) ll);
					} else if (ll instanceof Double) {
						p.setDouble(index, (Double) ll);
					} else {
						p.setString(index, ll.toString());
					}
				}
			});
		}
		return StringUtils.join(copy, " , ");
	}

	protected boolean isSelectDefault() {
		return this.selects.isEmpty();
	}

	protected boolean isList(ICriteria crit) {
		return this.criterias.get(crit).isList();
	}

	protected <T> List<T> getCriteriaList(ICriteria key, Class<T> clazz) {
		return this.criterias.get(key).getList(clazz);
	}

	protected <T> T getCriteriaList(ICriteria key, Class<T> clazz, int index) {
		return this.criterias.get(key).getList(clazz).get(index);
	}

	protected <T> T getCriteria(ICriteria key, Class<T> clazz) {
		return this.criterias.get(key).get(clazz);
	}

	protected java.sql.Date getCriteriaSqlDateFromDate(ICriteria key) {
		return DateUtilsExt.toSqlDate(this.criterias.get(key).get(Date.class));
	}

	protected java.sql.Timestamp getCriteriaSqlTimestampFromDate(ICriteria key) {
		return DateUtilsExt.toSqlTimestamp(this.criterias.get(key).get(Date.class));
	}

	protected java.sql.Timestamp getCriteriaSqlTimestampFromDate(ICriteria key, int index) {
		return DateUtilsExt.toSqlTimestamp(getCriteriaList(key, Date.class, index));
	}

	public abstract String getWhere(ICriteria s, JdbcCriteria crit) throws SQLException;

	public abstract String getTableNameDefault();

	public abstract String getTableName(ITable s);

	public abstract String getSelectName(ISelect s);

	public abstract String getSelectAlias(ISelect s);
}
