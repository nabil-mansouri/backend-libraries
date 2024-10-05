package com.nm.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * 
 * @author Nabil
 * 
 */
public class ListUtils {
	@SafeVarargs
	public static <T, T1 extends T> List<T> build(T1... e) {
		List<T> l = Lists.newArrayList();
		for (T ee : e) {
			l.add(ee);
		}
		return l;
	}
	public static <V, K> Multimap<V, K> invertToMulti(Map<K, V> map) {
		Multimap<V, K> inv = LinkedListMultimap.create();
		for (Entry<K, V> entry : map.entrySet()) {
			inv.put(entry.getValue(), entry.getKey());
		}
		return inv;
	}

	public static <TCastTo, TCastFrom extends TCastTo> List<TCastTo> convert(final List<TCastFrom> list) {
		return new ArrayList<TCastTo>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public TCastTo get(int i) {
				return list.get(i);
			}

			@Override
			public int size() {
				return list.size();
			}
		};
	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<Class<? extends T>> all(Class<?>... all) {
		Collection<Class<? extends T>> a = Lists.newArrayList();
		for (Class<?> c : all) {
			a.add((Class<? extends T>) c);
		}
		return a;
	}

	public static <TCastTo, TCastFrom> Collection<TCastTo> cast(final TCastFrom[] values) {
		return new ArrayList<TCastTo>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public TCastTo get(int i) {
				return (TCastTo) values[i];
			}

			@Override
			public int size() {
				return values.length;
			}
		};
	}

	public static <TCastTo, TCastFrom> Collection<TCastTo> cast(final Collection<TCastFrom> list) {
		final List<TCastFrom> from = Lists.newArrayList(list);
		return new ArrayList<TCastTo>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public TCastTo get(int i) {
				return (TCastTo) from.get(i);
			}

			@Override
			public int size() {
				return list.size();
			}
		};
	}

	public static <TCastTo, TCastFrom> List<TCastTo> cast(final List<TCastFrom> list) {
		return new ArrayList<TCastTo>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public TCastTo get(int i) {
				return (TCastTo) list.get(i);
			}

			@Override
			public int size() {
				return list.size();
			}
		};
	}

	public static List<List<Object>> cartesianProduct(List<?>... sets) {
		if (sets.length < 2)
			throw new IllegalArgumentException("Can't have a product of fewer than two sets (got " + sets.length + ")");

		return _cartesianProduct(0, sets);
	}

	private static List<List<Object>> _cartesianProduct(int index, List<?>... sets) {
		List<List<Object>> ret = new ArrayList<List<Object>>();
		if (index == sets.length) {
			ret.add(new ArrayList<Object>());
		} else {
			for (Object obj : sets[index]) {
				for (List<Object> set : _cartesianProduct(index + 1, sets)) {
					set.add(obj);
					ret.add(set);
				}
			}
		}
		return ret;
	}

}
