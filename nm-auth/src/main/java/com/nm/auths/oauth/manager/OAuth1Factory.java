package com.nm.auths.oauth.manager;

import java.util.List;

import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;

import com.nm.auths.constants.AuthenticationProvider;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface OAuth1Factory {
	public boolean accept(AuthenticationProvider provider);

	public OAuthRestTemplate get(AuthenticationProvider provider) throws Exception;

	public OAuthRestTemplate build(AuthenticationProvider provider, List<String> scopes) throws Exception;
}
