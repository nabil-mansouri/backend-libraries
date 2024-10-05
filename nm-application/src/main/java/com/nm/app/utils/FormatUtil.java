package com.nm.app.utils;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class FormatUtil {
	public static double parseDouble(String value) {
		if (Strings.isNullOrEmpty(value)) {
			return 0d;
		} else {
			return Double.valueOf(StringUtils.replace(value, ",", "."));
		}
	}

}
