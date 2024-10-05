package com.nm.auths.configurations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.auths.constants.AuthenticationType.AuthenticationTypeDefault;
import com.nm.auths.constants.ModuleConfigKeyAuthentication;
import com.nm.auths.converters.ConverterGroupDefault;
import com.nm.auths.converters.ConverterUserDefault;
import com.nm.auths.grants.AuthorityGranter;
import com.nm.auths.grants.AuthorityGranterProcessor;
import com.nm.auths.oauth.ConverterAuthenticationOAuth;
import com.nm.auths.oauth.ConverterAuthenticationToken;
import com.nm.auths.oauth.UserDetailsServicePreAuth;
import com.nm.auths.simple.ConverterAuthenticationSimple;
import com.nm.auths.simple.ConverterAuthenticationUsername;
import com.nm.auths.simple.UserDetailsServiceUsername;
import com.nm.auths.soa.SoaAuthentication;
import com.nm.auths.soa.SoaAuthenticationImpl;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@Import({ ConfigurationAuthenticationsSecurity.class })
public class ConfigurationAuthentications {
	public static final String MODULE_NAME = "auth";

	@Autowired(required = false)
	private Collection<AuthorityGranter> granters;

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(AuthenticationProviderDefault.class);
		reg.put(AuthenticationTypeDefault.class);
		reg.put(ModuleConfigKeyAuthentication.class);
	}

	@Bean
	public ConverterUserDefault converterUserDefault() {
		ConverterUserDefault g = new ConverterUserDefault();
		return g;
	}

	@Bean
	public ConverterGroupDefault converterGroupDefault() {
		ConverterGroupDefault g = new ConverterGroupDefault();
		return g;
	}

	@Bean
	public AuthorityGranterProcessor authorityGranterProcessor() {
		AuthorityGranterProcessor g = new AuthorityGranterProcessor();
		g.setGranters(granters);
		return g;
	}

	@Bean
	public ConverterAuthenticationOAuth converterAuthenticationOAuth(ConverterAuthenticationToken token) {
		ConverterAuthenticationOAuth o = new ConverterAuthenticationOAuth();
		o.setConverter(token);
		return o;
	}

	@Bean
	public ConverterAuthenticationToken converterAuthenticationToken() {
		return new ConverterAuthenticationToken();
	}

	@Bean
	public UserDetailsServicePreAuth userDetailsServicePreAuth(AuthorityGranterProcessor a) {
		UserDetailsServicePreAuth p = new UserDetailsServicePreAuth();
		p.setProcessor(a);
		return p;
	}

	@Bean
	public ConverterAuthenticationSimple converterAuthenticationSimple(PasswordEncoder p) {
		ConverterAuthenticationSimple s = new ConverterAuthenticationSimple();
		s.setPasswordEncoder(p);
		return s;
	}

	@Bean
	public ConverterAuthenticationUsername converterAuthenticationUsername() {
		return new ConverterAuthenticationUsername();
	}

	@Bean
	public UserDetailsServiceUsername userDetailsServiceUsername(AuthorityGranterProcessor p) {
		UserDetailsServiceUsername u = new UserDetailsServiceUsername();
		u.setProcessor(p);
		return u;
	}

	@Bean
	public SoaAuthentication soaAuthenticationImpl(AuthenticationManager m, DtoConverterRegistry d) {
		SoaAuthenticationImpl s = new SoaAuthenticationImpl();
		s.setManager(m);
		s.setRegistry(d);
		return s;
	}
}
