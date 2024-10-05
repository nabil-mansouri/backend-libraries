package com.nm.auths.oauth.manager;

import java.util.List;

import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import com.nm.utils.ApplicationUtils;
import com.nm.utils.ReflectionUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class OAuth2FactoryAbstract {

	protected OAuth2RestTemplate innerBuild(OAuth2RestOperations original, String newId, List<String> scopes)
			throws Exception {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		AuthorizationCodeResourceDetails initial = (AuthorizationCodeResourceDetails) original.getResource();
		details.setId(newId);
		details.setClientId(initial.getClientId());
		details.setClientSecret(initial.getClientSecret());
		details.setAccessTokenUri(initial.getAccessTokenUri());
		details.setUserAuthorizationUri(initial.getUserAuthorizationUri());
		details.setTokenName(initial.getTokenName());
		details.setScope(scopes);
		details.setUseCurrentUri(true);
		details.setAuthenticationScheme(initial.getAuthenticationScheme());
		details.setClientAuthenticationScheme(initial.getClientAuthenticationScheme());
		// MUST USE A SAVED CONTEXT FOR STATE KEY (but how to not share access
		// token?)
		AccessTokenRequest req = ApplicationUtils.getBean(AccessTokenRequest.class);
		OAuth2RestTemplate template = new OAuth2RestTemplate(details, new DefaultOAuth2ClientContext(req));
		AccessTokenProvider provider = (AccessTokenProvider) ReflectionUtils.get(original, "accessTokenProvider");
		template.setAccessTokenProvider(provider);
		return template;
	}

}
