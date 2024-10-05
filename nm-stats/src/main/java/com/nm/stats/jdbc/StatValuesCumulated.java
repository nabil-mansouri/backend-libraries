package com.nm.stats.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nm.utils.MathUtilExt;

/**
 * 
 * @author Mansouri Nabil
 *
 */
public class StatValuesCumulated extends StatValuesAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Map<String, Number> count = new LinkedHashMap<String, Number>();
	private final StatValuesAbstract original;

	@Override
	public void clear() {
		count.clear();
		original.clear();
	}

	public StatValuesCumulated(StatValuesAbstract original) {
		super();
		this.original = original;
	}

	@Override
	public void putAndAdd(String format, Number nb) {
		nb = MathUtilExt.sum(this.getCount().getOrDefault(format, 0l), nb);
		this.getCount().put(format, nb);
	}

	public Map<String, Number> getCount() {
		return count;
	}

	@Override
	public Collection<String> getCategories() {
		return count.keySet();
	}

	public Number getCount(String key) {
		return count.getOrDefault(key, 0l);
	}

	public void compute(Comparator<String> comp) {
		this.original.compute(comp);
		List<String> categories = new ArrayList<String>(this.original.getCategories());
		for (int i = 0; i < categories.size(); i++) {
			String current = categories.get(i);
			if (i == 0) {
				Number value = this.original.getCount(current);
				this.count.put(current, value);
			} else {
				String previous = categories.get(i - 1);
				Number value = MathUtilExt.sum(this.getCount(previous), this.original.getCount(current));
				this.count.put(current, value);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatValueCumulated [");
		if (count != null) {
			builder.append("count=");
			builder.append(count);
			builder.append(", ");
		}
		if (original != null) {
			builder.append("original=");
			builder.append(original);
		}
		builder.append("]");
		return builder.toString();
	}

}
