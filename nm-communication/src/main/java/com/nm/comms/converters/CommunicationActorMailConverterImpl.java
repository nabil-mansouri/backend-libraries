package com.nm.comms.converters;

import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CommunicationActorMailConverterImpl
		extends DtoConverterDefault<CommunicationActorDtoImpl, CommunicationActorMail> {

	public CommunicationActorDtoImpl toDto(CommunicationActorMail entity, OptionsList options) {
		CommunicationActorDtoImpl dto = new CommunicationActorDtoImpl();
		dto.setId(entity.getId());
		dto.setMail(entity.getEmail());
		return dto;
	}

	public CommunicationActorDtoImpl toDto(CommunicationActorDtoImpl dto, CommunicationActorMail entity,
			OptionsList options) throws DtoConvertException {
		return toDto(entity, options);
	}

	public CommunicationActorMail toEntity(CommunicationActorDtoImpl dto, OptionsList options)
			throws DtoConvertException {
		try {
			CommunicationActorMail any = new CommunicationActorMail();
			if (dto.getId() != null) {
				any = AbstractGenericDao.get(CommunicationActorMail.class).get(dto.getId());
			}
			any.setEmail(dto.getMail());
			return any;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
