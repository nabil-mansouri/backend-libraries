package com.nm.social.converters.google;

import java.nio.channels.NotYetBoundException;
import java.util.Collection;

import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.daos.QueryBuilderSocialEvent;
import com.nm.social.dtos.SocialEventDto;
import com.nm.social.models.SocialEvent;
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
public class GoogleEventConverter
		extends DtoConverterDefault<com.google.api.services.calendar.model.Event, SocialEvent> {

	public com.google.api.services.calendar.model.Event toDto(com.google.api.services.calendar.model.Event dto,
			SocialEvent entity, OptionsList options) throws DtoConvertException {
		throw new NotYetBoundException();
	}

	@Override
	public SocialEvent toEntity(com.google.api.services.calendar.model.Event dto, OptionsList options)
			throws DtoConvertException {
		IGenericDao<SocialEvent, Long> dao = AbstractGenericDao.get(SocialEvent.class);
		SocialEvent soc = new SocialEvent();
		Collection<SocialEvent> users = dao.find(QueryBuilderSocialEvent.get()
				.withAuthProvider(AuthenticationProviderDefault.Google).withUUID(dto.getId()));
		if (users.isEmpty()) {
			soc.setType(AuthenticationProviderDefault.Google);
			soc.setUuid(dto.getId());
		} else {
			soc = users.iterator().next();
		}
		//
		SocialEventDto details = new SocialEventDto();
		details.setGoogle(dto);
		soc.setDetails(details);
		return soc;
	}

}
