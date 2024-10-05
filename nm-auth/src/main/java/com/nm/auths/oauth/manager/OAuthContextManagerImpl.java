package com.nm.auths.oauth.manager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.springframework.security.oauth.common.OAuthProviderParameter;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContext;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.google.api.client.util.Sets;
import com.google.inject.internal.Maps;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.oauth.OAuth1RememberService;
import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OAuthContextManagerImpl implements OAuthContextManager {
	private static final Log LOG = LogFactory.getLog(OAuthContextManagerImpl.class);
	private Collection<OAuth2Factory> factories = Sets.newHashSet();
	private Collection<OAuth1Factory> factories1 = Sets.newHashSet();

	public void add(OAuth2Factory f) {
		this.factories.add(f);
	}

	public void add(OAuth1Factory f) {
		this.factories1.add(f);
	}

	public OAuthConsumerToken getOAuth1Token(OAuthRestTemplate res) {
		OAuthSecurityContextImpl clientContext = (OAuthSecurityContextImpl) ApplicationUtils
				.getBean(OAuthSecurityContext.class);
		//
		OAuth1RememberService service = new OAuth1RememberService();
		String resourceId = res.getResource().getId();
		//
		Map<String, OAuthConsumerToken> rememberedTokens = service.loadRememberedTokens(null, null);
		Map<String, OAuthConsumerToken> accessTokens = new TreeMap<String, OAuthConsumerToken>();
		Map<String, OAuthConsumerToken> requestTokens = new TreeMap<String, OAuthConsumerToken>();
		if (rememberedTokens != null) {
			for (Map.Entry<String, OAuthConsumerToken> tokenEntry : rememberedTokens.entrySet()) {
				OAuthConsumerToken token = tokenEntry.getValue();
				if (token != null) {
					if (token.isAccessToken()) {
						accessTokens.put(tokenEntry.getKey(), token);
					} else {
						requestTokens.put(tokenEntry.getKey(), token);
					}
				}
			}
		}
		clientContext.setAccessTokens(accessTokens);
		//
		try {
			if (accessTokens.containsKey(resourceId)) {
				return accessTokens.get(resourceId);
			} else {
				//
				OAuthContext currentWeb = ApplicationUtils.getBean(OAuthContext.class);
				OAuthConsumerToken token = requestTokens.remove(resourceId);
				if (token == null) {
					token = service.getToken(resourceId);
				}
				String verifier = currentWeb.getMapParameters().get(OAuthProviderParameter.oauth_verifier.toString());
				// if the token is null OR
				// if there is NO access token and (we're not using 1.0a or the
				// verifier is not null)
				if (token == null || (!token.isAccessToken() && (!res.getResource().isUse10a() || verifier == null))) {
					// no token associated with the resource, start the oauth
					// flow. if there's a request token, but no verifier, we'll
					// assume that a previous oauth request failed and we need
					// to get a new request token.
					if (LOG.isDebugEnabled()) {
						LOG.debug("Obtaining request token for resource: " + resourceId);
					}
					// obtain authorization.
					token = res.getSupport().getUnauthorizedRequestToken(resourceId, currentWeb.getCurrentURL());

					if (LOG.isDebugEnabled()) {
						LOG.debug("Request token obtained for resource " + resourceId + ": " + token);
					}

					// we've got a request token, now we need to authorize it.
					requestTokens.put(resourceId, token);
					service.storeToken(resourceId, token);
					String redirect = getUserAuthorizationRedirectURL(res.getResource(), token,
							currentWeb.getCurrentURL());

					if (LOG.isDebugEnabled()) {
						LOG.debug("Redirecting request to " + redirect
								+ " for user authorization of the request token for resource " + resourceId + ".");
					}
					Map<String, String> map = Maps.newHashMap();
					throw new UserRedirectRequiredException(redirect, map);
				} else if (!token.isAccessToken()) {
					// we have a presumably authorized request token, let's try
					// to get an access token with it.
					if (LOG.isDebugEnabled()) {
						LOG.debug("Obtaining access token for resource: " + resourceId);
					}
					// authorize the request token and store it.
					try {
						token = res.getSupport().getAccessToken(token, verifier);
					} finally {
						service.removeToken(resourceId);
					}

					if (LOG.isDebugEnabled()) {
						LOG.debug("Access token " + token + " obtained for resource " + resourceId
								+ ". Now storing and using.");
					}

					service.storeToken(resourceId, token);
				}
				accessTokens.put(resourceId, token);
				return token;
			}
		} finally {
			HashMap<String, OAuthConsumerToken> tokensToRemember = new HashMap<String, OAuthConsumerToken>();
			tokensToRemember.putAll(requestTokens);
			tokensToRemember.putAll(accessTokens);
			service.rememberTokens(tokensToRemember, null, null);
		}
	}

	public OAuthConsumerToken getOAuth1Token(String r) {
		OAuthRestTemplate res = ApplicationUtils.getBean(r);
		return getOAuth1Token(res);
	}

	public OAuthRestTemplate getOAuth1Template(AuthenticationProvider model) throws Exception {
		for (OAuth1Factory f : factories1) {
			if (f.accept(model)) {
				return f.get(model);
			}
		}
		throw new IllegalArgumentException("Could not found factory for type:" + model);
	}

	public OAuth2RestOperations getOAuth2Template(AuthenticationProvider model) throws Exception {
		for (OAuth2Factory f : factories) {
			if (f.accept(model)) {
				return f.get(model);
			}
		}
		throw new IllegalArgumentException("Could not found factory for type:" + model);
	}

	public OAuth2AccessToken getOAuth2Token(OAuth2RestOperations res) {
		// FORCE UPDATE TOKEN REQUEST
		hydrateTokenRequest(res);
		return res.getAccessToken();
	}

	public OAuth2AccessToken getOAuth2Token(String res) {
		OAuth2RestOperations r = ApplicationUtils.getBean(res);
		return getOAuth2Token(r);
	}

	protected String getUserAuthorizationRedirectURL(ProtectedResourceDetails details, OAuthConsumerToken requestToken,
			String callbackURL) {
		try {
			String baseURL = details.getUserAuthorizationURL();
			StringBuilder builder = new StringBuilder(baseURL);
			char appendChar = baseURL.indexOf('?') < 0 ? '?' : '&';
			builder.append(appendChar).append("oauth_token=");
			builder.append(URLEncoder.encode(requestToken.getValue(), "UTF-8"));
			if (!details.isUse10a()) {
				builder.append('&').append("oauth_callback=");
				builder.append(URLEncoder.encode(callbackURL, "UTF-8"));
			}
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public void hydrateTokenRequest() {
		Collection<OAuth2RestOperations> operations = ApplicationUtils.getBeansCollection(OAuth2RestOperations.class);
		for (OAuth2RestOperations o : operations) {
			hydrateTokenRequest(o);
		}
	}

	public AccessTokenRequest hydrateTokenRequest(OAuth2RestOperations res) {
		OAuthContext co = ApplicationUtils.getBean(OAuthContext.class);
		AccessTokenRequest request = res.getOAuth2ClientContext().getAccessTokenRequest();
		request.setCurrentUri(co.getCurrentURL());
		// CLEAR
		for (String key : request.toSingleValueMap().keySet()) {
			request.remove(key);
		}
		// SET
		for (NameValuePair v : co.getParameters()) {
			request.add(v.getName(), v.getValue());
		}
		return request;
	}

	public void setFactories(Collection<OAuth2Factory> factories) {
		this.factories = factories;
	}

	public OAuthRestTemplate updateOAuth1Scopes(AuthenticationProvider model, List<String> scopes) throws Exception {
		for (OAuth1Factory f : factories1) {
			if (f.accept(model)) {
				OAuthRestTemplate op = f.get(model);
				return op;
			}
		}
		throw new IllegalArgumentException("Could not found factory for type:" + model);
	}

	public OAuth2RestOperations updateOAuth2Scopes(AuthenticationProvider model, List<String> scopes) throws Exception {
		for (OAuth2Factory f : factories) {
			if (f.accept(model)) {
				OAuth2RestOperations op = f.get(model);
				if (op.getResource().getScope().containsAll(scopes)) {
					return op;
				} else {
					op.getResource().getScope().addAll(scopes);
					// REMOVE TOKEN FROM PARAMETERS
					OAuthContext co = ApplicationUtils.getBean(OAuthContext.class);
					co.removeParameters(op.getResource().getTokenName());
					// REMOVE ACCESS TOKEN
					op.getOAuth2ClientContext().setAccessToken(null);
					return op;
				}
			}
		}
		throw new IllegalArgumentException("Could not found factory for type:" + model);
	}
}
