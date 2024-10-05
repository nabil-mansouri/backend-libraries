package com.nm.utils.dates;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Nabil
 * 
 */
public class UUIDUtils {

	public static String UUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	public static String UUID(int size) {
		return StringUtils.substring(java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(), 0, size);
	}

}
