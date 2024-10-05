package com.nm.social.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.social.converters.fb.FacebookEventConverter;
import com.nm.social.converters.fb.FacebookGroupConverter;
import com.nm.social.converters.fb.FacebookPageConverter;
import com.nm.social.converters.fb.FacebookPageDataConverter;
import com.nm.social.converters.fb.FacebookSocialMessageConverter;
import com.nm.social.converters.fb.FacebookUserConverter;
import com.nm.social.operations.fb.SocialOperationEventsLoading;
import com.nm.social.operations.fb.SocialOperationFriendsLoading;
import com.nm.social.operations.fb.SocialOperationGroupLoading;
import com.nm.social.operations.fb.SocialOperationMeLoading;
import com.nm.social.operations.fb.SocialOperationPageLoading;
import com.nm.social.operations.fb.SocialOperationPost;
import com.nm.utils.dtos.DtoConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationSocialOperationFB {

	@Bean
	public SocialOperationEventsLoading socialOperationEventsLoadingFB(DtoConverterRegistry reg) {
		SocialOperationEventsLoading op = new SocialOperationEventsLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public FacebookSocialMessageConverter FacebookSocialMessageConverter() {
		FacebookSocialMessageConverter op = new FacebookSocialMessageConverter();
		return op;
	}

	@Bean
	public SocialOperationMeLoading socialOperationMeLoadingFB(DtoConverterRegistry reg) {
		SocialOperationMeLoading op = new SocialOperationMeLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationFriendsLoading socialOperationFriendsLoadingFB(DtoConverterRegistry reg) {
		SocialOperationFriendsLoading op = new SocialOperationFriendsLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationGroupLoading socialOperationGroupLoadingFB(DtoConverterRegistry reg) {
		SocialOperationGroupLoading op = new SocialOperationGroupLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationPageLoading socialOperationPageLoadingFB(DtoConverterRegistry reg) {
		SocialOperationPageLoading op = new SocialOperationPageLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationPost socialOperationPostFB(DtoConverterRegistry reg) {
		SocialOperationPost op = new SocialOperationPost();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public FacebookEventConverter facebookEventConverter() {
		return new FacebookEventConverter();
	}

	@Bean
	public FacebookUserConverter facebookUserConverter() {
		return new FacebookUserConverter();
	}

	@Bean
	public FacebookGroupConverter facebookGroupConverter() {
		return new FacebookGroupConverter();
	}

	@Bean
	public FacebookPageConverter facebookPageConverter() {
		return new FacebookPageConverter();
	}

	@Bean
	public FacebookPageDataConverter facebookPageDataConverter() {
		return new FacebookPageDataConverter();
	}
}
