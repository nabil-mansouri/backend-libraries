package com.nm.utils.jdbc;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class AbstractJdbcDao {
	public static interface Idable {
		public Long getId();
	}

	public static interface IdableString {
		public String getId();
	}

	public final Collection<GenericJdbcRow> generic(final JdbcQueryBuilderSelect query) throws JdbcDaoException {
		return generic(query, query.getSelects());
	}

	public static <T extends Idable> Map<Long, T> findAllById(Collection<T> all) {
		Map<Long, T> map = Maps.newHashMap();
		for (T a : all) {
			map.put(a.getId(), a);
		}
		return map;
	}

	public final <T> Collection<T> generic(final JdbcQueryBuilderSelect query, RowMapper<T> mapper) throws JdbcDaoException {
		try {
			return getJdbcTemplate().query(query.buildStatement(), mapper);
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public static <T extends Idable> Collection<Long> getIds(Collection<T> all) {
		Collection<Long> map = Sets.newConcurrentHashSet();
		for (T a : all) {
			map.add(a.getId());
		}
		return map;
	}

	public static <T extends IdableString> Map<String, T> findAllByIdString(Collection<T> all) {
		Map<String, T> map = Maps.newHashMap();
		for (T a : all) {
			map.put(a.getId(), a);
		}
		return map;
	}

	public int update(JdbcQueryBuilderUpdate update) throws JdbcDaoException {
		try {
			return getJdbcTemplate().update(update.buildStatement());
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public static void convertToCsv(ResultSet rs, OutputStream file) throws Exception {
		ResultSetMetaData meta = rs.getMetaData();
		int numberOfColumns = meta.getColumnCount();
		Collection<String> headers = Lists.newArrayList();
		for (int i = 1; i <= numberOfColumns; i++) {
			headers.add(meta.getColumnName(i));
		}
		IOUtils.write(StringUtils.join(headers, ";") + ";\n", file);
		while (rs.next()) {
			Collection<String> row = Lists.newArrayList();
			for (int i = 1; i <= numberOfColumns; i++) {
				row.add(rs.getString(i));
			}
			IOUtils.write(StringUtils.join(row, ";") + ";\n", file);
		}
	}

	protected int fetchSize() {
		return 10000;
	}

	public final Collection<GenericJdbcRow> generic(final JdbcQueryBuilderSelect query, final Collection<ISelect> selects)
			throws JdbcDaoException {
		try {
			final Collection<GenericJdbcRow> map = new ArrayList<GenericJdbcRow>();
			return getJdbcTemplate().query(query.buildStatement(fetchSize()), new ResultSetExtractor<Collection<GenericJdbcRow>>() {
				public Collection<GenericJdbcRow> extractData(ResultSet rs) throws SQLException, DataAccessException {
					System.out.println(rs.getFetchSize());
					while (rs.next()) {
						GenericJdbcRow m = new GenericJdbcRow();
						map.add(m);
						for (ISelect s : selects) {
							Object nb = rs.getObject(query.getSelectAlias(s));
							m.put(s, nb);
						}
					}
					return map;
				}
			});
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public final void generic(final JdbcQueryBuilderSelect query, final GenericJdbcListener listener) throws JdbcDaoException {
		generic(query, listener, query.getSelects());
	}

	public final void generic(final JdbcQueryBuilderSelect query, final GenericJdbcListener listener, final Collection<ISelect> selects)
			throws JdbcDaoException {
		try {
			getJdbcTemplate().query(query.buildStatement(), new ResultSetExtractor<Void>() {
				public Void extractData(ResultSet rs) throws SQLException, DataAccessException {
					while (rs.next()) {
						GenericJdbcRow m = new GenericJdbcRow();
						for (ISelect s : selects) {
							Object nb = rs.getObject(query.getSelectAlias(s));
							m.put(s, nb);
						}
						listener.onRow(m);
					}
					return null;
				}
			});
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public final ResultSet toCsv(final String query, final OutputStream out) throws JdbcDaoException {
		try {
			return getJdbcTemplate().query(query, new ResultSetExtractor<ResultSet>() {
				public ResultSet extractData(ResultSet rs) throws SQLException, DataAccessException {
					try {
						convertToCsv(rs, out);
					} catch (Exception e) {
						throw new SQLException(e);
					}
					return rs;
				}
			});
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public void toCsv(JdbcQueryBuilderSelect query, FileOutputStream out) throws JdbcDaoException {
		try {
			toCsv(query.build(), out);
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public abstract JdbcTemplate getJdbcTemplate();

}
