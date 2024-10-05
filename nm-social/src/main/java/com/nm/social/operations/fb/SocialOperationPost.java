package com.nm.social.operations.fb;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.facebook.api.PagePostData;
import org.springframework.social.facebook.api.PostData;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialNetwork;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth2;
import com.nm.social.operations.args.SocialMessageInfo;
import com.nm.social.templates.fb.FacebookTemplateCustom;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialOperationPost extends SocialOperationAbstract implements SocialOperationOAuth2 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Facebook);
	}

	public Collection<String> scopes() {
		return Arrays.asList("publish_actions", "publish_stream", "publish_pages");
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.PublishPost);
	}

	public SocialUser operation(OAuth2AccessToken operation, Object... params) throws Exception {
		final FacebookTemplateCustom template = template(operation);
		List<SocialMessageInfo> mess = extractParams(SocialMessageInfo.class, params);
		OptionsList opt = new OptionsList();
		opt.pushOverrides(FacebookTemplateCustom.class, template);
		SocialUser currentModel = me(template);
		opt.pushOverrides(SocialUser.class, currentModel);
		for (SocialMessageInfo m : mess) {
			DtoConverter<DtoPostData, SocialMessageInfo> converter = registry.search(DtoPostData.class, m.getClass());
			PostData data = converter.toDto(m, opt);
			if (m.postIntoWall()) {
				template.feedOperations().post(data);
			}
			//
			DtoConverter<PostData, PagePostData> conv = registry.search(PostData.class, PagePostData.class);
			for (SocialNetwork n : m.networks()) {
				if (n.getDetails().getFacebook() != null) {
					// NOT POSSIBLE
				} else if (n.getDetails().getFacebookPage() != null) {
					PagePostData p = new PagePostData(n.getDetails().getFacebookPage().getId());
					template.pageOperations().post(conv.toEntity(p, data, opt));
				}
			}
		}
		return currentModel;
	}
}
