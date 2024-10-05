package com.nm.comms.converters;

import java.util.Collection;

import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.dtos.CommunicationSubjectDtoImpl;
import com.nm.comms.dtos.MessageContentDto;
import com.nm.comms.models.CommunicationSubject;
import com.nm.comms.models.CommunicationSubjectAnything;
import com.nm.comms.models.CommunicationSubjectSimple;
import com.nm.comms.models.MessageContent;
import com.nm.utils.ListUtils;
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
public class CommunicationSubjectConverterImpl
		extends DtoConverterDefault<CommunicationSubjectDtoImpl, CommunicationSubject> {

	public Collection<Class<? extends CommunicationSubject>> managedEntity() {
		return ListUtils.all(CommunicationSubject.class, CommunicationSubjectAnything.class,
				CommunicationSubjectSimple.class);
	}

	public CommunicationSubjectDtoImpl toDto(CommunicationSubject entity, OptionsList options)
			throws DtoConvertException {
		try {
			CommunicationSubjectDtoImpl dto = new CommunicationSubjectDtoImpl();
			dto.setId(entity.getId());
			if (entity instanceof CommunicationSubjectSimple) {
				CommunicationSubjectSimple s = (CommunicationSubjectSimple) entity;
				DtoConverter<MessageContentDto, MessageContent> converter = registry().search(dto.getContent(),
						s.getContent());
				dto.setContent(converter.toDto(s.getContent(), options));
				dto.setAny(false);
			} else {
				dto.setAny(true);
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public CommunicationSubjectDtoImpl toDto(CommunicationSubjectDtoImpl dto, CommunicationSubject entity,
			OptionsList options) throws DtoConvertException {
		return toDto(entity, options);
	}

	public CommunicationSubject toEntity(CommunicationSubjectDtoImpl dto, OptionsList options)
			throws DtoConvertException {
		try {
			CommunicationSubject entity = new CommunicationSubjectSimple();
			if (dto.isAny()) {
				entity = new CommunicationSubjectAnything();
			}
			//
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(CommunicationSubject.class).get(dto.getId());
			}
			//
			if (entity instanceof CommunicationSubjectSimple) {
				CommunicationSubjectSimple s = (CommunicationSubjectSimple) entity;
				DtoConverter<MessageContentDto, MessageContent> converter = registry().search(dto.getContent(),
						MessageContent.class);
				s.setContent(converter.toEntity(dto.getContent(), options).setType(MessagePartTypeDefault.Title));
			}
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
