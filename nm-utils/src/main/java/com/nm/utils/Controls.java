package com.nm.utils;

import java.util.Collection;

public class Controls {
	
	public static boolean isDefine(String string) {
		return string != null && !string.isEmpty();
	}
	
	public static boolean isDefine(Collection<?> collection) {
		return collection != null && collection.size() > 0;
	}
	
}
