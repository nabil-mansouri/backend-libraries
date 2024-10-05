package com.nm.app.utils;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class MathUtil {
	public static boolean isNullOrZero(Double d1) {
		return d1 == null || d1 == 0 || d1 == 0d;
	}

	public static Double sub(String d1, String d2) {
		d1 = StringUtils.replace(StringUtils.replace(d1, "%", ""), ",", ".");
		d2 = StringUtils.replace(StringUtils.replace(d2, "%", ""), ",", ".");
		return NumberUtils.toDouble(d1) - NumberUtils.toDouble(d2);
	}

	public static boolean isNullOrZero(Long d1) {
		return d1 == null || d1 == 0 || d1 == 0l;
	}

	public static boolean isNullOrZero(Integer d1) {
		return d1 == null || d1 == 0 || d1 == 0d;
	}

	public static Double sub(Double... all) {
		Double total = 0d;
		for (Double d : all) {
			total -= (d == null) ? 0 : d;
		}
		return total;
	}

	public static Double sum(Double... all) {
		Double total = 0d;
		for (Double d : all) {
			total += (d == null) ? 0 : d;
		}
		return total;
	}

	public static Double sum(Long all1, Double... all) {
		Double total = (all1 == null) ? 0d : all1;
		for (Double d : all) {
			total += (d == null) ? 0 : d;
		}
		return total;
	}

	public static Double sum(Double all1, Long... all) {
		Double total = (all1 == null) ? 0d : all1;
		for (Long d : all) {
			total += (d == null) ? 0 : d;
		}
		return total;
	}

	public static Long sum(Long... all) {
		Long total = 0l;
		for (Long d : all) {
			total += (d == null) ? 0 : d;
		}
		return total;
	}

	public static Integer sum(Integer... all) {
		Integer total = 0;
		for (Integer d : all) {
			total += (d == null) ? 0 : d;
		}
		return total;
	}

	public static Double divide(Double d1, Double d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		}
		return d1 / d2;
	}

	public static Double mult(Double... all) {
		Double total = 0d;
		for (Double d : all) {
			total *= (d == null) ? 0 : d;
		}
		return total;
	}

	public static Double mult(Double d1, Double d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		}
		return d1 * d2;
	}

	public static Double mult(Integer d1, Double d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		}
		return d1 * d2;
	}

	public static interface Fetcher<T, T2> {
		public T2 fetch(T obj);
	}

	public static <T> Double sum(Collection<T> objects, Fetcher<T, Double> fetcher) {
		Double total = 0d;
		for (T o : objects) {
			total = sum(total, fetcher.fetch(o));
		}
		return total;
	}

	public static <T> Integer sumi(Collection<T> objects, Fetcher<T, Integer> fetcher) {
		Integer total = 0;
		for (T o : objects) {
			total = sum(total, fetcher.fetch(o));
		}
		return total;
	}

	public static Double mean(Double... all) {
		if (all.length == 0) {
			return 0d;
		}
		Double total = 0d;
		for (Double d : all) {
			total = sum(total, d);
		}
		return total / all.length;
	}

	public static <T> Double mean(Collection<T> objects, Fetcher<T, Double> fetcher) {
		if (objects.isEmpty()) {
			return 0d;
		}
		Double total = 0d;
		for (T o : objects) {
			total = sum(total, fetcher.fetch(o));
		}
		return total / objects.size();
	}

	public static <T> Double meanExc(Collection<T> objects, Fetcher<T, Double> fetcher) {
		if (objects.isEmpty()) {
			return 0d;
		}
		Double total = 0d;
		int cpt = 0;
		for (T o : objects) {
			total = sum(total, fetcher.fetch(o));
			if (!isNullOrZero(fetcher.fetch(o))) {
				cpt++;
			}
		}
		return total / cpt;
	}

	public static Double safeDivide(Double d1, Double d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		} else {
			return d1 / d2;
		}
	}

	public static Double safeDivide(Double d1, Integer d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		} else {
			return d1 / d2;
		}
	}

	public static <T> Double divide(Collection<T> objects1, Fetcher<T, Double> fetcher1, Collection<T> objects2,
			Fetcher<T, Double> fetcher2) {
		Double d1 = MathUtil.sum(objects1, fetcher1);
		Double d2 = MathUtil.sum(objects2, fetcher2);
		return safeDivide(d1, d2);
	}

	public static <T> Double dividei(Collection<T> objects1, Fetcher<T, Double> fetcher1, Collection<T> objects2,
			Fetcher<T, Integer> fetcher2) {
		Double d1 = MathUtil.sum(objects1, fetcher1);
		Integer d2 = MathUtil.sumi(objects2, fetcher2);
		return safeDivide(d1, d2);
	}

	public static int random_int(int Min, int Max) {
		return (int) (Math.random() * (Max - Min)) + Min;
	}

	public static double random(int Min, int Max) {
		return (Math.random() * (Max - Min)) + Min;
	}
}
