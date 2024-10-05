package com.nm.stats.jdbc;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.utils.MathUtilExt;

/**
 * 
 * @author Mansouri Nabil
 *
 */
public class StatValuesSum extends StatValuesAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Map<String, Number> count = new LinkedHashMap<String, Number>();
	private final StatValuesAbstract original1;
	private final StatValuesAbstract original2;

	public StatValuesSum(StatValuesAbstract original1, StatValuesAbstract original2) {
		super();
		this.original1 = original1;
		this.original2 = original2;
	}

	@Override
	public void clear() {
		this.count.clear();
		this.original1.clear();
		this.original2.clear();
	}

	@Override
	public void putAndAdd(String format, Number nb) {
		throw new IllegalAccessError("Could not putAndAdd for class:" + getClass());
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
		this.original1.compute(comp);
		this.original2.compute(comp);
		Set<String> categories = Sets.newConcurrentHashSet();
		categories.addAll(this.original1.getCategories());
		categories.addAll(this.original2.getCategories());
		List<String> categoriesL = Lists.newArrayList(categories);
		Collections.sort(categoriesL, comp);
		for (String c : categoriesL) {
			this.count.put(c, MathUtilExt.sum(this.original1.getCount(c), this.original2.getCount(c)));
		}
	}

	@Override
	public String toString() {
		return "StatValuesSum [count=" + count + ", original1=" + original1 + ", original2=" + original2 + "]";
	}

}
