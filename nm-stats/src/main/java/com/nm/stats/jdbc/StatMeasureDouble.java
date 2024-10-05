package com.nm.stats.jdbc;

import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureDouble extends StatMeasure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Double number;

	public StatMeasureDouble(ISelect select) {
		super(select);
	}

	@Override
	public String generate() {
		return (number != null) ? number.toString() : null;
	}

	@Override
	public int compareTo(StatMeasure o) {
		StatMeasureDouble d = (StatMeasureDouble) o;
		return cp(number, d.number);
	}

	@Override
	public void hydrate(GenericJdbcRow row, ISelect select) {
		number = row.getDouble(select);
	}

	@Override
	protected void _hydrate(String parse) throws Exception {
		this.number = (parse == null || parse.equals("null")) ? null : Double.valueOf(parse);
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