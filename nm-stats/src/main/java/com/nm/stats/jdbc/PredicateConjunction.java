package com.nm.stats.jdbc;

import java.util.Collection;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PredicateConjunction<T> implements java.util.function.Predicate<T> {
	private Collection<java.util.function.Predicate<T>> predicates = Lists.newArrayList();

	public boolean test(T t) {
		for (Predicate<T> p : predicates) {
			if (!p.test(t)) {
				return false;
			}
		}
		return true;
	}

	public PredicateConjunction<T> add(Predicate<T> p) {
		predicates.add(p);
		return this;
	}
}
