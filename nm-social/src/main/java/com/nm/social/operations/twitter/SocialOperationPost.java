package com.nm.social.operations.twitter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialNetwork;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth1;
import com.nm.social.operations.args.SocialMessageInfo;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialOperationPost extends SocialOperationAbstract implements SocialOperationOAuth1 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Twitter);
	}

	public Collection<String> scopes() {
		return Arrays.asList();
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.PublishPost);
	}

	public SocialUser operation(OAuthConsumerToken op, Object... params) throws Exception {
		final TwitterTemplate template = template(op);
		List<SocialMessageInfo> mess = extractParams(SocialMessageInfo.class, params);
		OptionsList opt = new OptionsList();
		opt.pushOverrides(SocialUser.class, me(template));
		for (SocialMessageInfo m : mess) {
			DtoConverter<DtoPostData, SocialMessageInfo> converter = registry.search(DtoPostData.class, m.getClass());
			DtoPostData data = converter.toDto(m, opt);
			if (m.postIntoWall()) {
				template.timelineOperations().updateStatus(data);
			}
			//
			for (SocialNetwork n : m.networks()) {
				if (n.getDetails().getTwitter() != null) {
					if (m.sendDirectMessage()) {
						for (SocialUser member : n.getUsers()) {
							template.directMessageOperations().sendDirectMessage(Long.valueOf(member.getUuid()),
									data.getStringMessage());
						}
					}
				}
			}
		}
		SocialUser currentModel = me(template(op));
		return currentModel;
	}
}
