package com.nm.stats.jdbc;

import java.util.Comparator;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class MeasureComparator implements Comparator<String> {
	private final StatValuesProxy proxy;

	public MeasureComparator(StatValuesProxy proxy) {
		super();
		this.proxy = proxy;
	}

	public int compare(String o1, String o2) {
		try {
			StatMeasureCollection col1 = proxy.measure();
			col1 = (StatMeasureCollection) col1.hydrate(o1);
			//
			StatMeasureCollection col2 = proxy.measure();
			col2 = (StatMeasureCollection) col2.hydrate(o2); 
			return col1.compareTo(col2);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
	}
}
