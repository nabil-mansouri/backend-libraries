package com.nm.stats.jdbc;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.utils.jdbc.GenericJdbcRow;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PredicateMulti<T> implements java.util.function.Predicate<T> {
	private Map<PredicateUniqueId<T>, List<T>> predicates = Maps.newLinkedHashMap();

	public boolean test(T t) {
		for (Predicate<T> p : predicates.keySet()) {
			if (p.test(t)) {
				predicates.get(p).add(t);
			}
		}
		return false;
	}

	public PredicateUniqueId<T> add(Predicate<T> p, String uuid) {
		List<T> list = Lists.newArrayList();
		PredicateUniqueId<T> pp = new PredicateUniqueId<T>(p, uuid);
		predicates.put(pp, list);
		return pp;
	}

	public PredicateUniqueId<T> add(Predicate<T> p) {
		List<T> list = Lists.newArrayList();
		PredicateUniqueId<T> pp = new PredicateUniqueId<T>(p);
		predicates.put(pp, list);
		return pp;
	}

	public Set<PredicateUniqueId<T>> keySet() {
		return predicates.keySet();
	}

	public Collection<?> get(PredicateUniqueId<GenericJdbcRow> key) {
		return this.predicates.get(key);
	}

}
