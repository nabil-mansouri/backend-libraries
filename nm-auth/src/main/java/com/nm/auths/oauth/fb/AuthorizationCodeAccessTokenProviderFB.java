package com.nm.auths.oauth.fb;

import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.util.MultiValueMap;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AuthorizationCodeAccessTokenProviderFB extends AuthorizationCodeAccessTokenProvider {

	public AuthorizationCodeAccessTokenProviderFB() {
		super();
	}

	@Override
	protected HttpMethod getHttpMethod() {
		return HttpMethod.GET;
	}

	protected String getAccessTokenUri(OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form) {
		form.add("client_id", resource.getClientId());
		String str = super.getAccessTokenUri(resource, form);
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving token from " + str);
		}
		return str;

	}

}
