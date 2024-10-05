package com.nm.auths.oauth.google;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.jackson2.JacksonFactory;
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
public class TokenProcessorGoogle implements TokenProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void hydrate(DtoAuthenticationOAuth dto) throws Exception {
		OAuth2AccessToken token = ApplicationUtils.getBean(OAuthContextManager.class)
				.getOAuth2Token(ConfigurationAuthenticationsSecurity.GOOGLE_REST_TEMPLATE);
		String tokenId = token.getAdditionalInformation().get("id_token").toString();
		GoogleIdToken t = GoogleIdToken.parse(new JacksonFactory(), tokenId);
		dto.setOpenid(t.getPayload().getSubject());
		dto.setEmail(t.getPayload().getEmail());
		dto.setProvider(AuthenticationProviderDefault.Google);
	}

}
