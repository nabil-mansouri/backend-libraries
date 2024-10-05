package com.nm.app.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class UUIDUtils {

	public static String uuid(int length) {
		return StringUtils.substring(StringUtils.replace(UUID.randomUUID().toString(), "-", ""), 0, length).toUpperCase();
	}
}
