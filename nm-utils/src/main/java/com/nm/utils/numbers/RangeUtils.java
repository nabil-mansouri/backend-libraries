package com.nm.utils.numbers;

/**
 * 
 * @author Nabil
 * 
 */
public class RangeUtils {
	public static boolean safeTest(Double from, Double to, Long value) {
		if (value == null) {
			value = 0l;
		}
		return safeTest(from, to, Double.valueOf(value));
	}

	public static boolean safeTest(Double from, Double to, Double value) {
		if (value == null) {
			value = 0d;
		}
		if (from != null && from > value) {
			return false;
		}
		if (to != null && to < value) {
			return false;
		}
		return true;
	}
}
