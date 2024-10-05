package com.nm.stats.jdbc;

import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureDateYear extends StatMeasureDate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatMeasureDateYear(ISelect select) {
		super(select);
	}

	protected String format() {
		return "yyyy";
	}

	@Override
	public String toString() {
		return "StatMeasureDateYear" + super.toString();
	}
}