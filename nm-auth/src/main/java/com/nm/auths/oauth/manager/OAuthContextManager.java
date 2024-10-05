package com.nm.auths.oauth.manager;

import java.util.List;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.nm.auths.constants.AuthenticationProvider;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface OAuthContextManager {

	public OAuthRestTemplate getOAuth1Template(AuthenticationProvider a) throws Exception;

	public OAuthConsumerToken getOAuth1Token(OAuthRestTemplate res);

	public OAuthConsumerToken getOAuth1Token(String res);

	public OAuth2RestOperations getOAuth2Template(AuthenticationProvider model) throws Exception;

	public OAuth2AccessToken getOAuth2Token(OAuth2RestOperations res);

	public OAuth2AccessToken getOAuth2Token(String res);

	public OAuthRestTemplate updateOAuth1Scopes(AuthenticationProvider model, List<String> scopes) throws Exception;

	public OAuth2RestOperations updateOAuth2Scopes(AuthenticationProvider model, List<String> scopes) throws Exception;

}
