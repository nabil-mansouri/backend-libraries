package com.nm.stats.jdbc;

import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureLong extends StatMeasure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long number;

	public StatMeasureLong(ISelect select) {
		super(select);
	}

	@Override
	public String generate() {
		return (number != null) ? number.toString() : "null";
	}

	@Override
	public int compareTo(StatMeasure o) {
		StatMeasureLong d = (StatMeasureLong) o;
		return cp(number, d.number);
	}

	@Override
	public void hydrate(GenericJdbcRow row, ISelect select) {
		number = row.getLong(select);
	}

	@Override
	protected void _hydrate(String parse) throws Exception {
		// SAFER to parse double
		this.number = (parse == null || parse.equals("null")) ? null : Double.valueOf(parse).longValue();
	}

	@Override
	public GenericJdbcRow getDimension() {
		return new GenericJdbcRow(select, number);
	}

	@Override
	public String toString() {
		return "StatMeasureLong [number=" + number + "]";
	}
}