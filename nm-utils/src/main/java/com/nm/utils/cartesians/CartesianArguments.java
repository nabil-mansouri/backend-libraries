package com.nm.utils.cartesians;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CartesianArguments<T> {
	private List<List<T>> values = new ArrayList<List<T>>();

	public List<List<T>> getValues() {
		return values;
	}

	public void setValues(List<List<T>> values) {
		this.values = values;
	}

	public int size() {
		return values.size();
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	public boolean add(List<T> e) {
		return values.add(e);
	}

	public boolean addAll(List<List<T>> c) {
		return values.addAll(c);
	}

	public boolean addAll(Collection<? extends List<T>> c) {
		return values.addAll(c);
	}

	public void clear() {
		values.clear();
	}

}
