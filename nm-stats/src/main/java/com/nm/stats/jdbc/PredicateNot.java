package com.nm.stats.jdbc;

import java.util.function.Predicate;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PredicateNot<T> implements java.util.function.Predicate<T> {
	private java.util.function.Predicate<T> predicate;

	public PredicateNot(Predicate<T> p) {
		this.predicate = p;
	}

	public boolean test(T t) {
		return !predicate.test(t);
	}

}
