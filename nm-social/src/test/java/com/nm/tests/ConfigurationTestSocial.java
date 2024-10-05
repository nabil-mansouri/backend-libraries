package com.nm.tests;
/**
 * 
 * @author Nabil MANSOURI
 *
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.EnableApplicationModule;
import com.nm.auths.configurations.EnableAuthenticationModule;
import com.nm.config.EnableConfigModule;
import com.nm.datas.configurations.EnableDatasModule;
import com.nm.social.configurations.EnableSocialModule;
import com.nm.tests.fb.FakeConverterFacebook;
import com.nm.tests.fb.FakeConverterTwitter;
import com.nm.utils.configurations.EnableUtilsModule;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@EnableDatasModule()
@EnableConfigModule()
@EnableSocialModule()
@EnableApplicationModule()
@EnableAuthenticationModule()
@EnableUtilsModule(enableDefaultDS = true)
public class ConfigurationTestSocial {
	@Bean
	public FakeConverterFacebook fakeConverter() {
		return new FakeConverterFacebook();
	}

	@Bean
	public FakeConverterTwitter fakeConverterTwitter() {
		return new FakeConverterTwitter();
	}
}
