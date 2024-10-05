package com.nm.social.operations.twitter;

import java.util.Collection;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.social.twitter.api.TwitterProfile;

import com.google.api.services.plus.PlusScopes;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth1;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialOperationMeLoading extends SocialOperationAbstract implements SocialOperationOAuth1 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Twitter);
	}

	public Collection<String> scopes() {
		return PlusScopes.all();
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadMe);
	}

	public SocialUser operation(OAuthConsumerToken token, Object... params) throws Exception {
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		OptionsList op = new OptionsList();
		DtoConverter<TwitterProfile, SocialUser> conv = registry.search(TwitterProfile.class, SocialUser.class);
		TwitterProfile current = template(token).userOperations().getUserProfile();
		SocialUser currentModel = conv.toEntity(current, op);
		// SAVING
		dao.saveOrUpdate(currentModel);
		return currentModel;
	}

}
