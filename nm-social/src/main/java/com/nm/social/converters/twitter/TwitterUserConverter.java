package com.nm.social.converters.twitter;

import java.nio.channels.NotYetBoundException;
import java.util.Collection;

import org.springframework.social.twitter.api.TwitterProfile;

import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.dtos.SocialUserDto;
import com.nm.social.models.SocialUser;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TwitterUserConverter extends DtoConverterDefault<TwitterProfile, SocialUser> {

	public TwitterProfile toDto(TwitterProfile dto, SocialUser entity, OptionsList options) throws DtoConvertException {
		throw new NotYetBoundException();
	}

	@Override
	public SocialUser toEntity(TwitterProfile dto, OptionsList options) throws DtoConvertException {
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		SocialUser soc = new SocialUser();
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Twitter).withUUID(dto.getId()));
		if (users.isEmpty()) {
			soc.setType(AuthenticationProviderDefault.Twitter);
			soc.setUuid(String.valueOf(dto.getId()));
		} else {
			soc = users.iterator().next();
		}
		//
		SocialUserDto details = new SocialUserDto();
		details.setTwitter(dto);
		soc.setDetails(details);
		return soc;
	}

}
