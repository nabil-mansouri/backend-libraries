package com.nm.utils.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nm.utils.dates.DateUtilsExt;

/**
 * 
 * @author Mansouri Nabil
 *
 */
public abstract class JdbcQueryBuilderUpdate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(JdbcQueryBuilderUpdate.class);

	protected Collection<ITable> tables = Sets.newLinkedHashSet();
	protected Collection<IParameter> parameters = Lists.newArrayList();
	protected Map<IValues, JdbcCriteria> values = Maps.newConcurrentMap();
	protected Map<ICriteria, JdbcCriteria> criterias = Maps.newConcurrentMap();
	protected Map<ITable, JdbcQueryBuilderUpdate> subTables = Maps.newConcurrentMap();

	public Map<ICriteria, JdbcCriteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(Map<ICriteria, JdbcCriteria> criterias) {
		this.criterias = criterias;
	}

	@SuppressWarnings("unchecked")
	public <T extends JdbcQueryBuilderUpdate> T clone(Class<T> c) {
		return (T) SerializationUtils.clone(this);
	}

	public JdbcQueryBuilderUpdate clone() {
		return SerializationUtils.clone(this);
	}

	public Collection<ITable> getTables() {
		return tables;
	}

	public void setTables(Collection<ITable> tables) {
		this.tables = tables;
	}

	protected abstract String getDefaultTable();

	public final String build() throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE ");
		{
			List<String> all = Lists.newArrayList();
			all.add(getDefaultTable());
			for (ITable t : tables) {
				all.add(getTableName(t));
			}
			builder.append(StringUtils.join(all, ","));
		}
		builder.append(" SET ");
		{
			List<String> all = Lists.newArrayList();
			for (IValues val : values.keySet()) {
				all.add(String.format("%s = ?", getSetColumn(val)));
			}
			builder.append(StringUtils.join(all, " , "));
		}
		if (!criterias.isEmpty()) {
			builder.append(" WHERE ");
			List<String> all = Lists.newArrayList();
			for (ICriteria c : criterias.keySet()) {
				all.add(getWhere(c, criterias.get(c)));
			}
			builder.append(StringUtils.join(all, " AND "));
		}
		builder.append(" ");
		String s = builder.toString();
		//
		log.debug("Generated query : " + s);
		return s;
	}

	protected abstract String getWhere(ICriteria c, JdbcCriteria jdbcCriteria) throws SQLException;

	protected abstract String getSetColumn(IValues val);

	protected abstract String getTableName(ITable s);

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

	protected void push(JdbcQueryBuilderUpdate p) {
		this.parameters.addAll(p.parameters);
	}

	protected void push(JdbcQueryBuilderSelect p) {
		this.parameters.addAll(p.parameters);
	}

	protected String buildCunjunction(List<String> l) {
		return String.format("(%s)", StringUtils.join(l, " AND "));
	}

	protected String buildSets(List<String> l) {
		return StringUtils.join(l, " , ");
	}

	protected String buildSelects(List<String> l) {
		return StringUtils.join(l, " , ");
	}

	protected String buildGroups(List<String> l) {
		return StringUtils.join(l, " , ");
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

	protected boolean isList(ICriteria crit) {
		return this.criterias.get(crit).isList();
	}

	protected <T> List<T> getCriteriaList(ICriteria key, Class<T> clazz) {
		return this.criterias.get(key).getList(clazz);
	}

	protected <T> T getCriteria(ICriteria key, Class<T> clazz) {
		return this.criterias.get(key).get(clazz);
	}

	protected <T> T getSet(IValues key, Class<T> clazz) {
		return this.values.get(key).get(clazz);
	}

	protected boolean isList(IValues crit) {
		return this.values.get(crit).isList();
	}

	protected <T> List<T> getSetList(IValues key, Class<T> clazz) {
		return this.values.get(key).getList(clazz);
	}

	protected java.sql.Date getCriteriaSqlDateFromDate(ICriteria key) {
		return DateUtilsExt.toSqlDate(this.criterias.get(key).get(Date.class));
	}

	protected java.sql.Timestamp getCriteriaSqlTimestampFromDate(ICriteria key) {
		return DateUtilsExt.toSqlTimestamp(this.criterias.get(key).get(Date.class));
	}

}
