package com.nm.stats.jdbc;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureStringTrimed extends StatMeasure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String string;

	public StatMeasureStringTrimed(ISelect select) {
		super(select);
	}

	@Override
	public String generate() {
		return (Strings.isNullOrEmpty(string)) ? "null" : string;
	}

	@Override
	public int compareTo(StatMeasure o) {
		StatMeasureStringTrimed d = (StatMeasureStringTrimed) o;
		return cp(string, d.string);
	}

	@Override
	public void hydrate(GenericJdbcRow row, ISelect select) {
		string = StringUtils.trim(row.getString(select));
	}

	@Override
	protected void _hydrate(String parse) throws Exception {
		this.string = StringUtils.trim((parse == null) ? null : (parse));
	}

	@Override
	public GenericJdbcRow getDimension() {
		return new GenericJdbcRow(select, string);
	}

	@Override
	public String toString() {
		return "StatMeasureString [string=" + string + "]";
	}
}