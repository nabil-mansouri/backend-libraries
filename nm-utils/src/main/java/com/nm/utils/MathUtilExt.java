package com.nm.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class MathUtilExt {
	public static Integer toInt(Number n) {
		return (n == null) ? null : n.intValue();
	}

	public static Long toLong(Number n) {
		return (n == null) ? null : n.longValue();
	}

	public static boolean equals(Number n1, Number n2) {
		n1 = (n1 == null) ? 0 : n1;
		n2 = (n2 == null) ? 0 : n2;
		return n1.doubleValue() == n2.doubleValue();
	}

	public static interface Fetcher<T, T2> {
		public T2 fetch(T obj);
	}

	public static Set<Long> intToLong(Set<Integer> in) {
		Set<Long> s = Sets.newHashSet();
		for (Integer i : in) {
			if (i == null) {
				s.add(null);
			} else {
				s.add(i.longValue());
			}
		}
		return s;
	}

	public static boolean between(Long from, Long to, Long value) {
		if (from == null) {
			return value < to;
		} else if (to == null) {
			return from <= value;
		} else {
			return from <= value && value < to;
		}
	}

	public static boolean between(Long outterFrom, Long outterTo, Long innerFrom, Long innerTo) {
		if (innerFrom == null) {
			return between(outterFrom, outterTo, innerTo);
		} else if (innerTo == null) {
			return between(outterFrom, outterTo, innerFrom);
		} else {
			return (outterFrom.equals(innerFrom) && outterTo.equals(innerTo))
					|| (between(outterFrom, outterTo, innerFrom) && between(outterFrom, outterTo, innerTo));
		}
	}

	public static boolean betweenEq(Long from, Long to, Long value) {
		if (from == null) {
			return value <= to;
		} else if (to == null) {
			return from <= value;
		} else {
			return from <= value && value <= to;
		}
	}

	public static boolean betweenEq(Long outterFrom, Long outterTo, Long innerFrom, Long innerTo) {
		if (innerFrom == null) {
			return betweenEq(outterFrom, outterTo, innerTo);
		} else if (innerTo == null) {
			return betweenEq(outterFrom, outterTo, innerFrom);
		} else {
			return betweenEq(outterFrom, outterTo, innerFrom) && betweenEq(outterFrom, outterTo, innerTo);
		}
	}

	public static <T> Double divide(Collection<T> objects1, Fetcher<T, Double> fetcher1, Collection<T> objects2,
			Fetcher<T, Double> fetcher2) {
		Double d1 = MathUtilExt.sum(objects1, fetcher1);
		Double d2 = MathUtilExt.sum(objects2, fetcher2);
		return safeDivide(d1, d2);
	}

	public static Double divide(Double d1, Double d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		}
		return d1 / d2;
	}

	public static Long divide(Long d1, int d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0l;
		}
		return Math.round(d1 * 1d / d2);
	}

	public static Double divide(Long d1, Long d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		}
		return (d1 * 1d) / (d2 * 1d);
	}

	public static Number divide(Number d1, Integer d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		}
		return (d1.doubleValue()) / d2;
	}

	public static Number divide(Number d1, Number d2) {
		if (isNullOrZero(d1) || isNullOrZero(d2)) {
			return 0d;
		}
		return (d1.doubleValue()) / d2.doubleValue();
	}

	public static <T> Double dividei(Collection<T> objects1, Fetcher<T, Double> fetcher1, Collection<T> objects2,
			Fetcher<T, Integer> fetcher2) {
		Double d1 = MathUtilExt.sum(objects1, fetcher1);
		Integer d2 = MathUtilExt.sumi(objects2, fetcher2);
		return safeDivide(d1, d2);
	}

	public static Double evolution(Double before, Double after) {
		Double sub = sub(after, before) * 1d;
		return divide(sub, (before));
	}

	public static Double evolution(Long before, Long after) {
		Double sub = sub(after, before) * 1d;
		return divide(sub, toDouble(before));
	}

	public static Double evolutionAbs(Double before, Double after) {
		Double sub = sub(after, before);
		return sub;
	}

	public static Long evolutionAbs(Long before, Long after) {
		Long sub = sub(after, before);
		return sub;
	}

	public static MathPair<Long, Long> findRange(List<MathPair<Long, Long>> list, Long value) {
		for (MathPair<Long, Long> pair : list) {
			Long from = pair.getL();
			Long to = pair.getR();
			if (between(from, to, value)) {
				return new MathPair<Long, Long>(from, to);
			}
		}
		return null;
	}

	public static List<Integer> generateInt(Integer min, Integer max) {
		List<Integer> all = Lists.newArrayList();
		for (int i = min; i <= max; i++) {
			all.add(i);
		}
		return all;
	}

	public static List<Long> generateLong(Long min, Long max) {
		List<Long> all = Lists.newArrayList();
		for (Long i = min; i <= max; i++) {
			all.add(i);
		}
		return all;
	}

	public static boolean isNullOrZero(Double d1) {
		return d1 == null || d1.doubleValue() == 0d;
	}

	public static boolean isNullOrZero(Integer d1) {
		return d1 == null || d1.intValue() == 0;
	}

	public static boolean isNullOrZero(Long d1) {
		boolean l = d1 == null;
		return l || d1.longValue() == 0l;
	}

	public static boolean isNullOrZero(Number d1) {
		return d1 == null || d1.doubleValue() == 0d;
	}

	public static Long max(Collection<Long> all) {
		Long max = null;
		for (Long d : all) {
			max = Math.max((max == null) ? 0l : max, d);
		}
		return max;
	}

	public static Long max(Long... all) {
		Long max = null;
		for (Long d : all) {
			max = Math.max((max == null) ? 0l : max, d);
		}
		return max;
	}

	public static Double maxDouble(Collection<Double> all) {
		Double max = null;
		for (Double d : all) {
			max = Math.max((max == null) ? 0d : max, d);
		}
		return max;
	}

	public static Number maxN(Collection<Number> all) {
		Number max = null;
		for (Number d : all) {
			max = Math.max((max == null) ? 0d : max.doubleValue(), d.doubleValue());
		}
		return max;
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

	public static Double mean(List<Long> all, List<Long> ponderation) {
		Long numerator = 0l;
		Long denom = 0l;
		for (int i = 0; i < all.size(); i++) {
			Long a = all.get(i);
			Long b = ponderation.get(i);
			numerator += (a * b);
			denom += b;
		}
		return divide(numerator, denom);
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

	public static Long min(Collection<Long> all) {
		Long max = null;
		for (Long d : all) {
			max = Math.min((max == null) ? Long.MAX_VALUE : max, d);
		}
		return max;
	}

	public static Long min(Long... all) {
		Long max = null;
		for (Long d : all) {
			max = Math.min((max == null) ? Long.MAX_VALUE : max, d);
		}
		return max;
	}

	public static Number minN(Collection<Number> all) {
		Number max = null;
		for (Number d : all) {
			max = Math.min((max == null) ? Double.MAX_VALUE : max.doubleValue(), d.doubleValue());
		}
		return max;
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

	public static Double percent(Double v1, Double v2, boolean format) {
		Double res = divide(v1 * 1d, v2 * 1d);
		return (format) ? res * 100 : res;
	}

	public static Double percent(Long v1, Long v2, boolean format) {
		Double res = divide(v1 * 1d, v2 * 1d);
		return (format) ? res * 100 : res;
	}

	public static double random(int Min, int Max) {
		return (Math.random() * (Max - Min)) + Min;
	}

	public static int random_int(int Min, int Max) {
		return (int) (Math.random() * (Max - Min)) + Min;
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

	public static Double sub(Double a, Double... all) {
		Double total = isNullOrZero(a) ? 0d : a;
		for (Double d : all) {
			total -= (d == null) ? 0 : d;
		}
		return total;
	}

	public static Long sub(Long a, Long... all) {
		Long total = (isNullOrZero(a)) ? 0l : a;
		for (Long d : all) {
			total -= (d == null) ? 0 : d;
		}
		return total;
	}

	public static Number sub(Number a, Number... all) {
		Double total = (isNullOrZero(a)) ? 0l : a.doubleValue();
		for (Number d : all) {
			total -= (d == null) ? 0 : d.doubleValue();
		}
		return total;
	}

	public static Double sub(String d1, String d2) {
		d1 = StringUtils.replace(StringUtils.replace(d1, "%", ""), ",", ".");
		d2 = StringUtils.replace(StringUtils.replace(d2, "%", ""), ",", ".");
		return NumberUtils.toDouble(d1) - NumberUtils.toDouble(d2);
	}

	public static Long sum(Collection<Long> all) {
		Long total = 0l;
		for (Long d : all) {
			total += (d == null) ? 0 : d;
		}
		return total;
	}

	public static <T> Double sum(Collection<T> objects, Fetcher<T, Double> fetcher) {
		Double total = 0d;
		for (T o : objects) {
			total = sum(total, fetcher.fetch(o));
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

	public static Double sum(Double all1, Long... all) {
		Double total = (all1 == null) ? 0d : all1;
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

	public static Long sum(Long... all) {
		Long total = 0l;
		for (Long d : all) {
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

	public static Double sum(Number... all) {
		Double total = 0d;
		for (Number d : all) {
			total += (d == null) ? 0 : d.doubleValue();
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

	public static Number sumN(Collection<Number> all) {
		Double total = 0d;
		for (Number d : all) {
			total += (d == null) ? 0 : d.doubleValue();
		}
		return total;
	}

	public static Double toDouble(Long l) {
		if (isNullOrZero(l)) {
			return 0d;
		}
		return l * 1d;
	}

	public static Double toDouble(String l) {
		l = StringUtils.trim(l);
		if (Strings.isNullOrEmpty(l)) {
			return 0d;
		}
		l = StringUtils.replace(l, " ", "");
		l = StringUtils.replace(l, ",", ".");
		return Double.valueOf(StringUtils.deleteWhitespace(l)) * 1d;
	}

	public static boolean isNumber(String l) {
		if (Strings.isNullOrEmpty(l)) {
			return false;
		}
		try {
			toDouble(l);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Integer toInt(String l) {
		return toDouble(l).intValue();
	}

	public static Long toLong(String l) {
		return toDouble(l).longValue();
	}

	public static boolean contains(Collection<? extends Number> filtered, Number id) {
		for (Number n : filtered) {
			if (equals(n, id)) {
				return true;
			}
		}
		return false;
	}

	public static boolean contains(Collection<? extends Number> list, Collection<? extends Number> toTest) {
		for (Number n : toTest) {
			if (!contains(list, n)) {
				return false;
			}
		}
		return true;
	}

	public static Long toLongNullable(String addressNumber) {
		if (Strings.isNullOrEmpty(addressNumber)) {
			return null;
		}
		return toLong(addressNumber);
	}

}
