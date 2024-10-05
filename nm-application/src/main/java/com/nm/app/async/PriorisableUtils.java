package com.nm.app.async;

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
public class PriorisableUtils {

	public static <T extends Priorisable> List<T> withDep(Collection<T> p) {
		List<T> copy = new ArrayList<T>();
		for (T sp : p) {
			if (sp.hasDependencies()) {
				copy.add(sp);
			}
		}
		return copy;
	}

	public static <T extends Priorisable> List<T> withoutDep(Collection<T> p) {
		List<T> copy = new ArrayList<T>();
		for (T sp : p) {
			if (!sp.hasDependencies()) {
				copy.add(sp);
			}
		}
		return copy;
	}

	public static <T extends Priorisable> List<T> sortAsc(Collection<T> p) {
		List<T> copy = new ArrayList<T>(p);
		Collections.sort(copy, new Comparator<T>() {

			public int compare(T o1, T o2) {
				return new Integer(o1.priority()).compareTo(new Integer(o2.priority()));
			}
		});
		return copy;
	}

	public static <T extends Priorisable> List<T> sortDesc(Collection<T> p) {
		List<T> copy = new ArrayList<T>(p);
		Collections.sort(copy, new Comparator<T>() {

			public int compare(T o1, T o2) {
				return -(new Integer(o1.priority()).compareTo(new Integer(o2.priority())));
			}
		});
		return copy;
	}

}
