package com.nm.auths.configurations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.InMemoryProtectedResourceDetailsService;
import org.springframework.security.oauth.consumer.OAuthSecurityContext;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.CoreOAuthConsumerSupport;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.auths.constants.ModuleConfigKeyAuthentication;
import com.nm.auths.oauth.OAuth1RememberContext;
import com.nm.auths.oauth.UserDetailsServicePreAuth;
import com.nm.auths.oauth.fb.AuthorizationCodeAccessTokenProviderFB;
import com.nm.auths.oauth.manager.OAuth1FactoryTwitter;
import com.nm.auths.oauth.manager.OAuth2Cache;
import com.nm.auths.oauth.manager.OAuth2FactoryFacebook;
import com.nm.auths.oauth.manager.OAuth2FactoryGoogle;
import com.nm.auths.oauth.manager.OAuthContextManager;
import com.nm.auths.oauth.manager.OAuthContextManagerImpl;
import com.nm.auths.simple.UserDetailsServiceUsername;
import com.nm.config.SoaModuleConfig;
import com.nm.utils.db.DatabaseTemplateFactory;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableSpringHttpSession
@EnableJdbcHttpSessionCustom
public class ConfigurationAuthenticationsSecurity {
	public static final String SESSION_TABLE_NAME = "nm_app_session";

