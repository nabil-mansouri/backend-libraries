package com.nm.social.converters.google;

import java.nio.channels.NotYetBoundException;
import java.util.Collection;

import org.springframework.social.google.api.plus.Person;

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
public class GoogleUserConverter extends DtoConverterDefault<Person, SocialUser> {

	public Person toDto(Person dto, SocialUser entity, OptionsList options) throws DtoConvertException {
		throw new NotYetBoundException();
	}

	@Override
	public SocialUser toEntity(Person dto, OptionsList options) throws DtoConvertException {
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		SocialUser soc = new SocialUser();
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Google).withUUID(dto.getId()));
		if (users.isEmpty()) {
			soc.setType(AuthenticationProviderDefault.Google);
			soc.setUuid(dto.getId());
		} else {
			soc = users.iterator().next();
		}
		//
		soc.setEmail(dto.getAccountEmail());
		//
		SocialUserDto details = new SocialUserDto();
		details.setGoogle(dto);
		soc.setDetails(details);
		return soc;
	}

}
