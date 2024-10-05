package com.nm.social.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.social.converters.google.GoogleCalendarConverter;
import com.nm.social.converters.google.GoogleEventConverter;
import com.nm.social.converters.google.GoogleUserConverter;
import com.nm.social.operations.google.SocialOperationEventPosting;
import com.nm.social.operations.google.SocialOperationFriendsLoading;
import com.nm.social.operations.google.SocialOperationMeLoading;
import com.nm.utils.dtos.DtoConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationSocialOperationGoogle {
	@Bean
	public GoogleCalendarConverter googleCalendarConverter() {
		return new GoogleCalendarConverter();
	}

	@Bean
	public GoogleUserConverter googleUserConverter() {
		return new GoogleUserConverter();
	}

	@Bean
	public GoogleEventConverter googleEventConverter() {
		return new GoogleEventConverter();
	}

	@Bean
	public SocialOperationMeLoading socialOperationMeLoadingGoogle(DtoConverterRegistry reg) {
		SocialOperationMeLoading op = new SocialOperationMeLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationFriendsLoading socialOperationFriendsLoadingGoogle(DtoConverterRegistry reg) {
		SocialOperationFriendsLoading op = new SocialOperationFriendsLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationEventPosting SocialOperationEventPostingGoogle(DtoConverterRegistry reg) {
		SocialOperationEventPosting op = new SocialOperationEventPosting();
		op.setRegistry(reg);
		return op;
	}
	// NO NEED TO LOAD EVENTS FROM GOOGLE
	// @Bean
	// public SocialOperationEventLoading
	// socialOperationEventLoadingGoogle(DtoConverterRegistry reg) {
	// SocialOperationEventLoading op = new SocialOperationEventLoading();
	// op.setRegistry(reg);
	// return op;
	// }

}
