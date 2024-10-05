package com.nm.auths.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.nm.auths.oauth.manager.OAuthContext;
import com.nm.auths.oauth.manager.OAuthContextWeb;
import com.nm.auths.oauth.manager.OAuthContextWebFilter;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationAuthenticationsWebContext {

	@Bean
	public OAuthContextWebFilter currentWebContextFilter() {
		return new OAuthContextWebFilter();
	}

	@Bean
	@Scope(value = "request")
	public OAuthContext currentWebContext() {
		return new OAuthContextWeb();
	}

}
