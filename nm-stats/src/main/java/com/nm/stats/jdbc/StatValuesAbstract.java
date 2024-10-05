package com.nm.stats.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class StatValuesAbstract implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatValuesAbstract() {
		super();
	}

	@SuppressWarnings("unchecked")
	public final <T extends StatValuesAbstract> T get() {
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final <T extends StatValuesAbstract> T get(Class<T> clazz) {
		return (T) this;
	}

	public final List<String> getCategoriesSortedByCount() {
		List<String> categoriesSortedByCount = new ArrayList<String>(getCategories());
		Collections.sort(categoriesSortedByCount, new Comparator<String>() {

			public int compare(String o1, String o2) {
				Number v1 = getCount(o1);
				Number v2 = getCount(o2);
				return -new Double(v1.doubleValue()).compareTo(v2.doubleValue());
			}
		});
		return categoriesSortedByCount;
	}

	public final void setCategoriesSortedByCount(List<String> s) {
	}

	public StatValuesAbstract toCumulate() {
		throw new IllegalAccessError("Could not convert to accumulate instance ...." + getClass());
	}

	public StatValuesAbstract sumWith(StatValuesAbstract arg) {
		throw new IllegalAccessError("Could not convert to sum instance ...." + getClass());
	}

	public StatValuesAbstract diffWith(StatValuesAbstract arg) {
		throw new IllegalAccessError("Could not convert to diff instance ...." + getClass());
	}

	public abstract void clear();

	public abstract void compute(Comparator<String> comp);

	public abstract Collection<String> getCategories();

	public abstract Number getCount(String generate);

	public abstract void putAndAdd(String generate, Number count);

	public StatValuesAbstract makeClone() {
		try {
			return (StatValuesAbstract) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException(e);
		}
	}
}