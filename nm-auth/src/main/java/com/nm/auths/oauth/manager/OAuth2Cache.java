package com.nm.auths.oauth.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.google.common.collect.Maps;
import com.nm.auths.constants.AuthenticationProvider;

/**
 * Scope session
 * 
 * @author Nabil MANSOURI
 *
 */
public class OAuth2Cache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, OAuth2RestTemplate> cache = Maps.newHashMap();

	private String key(AuthenticationProvider provider, Collection<String> scope) {
		List<String> sorted = new ArrayList<String>();
		for (String s : scope) {
			sorted.add(StringUtils.trim(s));
		}
		sorted.sort(new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		return provider.toString() + "/" + StringUtils.join(sorted, "-");
	}

	public OAuth2RestTemplate push(AuthenticationProvider provider, Collection<String> scopes, OAuth2RestTemplate r) {
		cache.put(key(provider, scopes), r);
		return r;
	}

	public boolean contains(AuthenticationProvider provider, Collection<String> scopes) {
		return cache.containsKey(key(provider, scopes));
	}

	public OAuth2RestTemplate get(AuthenticationProvider provider, Collection<String> scopes) {
		return cache.get(key(provider, scopes));
	}
}
