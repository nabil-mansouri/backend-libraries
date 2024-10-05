package com.nm.social.operations;

import java.util.Collection;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;

import com.nm.social.models.SocialUser;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SocialOperationOAuth1 extends SocialOperation {
	public Collection<String> scopes();

	public SocialUser operation(OAuthConsumerToken op, Object... params) throws Exception;

}
