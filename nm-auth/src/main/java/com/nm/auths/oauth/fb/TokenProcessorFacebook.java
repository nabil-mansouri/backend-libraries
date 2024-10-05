package com.nm.auths.oauth.fb;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import com.nm.auths.configurations.ConfigurationAuthenticationsSecurity;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.auths.dtos.DtoAuthenticationOAuth;
import com.nm.auths.oauth.TokenProcessor;
import com.nm.auths.oauth.manager.OAuthContextManager;
import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TokenProcessorFacebook implements TokenProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void hydrate(DtoAuthenticationOAuth dto) throws Exception {
		OAuth2AccessToken token = ApplicationUtils.getBean(OAuthContextManager.class)
				.getOAuth2Token(ConfigurationAuthenticationsSecurity.FACEBOOK_REST_TEMPLATE);
		String accessToken = token.getValue();
		Facebook facebook = new FacebookTemplate(accessToken);
		//
		dto.setOpenid(facebook.userOperations().getUserProfile().getId());
		dto.setEmail(facebook.userOperations().getUserProfile().getEmail());
		dto.setProvider(AuthenticationProviderDefault.Facebook);
	}

}
