package com.nm.auths.oauth.twitter;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.nm.auths.configurations.ConfigurationAuthenticationsSecurity;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.auths.constants.ModuleConfigKeyAuthentication;
import com.nm.auths.dtos.DtoAuthenticationOAuth;
import com.nm.auths.oauth.TokenProcessor;
import com.nm.auths.oauth.manager.OAuthContextManager;
import com.nm.config.SoaModuleConfig;
import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TokenProcessorTwitter implements TokenProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void hydrate(DtoAuthenticationOAuth dto) throws Exception {
		SoaModuleConfig soa = ApplicationUtils.getBean(SoaModuleConfig.class);
		OAuthConsumerToken accessToken = ApplicationUtils.getBean(OAuthContextManager.class)
				.getOAuth1Token(ConfigurationAuthenticationsSecurity.TWITTER_REST_TEMPLATE);
		String consumer = soa.getText(ModuleConfigKeyAuthentication.TwitterClientId);
		String secret = soa.getText(ModuleConfigKeyAuthentication.TwitterSecret);
		TwitterTemplate template = new TwitterTemplate(consumer, secret, accessToken.getValue(),
				accessToken.getSecret());
		long id = template.userOperations().getUserProfile().getId();
		//
		dto.setOpenid(id + "");
		dto.setEmail(template.userOperations().getUserProfile().getName());
		dto.setProvider(AuthenticationProviderDefault.Twitter);
	}

}
