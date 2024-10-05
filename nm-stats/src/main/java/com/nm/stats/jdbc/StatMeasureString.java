package com.nm.stats.jdbc;

import com.google.common.base.Strings;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureString extends StatMeasure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String string;

	public StatMeasureString(ISelect select) {
		super(select);
	}

	@Override
	public String generate() {
		return (Strings.isNullOrEmpty(string)) ? "null" : string;
	}

	@Override
	public int compareTo(StatMeasure o) {
		StatMeasureString d = (StatMeasureString) o;
		return cp(string, d.string);
	}

	@Override
	public void hydrate(GenericJdbcRow row, ISelect select) {
		string = row.getString(select);
	}

	@Override
	protected void _hydrate(String parse) throws Exception {
		this.string = (parse == null) ? null : (parse);
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