package com.nm.utils.dates;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class URLUtilsExt {
	public static Map<String, List<String>> getQueryParams(String url) {
		try {
			Map<String, List<String>> params = new HashMap<String, List<String>>();
			String[] urlParts = url.split("\\?");
			if (urlParts.length > 1) {
				String query = urlParts[1];
				for (String param : query.split("&")) {
					String[] pair = param.split("=");
					String key = URLDecoder.decode(pair[0], "UTF-8");
					String value = "";
					if (pair.length > 1) {
						value = URLDecoder.decode(pair[1], "UTF-8");
					}

					List<String> values = params.get(key);
					if (values == null) {
						values = new ArrayList<String>();
						params.put(key, values);
					}
					values.add(value);
				}
			}

			return params;
		} catch (UnsupportedEncodingException ex) {
			throw new AssertionError(ex);
		}
	}

	public static List<String> getQueryParams(String url, String name) {
		List<String> l = Lists.newArrayList();
		return getQueryParams(url).getOrDefault(name, l);
	}

	public static Map<String, String> getQueryParamsMap(String url) throws URISyntaxException {
		Map<String, String> map = Maps.newHashMap();
		URIBuilder builder = new URIBuilder(url);
		for (NameValuePair p : builder.getQueryParams()) {
			map.put(p.getName(), p.getValue());
		}
		return map;
	}

	public static String build(String url, Map<String, String> parameters) throws URISyntaxException {
		URIBuilder builder = new URIBuilder(url);
		for (String key : parameters.keySet()) {
			builder.addParameter(key, parameters.get(key));
		}
		return builder.build().toString();
	}

	public static String getQueryParamsFirst(String url, String name) {
		List<String> l = getQueryParams(url, name);
		if (l.isEmpty()) {
			return null;
		} else {
			return l.iterator().next();
		}
	}

	public static String getQueryParamsFirst(URL url, String name) {
		return getQueryParamsFirst(url.toString(), name);
	}
}
