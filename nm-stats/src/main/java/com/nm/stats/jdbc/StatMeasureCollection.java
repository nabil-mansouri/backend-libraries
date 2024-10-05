package com.nm.stats.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class StatMeasureCollection extends StatMeasure {
	public StatMeasureCollection() {
		super(null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<ISelect, StatMeasure> mapping = Maps.newLinkedHashMap();

	public StatMeasureCollection with(Map<ISelect, StatMeasure> mapping) {
		this.mapping.putAll(mapping);
		return this;
	}

	public StatMeasureCollection with(ISelect p, StatMeasure s) {
		this.mapping.put(p, s);
		return this;
	}

	@Override
	public int compareTo(StatMeasure o) {
		StatMeasureCollection col = (StatMeasureCollection) o;
		CompareToBuilder comp = new CompareToBuilder();
		for (ISelect s : mapping.keySet()) {
			comp.append(this.mapping.get(s), col.mapping.get(s));
		}
		return comp.toComparison();
	}

	@Override
	public String generate() {
		List<String> strings = Lists.newArrayList();
		for (ISelect m : mapping.keySet()) {
			strings.add(mapping.get(m).generate());
		}
		return StringUtils.join(strings, ":");
	}

	@Override
	public void hydrate(GenericJdbcRow row, ISelect select) {
		StatMeasure m = mapping.get(select);
		m.hydrate(row, select);
	}

	@Override
	protected void _hydrate(String parse) throws Exception {
		List<String> strings = Lists.newArrayList(StringUtils.split(parse, ":"));
		int i = 0;
		for (ISelect m : mapping.keySet()) {
			if (strings.size() > i) {
				mapping.get(m)._hydrate(strings.get(i));
			}
			i++;
		}
	}

	@Override
	public GenericJdbcRow getDimension() {
		GenericJdbcRow m = new GenericJdbcRow();
		for (ISelect s : this.mapping.keySet()) {
			m.putAll(mapping.get(s).getDimension());
		}
		return m;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((generate() == null) ? 0 : generate().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatMeasureCollection other = (StatMeasureCollection) obj;
		if (generate() == null) {
			if (other.generate() != null)
				return false;
		} else if (!generate().equals(other.generate()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatMeasureCollection [mapping=" + mapping + "]";
	}
}