package com.nm.stats.jdbc;

import java.util.Map;

import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/***
 * 
 * @author Nabil MANSOURI
 *
 */
public interface MeasureAggregator {
	public Map<ISelect, StatMeasure> mapping(StatValuesProxy row);

	public void refresh(GenericJdbcRow row);
}
