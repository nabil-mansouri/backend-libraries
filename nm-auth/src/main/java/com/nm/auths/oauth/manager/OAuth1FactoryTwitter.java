package com.nm.auths.oauth.manager;

import java.util.List;

import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;

import com.nm.auths.configurations.ConfigurationAuthenticationsSecurity;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.dates.UUIDUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OAuth1FactoryTwitter extends OAuth1FactoryAbstract implements OAuth1Factory {

	public boolean accept(AuthenticationProvider provider) {
		return AuthenticationProviderDefault.Twitter.equals(provider);
	}

	public OAuthRestTemplate get(AuthenticationProvider provider) throws Exception {
		return ApplicationUtils.getBean(ConfigurationAuthenticationsSecurity.TWITTER_REST_TEMPLATE);
	}

	public OAuthRestTemplate build(AuthenticationProvider provider, List<String> scopes) throws Exception {
		OAuthRestTemplate original = ApplicationUtils
				.getBean(ConfigurationAuthenticationsSecurity.TWITTER_REST_TEMPLATE);
		return innerBuild(original, UUIDUtils.UUID(16), scopes);
	}

}
