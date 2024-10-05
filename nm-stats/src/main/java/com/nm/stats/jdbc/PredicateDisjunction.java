package com.nm.stats.jdbc;

import java.util.Collection;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PredicateDisjunction<T> implements java.util.function.Predicate<T> {
	private Collection<java.util.function.Predicate<T>> predicates = Lists.newArrayList();

	public boolean test(T t) {
		for (Predicate<T> p : predicates) {
			if (p.test(t)) {
				return true;
			}
		}
		return false;
	}

	public PredicateDisjunction<T> add(Predicate<T> p) {
		predicates.add(p);
		return this;
	}
}
