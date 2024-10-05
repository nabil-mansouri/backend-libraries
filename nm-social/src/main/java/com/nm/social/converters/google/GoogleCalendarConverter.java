package com.nm.social.converters.google;

import java.nio.channels.NotYetBoundException;
import java.util.Collection;

import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.daos.QueryBuilderSocialCalendar;
import com.nm.social.models.SocialCalendar;
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
public class GoogleCalendarConverter
		extends DtoConverterDefault<com.google.api.services.calendar.model.Calendar, SocialCalendar> {

	public com.google.api.services.calendar.model.Calendar toDto(com.google.api.services.calendar.model.Calendar dto,
			SocialCalendar entity, OptionsList options) throws DtoConvertException {
		throw new NotYetBoundException();
	}

	@Override
	public SocialCalendar toEntity(com.google.api.services.calendar.model.Calendar dto, OptionsList options)
			throws DtoConvertException {
		IGenericDao<SocialCalendar, Long> dao = AbstractGenericDao.get(SocialCalendar.class);
		SocialCalendar soc = new SocialCalendar();
		Collection<SocialCalendar> users = dao.find(QueryBuilderSocialCalendar.get()
				.withAuthProvider(AuthenticationProviderDefault.Google).withUUID(dto.getId()));
		if (users.isEmpty()) {
			soc.setType(AuthenticationProviderDefault.Google);
			soc.setUuid(dto.getId());
		} else {
			soc = users.iterator().next();
		}
		return soc;
	}

}
