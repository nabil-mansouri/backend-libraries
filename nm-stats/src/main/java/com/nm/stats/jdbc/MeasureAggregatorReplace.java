package com.nm.stats.jdbc;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class MeasureAggregatorReplace implements MeasureAggregator {
	private Collection<ISelect> selects = Lists.newArrayList();

	public MeasureAggregatorReplace(ISelect s) {
		selects.add(s);
	}

	public MeasureAggregatorReplace(Collection<ISelect> s) {
		selects.addAll(s);
	}

	public Map<ISelect, StatMeasure> mapping(StatValuesProxy row) {
		Map<ISelect, StatMeasure> copy = Maps.newLinkedHashMap(row.mapping);
		copy.keySet().retainAll(selects);
		return copy;
	}

	public void refresh(GenericJdbcRow row) {
		for (ISelect s : selects) {
			replace(s, row);
		}
	}

	protected abstract void replace(ISelect select, GenericJdbcRow row);
}
