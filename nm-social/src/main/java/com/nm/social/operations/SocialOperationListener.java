package com.nm.social.operations;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.models.SocialUser;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SocialOperationListener {
	public void onResult(AuthenticationProvider provider, SocialOperationEnum operation, SocialUser user);

	public static class SocialOperationListenerDefault implements SocialOperationListener {

		@Override
		public void onResult(AuthenticationProvider provider, SocialOperationEnum operation, SocialUser user) {

		}

	}
}
