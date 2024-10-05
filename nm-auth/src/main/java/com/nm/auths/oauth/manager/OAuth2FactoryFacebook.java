package com.nm.auths.oauth.manager;

import java.util.List;

import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

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
public class OAuth2FactoryFacebook extends OAuth2FactoryAbstract implements OAuth2Factory {

	public boolean accept(AuthenticationProvider provider) {
		return AuthenticationProviderDefault.Facebook.equals(provider);
	}

	public OAuth2RestTemplate get(AuthenticationProvider provider) throws Exception {
		return ApplicationUtils.getBean(ConfigurationAuthenticationsSecurity.FACEBOOK_REST_TEMPLATE);
	}

	public OAuth2RestTemplate build(AuthenticationProvider provider, List<String> scopes) throws Exception {
		OAuth2RestOperations original = ApplicationUtils
				.getBean(ConfigurationAuthenticationsSecurity.FACEBOOK_REST_TEMPLATE);
		return innerBuild(original, UUIDUtils.UUID(16), scopes);
	}

}
