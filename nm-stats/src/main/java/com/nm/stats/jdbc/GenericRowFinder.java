package com.nm.stats.jdbc;

import java.util.Date;

import com.nm.stats.jdbc.keys.GenericJdbcRowList;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class GenericRowFinder {

	public GenericRowFinder() {
		super();
	}

	public static GenericJdbcRow find(ISelect select, Date limit, GenericJdbcRowList list, boolean inclusiv) {
		Date reference = null;
		GenericJdbcRow founded = null;
		for (GenericJdbcRow row : list) {
			Date value = row.getDate(select);
			if (value != null && (value.before(limit) || (value.equals(limit) && inclusiv))) {
				if (reference == null) {
					reference = value;
					founded = row;
				} else if (value.after(reference)) {
					reference = value;
					founded = row;
				}
			}
		}
		return founded;
	}

}
