package com.nm.utils.jdbc;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class AbstractJdbcDaoTrialProxy {

	private AbstractJdbcDao inner = new AbstractJdbcDao() {
		public JdbcTemplate getJdbcTemplate() {
			return AbstractJdbcDaoTrialProxy.this.getJdbcTemplate();
		};
	};

	protected abstract long trialLimit();

	public final Collection<GenericJdbcRow> generic(final JdbcQueryBuilderSelect query, final Collection<ISelect> selects)
			throws JdbcDaoException {
		Collection<GenericJdbcRow> all = Lists.newArrayList();
		long offset = 0;
		Collection<GenericJdbcRow> temp = Lists.newArrayList();
		do {
			temp = inner.generic(query.withLimit(trialLimit()).withOffset(offset), selects);
			all.addAll(temp);
			offset += trialLimit();
		} while (!temp.isEmpty());
		return all;
	}

	public final void generic(final JdbcQueryBuilderSelect query, final GenericJdbcListener listener) throws JdbcDaoException {
		this.generic(query, listener, query.getSelects());
	}

	public final void generic(final JdbcQueryBuilderSelect query, final GenericJdbcListener listener, final Collection<ISelect> selects)
			throws JdbcDaoException {
		final AtomicBoolean b = new AtomicBoolean(true);
		long offset = 0;
		do {
			inner.generic(query.withLimit(trialLimit()).withOffset(offset), new GenericJdbcListener() {

				public void onRow(GenericJdbcRow row) {
					b.set(true);
					listener.onRow(row);
				}
			}, selects);
			offset += trialLimit();
		} while (b.get());
	}

	public final ResultSet toCsv(final JdbcQueryBuilderSelect query, final OutputStream out) throws JdbcDaoException {
		try {
			final AtomicBoolean b = new AtomicBoolean(true);
			long offset = 0;
			ResultSet rs = null;
			do {
				rs = inner.toCsv(query.withLimit(trialLimit()).withOffset(offset).build(), out);
				offset += trialLimit();
			} while (b.get());
			return rs;
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public abstract JdbcTemplate getJdbcTemplate();

}
