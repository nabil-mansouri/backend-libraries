package com.nm.stats.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
public class StatValuesDefault extends StatValuesAbstract implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Map<String, Number> count = new LinkedHashMap<String, Number>();
	private Map<String, Number> countPercent = new LinkedHashMap<String, Number>();
	private List<String> categories = new ArrayList<String>();
	private Number total = 0l;
	private Number last = 0l;
	private Number mean = 0l;
	private Number max = null;
	private Number min = null;

	@Override
	public void clear() {
		count.clear();
		countPercent.clear();
		categories.clear();
		total = 0l;
		last = 0l;
		mean = 0l;
		max = null;
		min = null;
	}

	public Map<String, Number> getCountPercent() {
		return countPercent;
	}

	public void setCountPercent(Map<String, Number> countPercent) {
		this.countPercent = countPercent;
	}

	public Collection<String> getCategories() {
		return categories;
	}

	public Map<String, Number> getCount() {
		return count;
	}

	public Number getCount(String key) {
		return count.getOrDefault(key, 0l);
	}

	public Number getOrCreateCount(String key, Number val) {
		if (count.containsKey(key)) {
			return count.get(key);
		} else {
			count.put(key, val);
			return val;
		}
	}

	public Number getCountPercent(String key) {
		return countPercent.getOrDefault(key, 0d);
	}

	public Number getOrDefaultCount(String key, Number defaultValue) {
		return count.getOrDefault(key, defaultValue);
	}

	public Number getLast() {
		return last;
	}

	public Number getMean() {
		return mean;
	}

	public Number getTotal() {
		return total;
	}

	public void put(String format, Number nb) {
		this.last = nb;
		this.categories.add(format);
		this.getCount().put(format, nb);
	}

	public void putAndAdd(String format, Number nb) {
		nb = MathUtilExt.sum(this.getCount().getOrDefault(format, 0l), nb);
		//
		this.last = nb;
		if (categories.contains(format)) {
			this.getCount().put(format, nb);
		} else {
			this.categories.add(format);
			this.getCount().put(format, nb);
		}
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setCount(Map<String, Number> count) {
		this.count = count;
	}

	public void setLast(Number last) {
		this.last = last;
	}

	public void setMean(Number mean) {
		this.mean = mean;
	}

	public void setTotal(Number total) {
		this.total = total;
	}

	public Number getMax() {
		return max;
	}

	public Number getMin() {
		return min;
	}

	public void compute(Comparator<String> comp) {
		this.total = 0l;
		this.total = MathUtilExt.sumN(this.count.values());
		this.mean = MathUtilExt.divide(this.total, this.categories.size());
		this.max = MathUtilExt.maxN(this.count.values());
		this.min = MathUtilExt.minN(this.count.values());
		this.countPercent.clear();
		for (String key : this.count.keySet()) {
			this.countPercent.put(key, MathUtilExt.divide(this.count.get(key), this.total));
		}
		if (comp != null) {
			Collections.sort(categories, comp);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatResultNumberByCategoryDto [");
		if (count != null) {
			builder.append("count=");
			builder.append(count);
			builder.append(", ");
		}
		if (categories != null) {
			builder.append("categories=");
			builder.append(categories);
			builder.append(", ");
		}
		if (total != null) {
			builder.append("total=");
			builder.append(total);
			builder.append(", ");
		}
		if (last != null) {
			builder.append("last=");
			builder.append(last);
			builder.append(", ");
		}
		if (mean != null) {
			builder.append("mean=");
			builder.append(mean);
			builder.append(", ");
		}
		builder.append("]\n");
		return builder.toString();
	}

}
