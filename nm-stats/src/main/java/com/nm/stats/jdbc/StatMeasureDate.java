package com.nm.stats.jdbc;

import java.util.Date;

import com.nm.utils.dates.DateUtilsExt;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureDate extends StatMeasure {
	public StatMeasureDate(ISelect select) {
		super(select);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Date date;

	@Override
	public String generate() {
		return (date != null) ? DateUtilsExt.format(date, format()) : "null";
	}

	@Override
	public void hydrate(GenericJdbcRow row, ISelect select) {
		date = row.getDate(select);
	}

	@Override
	public int compareTo(StatMeasure o) {
		StatMeasureDate d = (StatMeasureDate) o;
		return cp(date, d.date);
	}

	@Override
	protected void _hydrate(String parse) throws Exception {
		this.date = (parse == null || parse.equals("null")) ? null : com.nm.utils.dates.DateUtilsExt.parse(parse, format());
	}

	@Override
	public GenericJdbcRow getDimension() {
		return new GenericJdbcRow(select, date);
	}

	protected String format() {
		return "dd-MM-yyyy";
	}

	@Override
	public String toString() {
		return "StatMeasureDate [date=" + date + "]";
	}
}