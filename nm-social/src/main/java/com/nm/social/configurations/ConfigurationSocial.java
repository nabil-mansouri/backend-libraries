package com.nm.social.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.nm.auths.oauth.manager.OAuthContextManager;
import com.nm.social.constants.SocialFieldsEnum.SocialFieldsDefault;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.converters.ConverterSocialState;
import com.nm.social.operations.SocialOperationLauncher;
import com.nm.social.soa.SoaSocial;
import com.nm.social.soa.SoaSocialImpl;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@Import(value = { ConfigurationSocialOperationFB.class, //
		ConfigurationSocialOperationGoogle.class, ConfigurationSocialOperationTwitter.class })
public class ConfigurationSocial {
	public static final String MODULE_NAME = "social";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(SocialFieldsDefault.class);
		reg.put(SocialOperationEnumDefault.class);
	}

	@Bean
	public SocialOperationLauncher socialOperationLauncher(OAuthContextManager manager) {
		SocialOperationLauncher s = new SocialOperationLauncher();
		s.setManager(manager);
		return s;
	}

	@Bean
	public ConverterSocialState ConverterSocialState() {
		ConverterSocialState s = new ConverterSocialState();
		return s;
	}

	@Bean
	public SoaSocial soaSocialImpl(SocialOperationLauncher launcher, DtoConverterRegistry registry) {
		SoaSocialImpl s = new SoaSocialImpl();
		s.setLauncher(launcher);
		s.setRegistry(registry);
		return s;
	}

}
