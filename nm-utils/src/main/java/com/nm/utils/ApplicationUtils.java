package com.nm.utils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class ApplicationUtils {

	private static ApplicationContext ac;
	private static AbstractBeanFactory fac;
	private final static Map<String, String> cache = new ConcurrentHashMap<String, String>();

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		Object o = ac.getBean(clazz);
		return ((T) o);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String clazz) {
		Object o = ac.getBean(clazz);
		return ((T) o);
	}

	public static <T> Map<String, T> getBeans(Class<T> clazz) {
		Map<String, T> o = ac.getBeansOfType(clazz);
		return o;
	}

	public static <T> Collection<T> getBeansCollection(Class<T> clazz) {
		Map<String, T> o = ac.getBeansOfType(clazz);
		return o.values();
	}

	public static <T> T getFirstBean(Class<T> clazz) {
		Map<String, T> o = ac.getBeansOfType(clazz);
		return o.values().iterator().next();
	}

	public void setApplicationContext(ApplicationContext ac) {
		ApplicationUtils.ac = ac;
	}

	public void setFac(AbstractBeanFactory fac) {
		ApplicationUtils.fac = fac;
	}

	public static String getProperty(String key) {
		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		String foundProp = null;
		try {
			foundProp = ApplicationUtils.fac.resolveEmbeddedValue("${" + key.trim() + "}");
			cache.put(key, foundProp);
		} catch (IllegalArgumentException ex) {
			// ok - property was not found
		}

		return foundProp;
	}
}
