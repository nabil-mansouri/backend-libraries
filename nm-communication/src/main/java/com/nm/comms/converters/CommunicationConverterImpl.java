package com.nm.comms.converters;

import com.nm.comms.constants.CommunicationOptions;
import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.CommunicationDtoImpl;
import com.nm.comms.dtos.CommunicationSubjectDto;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationSubject;
import com.nm.comms.soa.CommunicationAdapter;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CommunicationConverterImpl extends DtoConverterDefault<CommunicationDtoImpl, Communication> {

	public CommunicationDtoImpl toDto(Communication entity, OptionsList options) throws DtoConvertException {
		CommunicationAdapter adapter = options.get(CommunicationOptions.ADAPTER_KEY, CommunicationAdapter.class);
		CommunicationDtoImpl dto = new CommunicationDtoImpl();
		dto.setId(entity.getId());
		//
		try {
			DtoConverter<CommunicationActorDto, CommunicationActor> converter = registry().search(adapter.actorDtoClass(),
					entity.getOwner().getClass());
			CommunicationActorDto actr = converter.toDto(entity.getOwner(), options);
			dto.setOwner(actr);
			//
			DtoConverter<CommunicationSubjectDto, CommunicationSubject> converter2 = registry().search(adapter.subjectDtoClass(),
					entity.getAbout().getClass());
			CommunicationSubjectDto sub = converter2.toDto(entity.getAbout(), options);
			dto.setSubject(sub);
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public CommunicationDtoImpl toDto(CommunicationDtoImpl dto, Communication entity, OptionsList options) throws DtoConvertException {
		return toDto(entity, options);
	}

	public Communication toEntity(CommunicationDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			Communication any = new Communication();
			if (dto.getId() != null) {
				any = AbstractGenericDao.get(Communication.class).get(dto.getId());
			}
			//
			CommunicationActor actr = registry().search(dto.getOwner(), CommunicationActor.class).toEntity(dto.getOwner(), options);
			any.setOwner(actr);
			//
			CommunicationSubject sub = registry().search(dto.getSubject(), CommunicationSubject.class).toEntity(dto.getSubject(), options);
			any.setAbout(sub);
			return any;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
