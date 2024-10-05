package com.nm.utils;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class PathUtilsExt {

	public static String buildURL(List<String> paths, String name) {
		List<String> copy = Lists.newArrayList();
		copy.addAll(paths);
		copy.add(name);
		return buildURL(copy);
	}

	public static String buildURL(String base, List<String> paths, String name) {
		List<String> copy = Lists.newArrayList();
		copy.add(base);
		copy.addAll(paths);
		copy.add(name);
		return buildURL(copy);
	}

	public static String buildURL(String base, List<String> paths) {
		List<String> copy = Lists.newArrayList();
		copy.add(base);
		copy.addAll(paths);
		return buildURL(copy);
	}

	public static String buildURL(List<String> paths) {
		List<String> fixed = Lists.newArrayList();
		for (String p : paths) {
			p = StringUtils.trim(p);
			p = StringUtils.removeStart(p, "/");
			p = StringUtils.removeEnd(p, "/");
			fixed.add(p);
		}
		return StringUtils.join(fixed, "/");
	}

	public static String buildFSPath(String base, List<String> paths, String name) {
		List<String> copy = Lists.newArrayList();
		copy.add(base);
		copy.addAll(paths);
		copy.add(name);
		return buildFSPath(copy);
	}

	public static String buildFSPath(String base, List<String> paths) {
		List<String> copy = Lists.newArrayList();
		copy.add(base);
		copy.addAll(paths);
		return buildFSPath(copy);
	}

	public static String buildFSPath(List<String> paths) {
		List<String> fixed = Lists.newArrayList();
		for (String p : paths) {
			p = StringUtils.trim(p);
			// p = StringUtils.removeStart(p, "/");
			// p = StringUtils.removeEnd(p, "/");
			// p = StringUtils.removeStart(p, "\\");
			// p = StringUtils.removeEnd(p, "\\");
			fixed.add(p);
		}
		return StringUtils.join(fixed, File.separator);
	}
}
