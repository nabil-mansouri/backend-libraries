package com.nm.utils.graphs.finder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author nabilmansouri
 *
 */
public class GraphFinderPathList<T> {

	private Collection<GraphFinderPath<T>> all = new ArrayList<GraphFinderPath<T>>();

	public Collection<GraphFinderPath<T>> getAll() {
		return all;
	}

	public int size() {
		return this.all.size();
	}

	public void setAll(Collection<GraphFinderPath<T>> all) {
		this.all = all;
	}

	public Collection<T> result() {
		Collection<T> a = new ArrayList<T>();
		for (GraphFinderPath<T> p : this.all) {
			a.add(p.getFounded());
		}
		return a;
	}

	public boolean founded() {
		return !this.all.isEmpty();
	}

	public GraphFinderPath<T> create() {
		GraphFinderPath<T> c = new GraphFinderPath<T>();
		all.add(c);
		return c;
	}
}
