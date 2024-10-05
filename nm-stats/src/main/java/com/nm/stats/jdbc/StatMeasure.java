package com.nm.stats.jdbc;

import java.io.Serializable;

import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class StatMeasure implements Serializable, Comparable<StatMeasure>, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ISelect select;

	public ISelect getSelect() {
		return select;
	}

	@SuppressWarnings("unused")
	private StatMeasure() {
		super();
	}

	static <T extends Comparable<T>> int cp(T a, T b) {
		return a == null ? (b == null ? 0 : Integer.MIN_VALUE) : (b == null ? Integer.MAX_VALUE : a.compareTo(b));
	}

	public StatMeasure(ISelect select) {
		super();
		this.select = select;
	}

	public abstract int compareTo(StatMeasure o);

	public abstract String generate();

	public abstract GenericJdbcRow getDimension();

	public abstract void hydrate(GenericJdbcRow row, ISelect select);

	protected abstract void _hydrate(String parse) throws Exception;

	public StatMeasure hydrate(String value) throws Exception {
		StatMeasure clone = makeClone();
		clone._hydrate(value);
		return clone;
	}
	//BETTER PERFORMANCE LIKE THIS
	public StatMeasure makeClone() throws CloneNotSupportedException {
		return (StatMeasure) super.clone();
	}

}
