package com.nm.social.operations;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.google.api.client.util.Lists;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.oauth.manager.OAuthContextManager;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationListener.SocialOperationListenerDefault;
import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialOperationLauncher {
	private OAuthContextManager manager;

	public void setManager(OAuthContextManager manager) {
		this.manager = manager;
	}

	public void oauth1(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			Object... params) throws Exception {
		oauth1(providers, e, new SocialOperationListenerDefault(), params);
	}

	public void oauth1(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			SocialOperationListener listener, Object... params) throws Exception {
		try {
			Collection<SocialOperationOAuth1> operations = ApplicationUtils
					.getBeansCollection(SocialOperationOAuth1.class);
			// Keep ORDER of operations and order of authproviders
			for (SocialOperationEnum ee : e) {
				for (SocialOperationOAuth1 op : operations) {
					if (op.accept(ee)) {
						for (AuthenticationProvider a : providers) {
							if (op.accept(a)) {
								// EXECUTE
								List<String> scopes = Lists.newArrayList();
								for (String s : op.scopes()) {
									Collections.addAll(scopes, s.split(","));
								}
								manager.updateOAuth1Scopes(a, scopes);
							}
						}
					}
				}
			}
			// EXECUTE
			for (SocialOperationEnum ee : e) {
				for (SocialOperationOAuth1 op : operations) {
					if (op.accept(ee)) {
						for (AuthenticationProvider a : providers) {
							if (op.accept(a)) {
								OAuthRestTemplate copyWithScopes = manager.getOAuth1Template(a);
								OAuthConsumerToken token = manager.getOAuth1Token(copyWithScopes);
								SocialUser user = op.operation(token, params);
								listener.onResult(a, ee, user);
							}
						}
					}
				}
			}
		} finally {

		}
	}

	public void oauth2(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			Object... params) throws Exception {
		oauth2(providers, e, new SocialOperationListenerDefault(), params);
	}

	public void oauth2(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			SocialOperationListener listener, Object... params) throws Exception {
		try {
			Collection<SocialOperationOAuth2> operations = ApplicationUtils
					.getBeansCollection(SocialOperationOAuth2.class);
			// Keep ORDER of operations and order of authproviders
			for (SocialOperationEnum ee : e) {
				for (SocialOperationOAuth2 op : operations) {
					if (op.accept(ee)) {
						for (AuthenticationProvider a : providers) {
							if (op.accept(a)) {
								// EXECUTE
								List<String> scopes = Lists.newArrayList();
								for (String s : op.scopes()) {
									Collections.addAll(scopes, s.split(","));
								}
								manager.updateOAuth2Scopes(a, scopes);
							}
						}
					}
				}
			}
			// EXECUTE
			for (SocialOperationEnum ee : e) {
				for (SocialOperationOAuth2 op : operations) {
					if (op.accept(ee)) {
						for (AuthenticationProvider a : providers) {
							if (op.accept(a)) {
								OAuth2RestOperations copyWithScopes = manager.getOAuth2Template(a);
								OAuth2AccessToken token = manager.getOAuth2Token(copyWithScopes);
								SocialUser user = op.operation(token, params);
								listener.onResult(a, ee, user);
							}
						}
					}
				}
			}
		} finally {

		}
	}

}
