package com.nm.auths.oauth.manager;

import java.util.List;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.nm.auths.constants.AuthenticationProvider;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface OAuth2Factory {
	public boolean accept(AuthenticationProvider provider);

	public OAuth2RestTemplate get(AuthenticationProvider provider) throws Exception;

	public OAuth2RestTemplate build(AuthenticationProvider provider, List<String> scopes) throws Exception;
}
