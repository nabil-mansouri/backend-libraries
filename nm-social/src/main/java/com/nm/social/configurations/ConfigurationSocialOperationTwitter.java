package com.nm.social.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.social.converters.twitter.TwitterListConverter;
import com.nm.social.converters.twitter.TwitterSocialMessageConverter;
import com.nm.social.converters.twitter.TwitterUserConverter;
import com.nm.social.operations.twitter.SocialOperationFriendsLoading;
import com.nm.social.operations.twitter.SocialOperationListLoading;
import com.nm.social.operations.twitter.SocialOperationMeLoading;
import com.nm.social.operations.twitter.SocialOperationPost;
import com.nm.utils.dtos.DtoConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationSocialOperationTwitter {
	@Bean
	public TwitterUserConverter twitterUserConverter() {
		return new TwitterUserConverter();
	}

	@Bean
	public TwitterListConverter twitterListConverter() {
		return new TwitterListConverter();
	}

	@Bean
	public TwitterSocialMessageConverter twitterSocialMessageConverter() {
		TwitterSocialMessageConverter op = new TwitterSocialMessageConverter();
		return op;
	}

	@Bean
	public SocialOperationMeLoading socialOperationMeLoadingTwitter(DtoConverterRegistry reg) {
		SocialOperationMeLoading op = new SocialOperationMeLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationFriendsLoading socialOperationFriendsLoadingTwitter(DtoConverterRegistry reg) {
		SocialOperationFriendsLoading op = new SocialOperationFriendsLoading();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationPost socialOperationPostTwitter(DtoConverterRegistry reg) {
		SocialOperationPost op = new SocialOperationPost();
		op.setRegistry(reg);
		return op;
	}

	@Bean
	public SocialOperationListLoading socialOperationListLoadingTwitter(DtoConverterRegistry reg) {
		SocialOperationListLoading op = new SocialOperationListLoading();
		op.setRegistry(reg);
		return op;
	}

}
