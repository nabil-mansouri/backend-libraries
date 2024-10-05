package com.nm.utils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class LogicalUtils {
	@SafeVarargs
	public static <T> T firstNotNull(T... toTest) {
		for (T t : toTest) {
			if (t != null) {
				return t;
			}
		}
		return null;
	}

	public static <T> T ifempty(T toTest, T defau) {
		if (toTest == null) {
			return defau;
		} else {
			return toTest;
		}
	}
}
