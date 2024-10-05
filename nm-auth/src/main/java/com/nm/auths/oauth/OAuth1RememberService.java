package com.nm.auths.oauth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.rememberme.OAuthRememberMeServices;
import org.springframework.security.oauth.consumer.token.OAuthConsumerTokenServices;

import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OAuth1RememberService implements OAuthRememberMeServices, OAuthConsumerTokenServices {

	public OAuthConsumerToken getToken(String resourceId) throws AuthenticationException {
		OAuth1RememberContext context = ApplicationUtils.getBean(OAuth1RememberContext.class);
		return context.getTokens().get(resourceId);
	}

	public void storeToken(String resourceId, OAuthConsumerToken token) {
		OAuth1RememberContext context = ApplicationUtils.getBean(OAuth1RememberContext.class);
		context.getTokens().put(resourceId, token);
	}

	public void removeToken(String resourceId) {
		OAuth1RememberContext context = ApplicationUtils.getBean(OAuth1RememberContext.class);
		context.getTokens().remove(resourceId);
	}

	public Map<String, OAuthConsumerToken> loadRememberedTokens(HttpServletRequest request,
			HttpServletResponse response) {
		OAuth1RememberContext context = ApplicationUtils.getBean(OAuth1RememberContext.class);
		return context.getTokens();
	}

	public void rememberTokens(Map<String, OAuthConsumerToken> tokens, HttpServletRequest request,
			HttpServletResponse response) {
		OAuth1RememberContext context = ApplicationUtils.getBean(OAuth1RememberContext.class);
		context.setTokens(tokens);
	}

}
