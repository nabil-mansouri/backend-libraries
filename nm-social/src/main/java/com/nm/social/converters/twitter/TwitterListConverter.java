package com.nm.social.converters.twitter;

import java.nio.channels.NotYetBoundException;
import java.util.Collection;

import org.springframework.social.twitter.api.UserList;

import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.daos.QueryBuilderSocialNetwork;
import com.nm.social.dtos.SocialNetworkDto;
import com.nm.social.models.SocialNetwork;
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
public class TwitterListConverter extends DtoConverterDefault<UserList, SocialNetwork> {

	public UserList toDto(UserList dto, SocialNetwork entity, OptionsList options) throws DtoConvertException {
		throw new NotYetBoundException();
	}

	@Override
	public SocialNetwork toEntity(UserList dto, OptionsList options) throws DtoConvertException {
		IGenericDao<SocialNetwork, Long> dao = AbstractGenericDao.get(SocialNetwork.class);
		SocialNetwork soc = new SocialNetwork();
		Collection<SocialNetwork> networks = dao.find(QueryBuilderSocialNetwork.get()
				.withAuthProvider(AuthenticationProviderDefault.Twitter).withUUID(String.valueOf(dto.getId())));
		if (networks.isEmpty()) {
			soc.setType(AuthenticationProviderDefault.Twitter);
			soc.setUuid(String.valueOf(dto.getId()));
		} else {
			soc = networks.iterator().next();
		}
		//
		SocialNetworkDto details = new SocialNetworkDto();
		details.setTwitter(dto);
		soc.setDetails(details);
		return soc;
	}

}
