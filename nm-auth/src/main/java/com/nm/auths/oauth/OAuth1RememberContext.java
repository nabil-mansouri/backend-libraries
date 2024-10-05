package com.nm.auths.oauth;

import java.io.Serializable;
import java.util.Map;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;

import com.google.inject.internal.Maps;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OAuth1RememberContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, OAuthConsumerToken> tokens = Maps.newHashMap();

	public Map<String, OAuthConsumerToken> getTokens() {
		return tokens;
	}

	public void setTokens(Map<String, OAuthConsumerToken> tokens) {
		this.tokens = tokens;
	}

}
