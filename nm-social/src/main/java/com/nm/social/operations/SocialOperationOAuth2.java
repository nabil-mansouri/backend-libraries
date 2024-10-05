package com.nm.social.operations;

import java.util.Collection;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.nm.social.models.SocialUser;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SocialOperationOAuth2 extends SocialOperation {
	public Collection<String> scopes();

	public SocialUser operation(OAuth2AccessToken op, Object... params) throws Exception;

}