	public static final String GOOGLE_REST_TEMPLATE = "googleRestTemplate";
	public static final String FACEBOOK_REST_TEMPLATE = "fbRestTemplate";
	public static final String TWITTER_REST_TEMPLATE = "twitterRestTemplate";

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder encoder,
			UserDetailsServiceUsername user) {
		DaoAuthenticationProvider pro = new DaoAuthenticationProvider();
		pro.setPasswordEncoder(encoder);
		pro.setUserDetailsService(user);
		return pro;
	}

	@Bean
	public PreAuthenticatedAuthenticationProvider daoPreAuthenticationProvider(UserDetailsServicePreAuth user) {
		PreAuthenticatedAuthenticationProvider pro = new PreAuthenticatedAuthenticationProvider();
		pro.setPreAuthenticatedUserDetailsService(user);
		return pro;
	}

	@Bean()
	public AuthenticationManager providerManager(DaoAuthenticationProvider provider0,
			PreAuthenticatedAuthenticationProvider provider1) {
		List<AuthenticationProvider> providers = Lists.newArrayList();
		providers.add(provider0);
		providers.add(provider1);
		ProviderManager manager = new ProviderManager(providers);
		return manager;
	}

	private DataSource getDS(DatabaseTemplateFactory factory) {
		return factory.dsResource(ConfigurationAuthentications.MODULE_NAME);
	}

	/**
	 * Need for httpsession jdbc
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean()
	public PlatformTransactionManager transactionManager(DatabaseTemplateFactory fac) {
		return new DataSourceTransactionManager(getDS(fac));
	}

	@Bean
	@Scope("session")
	public OAuth2ProtectedResourceDetails googleResource(SoaModuleConfig soa) {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setId("google-oauth-client");
		details.setClientId(soa.getText(ModuleConfigKeyAuthentication.GoogleClientId));
		details.setClientSecret(soa.getText(ModuleConfigKeyAuthentication.GoogleSecret));
		details.setAccessTokenUri(soa.getText(ModuleConfigKeyAuthentication.GoogleTokenURI));
		details.setUserAuthorizationUri(soa.getText(ModuleConfigKeyAuthentication.GoogleAuthURI));
		details.setTokenName(soa.getText(ModuleConfigKeyAuthentication.GoogleAuthCode));
		String commaSeparatedScopes = soa.getText(ModuleConfigKeyAuthentication.GoogleScope);
		details.setScope(parseScopes(commaSeparatedScopes));
		details.setUseCurrentUri(true);
		details.setAuthenticationScheme(AuthenticationScheme.query);
		details.setClientAuthenticationScheme(AuthenticationScheme.form);
		return details;
	}

	private List<String> parseScopes(String commaSeparatedScopes) {
		List<String> scopes = Lists.newArrayList();
		Collections.addAll(scopes, commaSeparatedScopes.split(","));
		return scopes;
	}

	@Bean(name = GOOGLE_REST_TEMPLATE)
	@Scope(value = "session")
	public OAuth2RestOperations restTemplate(SoaModuleConfig soa, DatabaseTemplateFactory fac) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(googleResource(soa));
		// SET JDBC TOKEN SERVICE
		ClientTokenServices clientTokenServices = new JdbcClientTokenServices(getDS(fac));
		AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider> asList(
				new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
				new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider()));
		provider.setClientTokenServices(clientTokenServices);
		template.setAccessTokenProvider(provider);
		return template;
	}

	@Bean
	@Scope("session")
	public OAuth2ProtectedResourceDetails facebookResource(SoaModuleConfig soa) {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setId("fb-oauth-client");
		details.setClientId(soa.getText(ModuleConfigKeyAuthentication.FacebookClientId));
		details.setClientSecret(soa.getText(ModuleConfigKeyAuthentication.FacebookSecret));
		details.setAccessTokenUri(soa.getText(ModuleConfigKeyAuthentication.FacebookTokenURI));
		details.setUserAuthorizationUri(soa.getText(ModuleConfigKeyAuthentication.FacebookAuthURI));
		details.setTokenName(soa.getText(ModuleConfigKeyAuthentication.FacebookAuthCode));
		String commaSeparatedScopes = soa.getText(ModuleConfigKeyAuthentication.FacebookScope);
		details.setScope(parseScopes(commaSeparatedScopes));
		details.setUseCurrentUri(true);
		details.setAuthenticationScheme(AuthenticationScheme.form);
		details.setClientAuthenticationScheme(AuthenticationScheme.form);
		return details;
	}

	@Bean(name = FACEBOOK_REST_TEMPLATE)
	@Scope(value = "session")
	public OAuth2RestOperations restTemplateFB(SoaModuleConfig soa, DatabaseTemplateFactory fac) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(facebookResource(soa));
		// SET JDBC TOKEN SERVICE
		ClientTokenServices clientTokenServices = new JdbcClientTokenServices(getDS(fac));
		// OVERRIDE AUTH ACCESS TOKEN
		AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider> asList(
				new AuthorizationCodeAccessTokenProviderFB(), new ImplicitAccessTokenProvider(),
				new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider()));
		provider.setClientTokenServices(clientTokenServices);
		template.setAccessTokenProvider(provider);
		return template;
	}

	public static final String TWITTER_OAUTH_CLIENT_ID = "twitter-oauth-client";

	@Bean
	@Scope("session")
	public BaseProtectedResourceDetails twitterResource(SoaModuleConfig soa) {
		BaseProtectedResourceDetails details = new BaseProtectedResourceDetails();
		details.setId(TWITTER_OAUTH_CLIENT_ID);
		details.setAccessTokenHttpMethod("POST");
		details.setAccessTokenURL(soa.getText(ModuleConfigKeyAuthentication.TwitterAccessTokenURI));
		details.setConsumerKey(soa.getText(ModuleConfigKeyAuthentication.TwitterClientId));
		details.setRequestTokenHttpMethod("POST");
		details.setRequestTokenURL(soa.getText(ModuleConfigKeyAuthentication.TwitterRequestTokenURI));
		details.setAcceptsAuthorizationHeader(true);
		details.setUserAuthorizationURL(soa.getText(ModuleConfigKeyAuthentication.TwitterAuthURI));
		String sec = soa.getText(ModuleConfigKeyAuthentication.TwitterSecret);
		SharedConsumerSecretImpl sharedSecret = new SharedConsumerSecretImpl(sec);
		details.setSharedSecret(sharedSecret);
		// details.setUse10a(true);
		return details;
	}

	@Bean(name = TWITTER_REST_TEMPLATE)
	@Scope(value = "session")
	public OAuthRestTemplate restTemplateTwitter(SoaModuleConfig soa) {
		OAuthRestTemplate template = new OAuthRestTemplate(twitterResource(soa));
		CoreOAuthConsumerSupport consumer = new CoreOAuthConsumerSupport();
		consumer.setProtectedResourceDetailsService(memoryService(soa));
		template.setSupport(consumer);
		return template;
	}

	@Bean
	@Scope(value = "session")
	public InMemoryProtectedResourceDetailsService memoryService(SoaModuleConfig soa) {
		ProtectedResourceDetails d = twitterResource(soa);
		InMemoryProtectedResourceDetailsService in = new InMemoryProtectedResourceDetailsService();
		Map<String, ProtectedResourceDetails> map = Maps.newHashMap();
		map.put(d.getId(), d);
		in.setResourceDetailsStore(map);
		return in;
	}

	@Bean
	@Scope(value = "session")
	public OAuth1RememberContext rememberOAuth1Context() {
		return new OAuth1RememberContext();
	}

	@Bean
	@Scope(value = "session")
	public OAuthSecurityContext oauthSecurityContextImpl() {
		return new OAuthSecurityContextImpl();
	}

	/**
	 * one OauthClientCOntext foreach other provider (scope session then pass as
	 * argument to RestTemplate)
	 * 
	 * @See @EnableOAuth2Client (and remove it)
	 * 
	 * @return
	 */
	@Bean
	@Scope(value = "session")
	public OAuth2Cache oauth2Cache() {
		return new OAuth2Cache();
	}

	@Bean
	@Scope(value = "request")
	public AccessTokenRequest accessTokenRequest() {
		return new DefaultAccessTokenRequest();
	}

	@Bean
	public OAuthContextManager oauthContextManagerImpl() {
		OAuthContextManagerImpl i = new OAuthContextManagerImpl();
		i.add(new OAuth2FactoryGoogle());
		i.add(new OAuth2FactoryFacebook());
		i.add(new OAuth1FactoryTwitter());
		return i;
	}

}
