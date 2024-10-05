package com.nm.auths.oauth.manager;

import java.util.List;

import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class OAuth1FactoryAbstract {

	protected OAuthRestTemplate innerBuild(OAuthRestTemplate original, String newId, List<String> scopes)
			throws Exception {
		BaseProtectedResourceDetails details = new BaseProtectedResourceDetails();
		BaseProtectedResourceDetails initial = (BaseProtectedResourceDetails) original.getResource();
		details.setId(newId);
		details.setAcceptsAuthorizationHeader(initial.isAcceptsAuthorizationHeader());
		details.setAccessTokenHttpMethod(initial.getAccessTokenHttpMethod());
		details.setAccessTokenURL(initial.getAccessTokenURL());
		details.setAdditionalParameters(initial.getAdditionalParameters());
		details.setAdditionalRequestHeaders(initial.getAdditionalRequestHeaders());
		details.setAuthorizationHeaderRealm(initial.getAuthorizationHeaderRealm());
		details.setConsumerKey(initial.getConsumerKey());
		details.setRequestTokenHttpMethod(initial.getRequestTokenHttpMethod());
		details.setRequestTokenURL(initial.getRequestTokenURL());
		details.setSharedSecret(initial.getSharedSecret());
		details.setSignatureMethod(initial.getSignatureMethod());
		details.setUse10a(initial.isUse10a());
		details.setUserAuthorizationURL(initial.getUserAuthorizationURL());
		// MUST USE A SAVED CONTEXT FOR STATE KEY (but how to not share access
		// token?)
		OAuthRestTemplate template = new OAuthRestTemplate(details);
		template.setSupport(original.getSupport());
		return template;
	}

}
