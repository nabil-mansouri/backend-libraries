package com.nm.utils.jdbc;

import java.util.Date;
import java.util.LinkedHashMap;

import com.google.common.base.Enums;
import com.nm.utils.MathUtilExt;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class GenericJdbcRow extends LinkedHashMap<ISelect, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenericJdbcRow() {
	}

	public GenericJdbcRow(ISelect select, Object o) {
		put(select, o);
	}

	public boolean containsKey(ISelect key) {
		return super.containsKey(key);
	}

	public Date getDate(ISelect select) {
		return getDate(select, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(ISelect key, Class<T> clazz) {
		return (T) get(key);
	}

	public Date getDate(ISelect select, Date defaut) {
		if (this.get(select) != null) {
			return (Date) (this.get(select));
		}
		return defaut;
	}

	public Double getDouble(ISelect select) {
		return getDouble(select, null);
	}

	public Double getDouble(ISelect select, Double defaut) {
		if (this.get(select) != null) {
			return Double.valueOf(this.get(select).toString());
		}
		return defaut;
	}

	public Number getNumber(ISelect select) {
		return getNumber(select, null);
	}

	public Number getNumber(ISelect select, Number defaut) {
		if (this.get(select) != null) {
			return Double.valueOf(this.get(select).toString());
		}
		return defaut;
	}

	public Long getLong(ISelect select) {
		return getLong(select, null);
	}

	public Long getLong(ISelect select, Long defaut) {
		if (this.get(select) != null) {
			return MathUtilExt.toLong(this.get(select).toString());
		}
		return defaut;
	}

	public String getString(ISelect select) {
		return getString(select, null);
	}

	public String getString(ISelect select, String defaut) {
		if (this.get(select) != null) {
			return this.get(select).toString();
		}
		return defaut;
	}

	public <T extends Enum<T>> Enum<T> getEnum(ISelect select, Class<T> clazz) {
		return getEnum(select, clazz, null);
	}

	public <T extends Enum<T>> Enum<T> getEnum(ISelect select, Class<T> clazz, Enum<T> defaut) {
		String value = getString(select);
		if (value != null && Enums.getIfPresent(clazz, value).isPresent()) {
			return Enums.getIfPresent(clazz, value).get();
		}
		return defaut;
	}

	public void merge(GenericJdbcRow dimension) {
		this.putAll(dimension);
	}
}
