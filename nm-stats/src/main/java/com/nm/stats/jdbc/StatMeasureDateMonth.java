package com.nm.stats.jdbc;

import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureDateMonth extends StatMeasureDate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatMeasureDateMonth(ISelect select) {
		super(select);
	}

	protected String format() {
		return "MM-yyyy";
	}

	@Override
	public String toString() {
		return "StatMeasureDateMonth" + super.toString();
	}
}