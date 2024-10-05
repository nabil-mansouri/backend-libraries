package com.nm.comms.converters;

import com.nm.comms.constants.CommunicationType;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.daos.DaoCommunication;
import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.MessageContentDto;
import com.nm.comms.dtos.MessageDtoAsync;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.comms.models.MessageContent;
import com.nm.utils.ApplicationUtils;
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
public class MessageAsyncConverterImpl extends DtoConverterDefault<MessageDtoAsync, Message> {

	public MessageDtoAsync toDto(Message entity, OptionsList options) throws DtoConvertException {
		try {
			MessageDtoAsync dto = new MessageDtoAsync();
			dto.setId(entity.getId());
			for (MessageContent c : entity.getContents()) {
				DtoConverter<MessageContentDto, MessageContent> converter = registry().search(dto.getContent(), c);
				if (c.getType().equals(MessagePartTypeDefault.Content)) {
					dto.setContent(converter.toDto(c, options));
				} else if (c.getType().equals(MessagePartTypeDefault.Title)) {
					dto.setTitle(converter.toDto(c, options));
				} else if (c.getType().equals(MessagePartTypeDefault.Joined)) {
					dto.getJoinded().add(converter.toDto(c, options));
				}
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public MessageDtoAsync toDto(MessageDtoAsync dto, Message entity, OptionsList options) throws DtoConvertException {
		return toDto(entity, options);
	}

	public Message toEntity(MessageDtoAsync dto, OptionsList options) throws DtoConvertException {
		try {
			Message any = new Message();
			if (dto.getId() != null) {
				any = AbstractGenericDao.get(Message.class).get(dto.getId());
			}
			any.getContents().clear();
			//
			DtoConverter<MessageContentDto, MessageContent> converter = registry().search(dto.getContent(),
					MessageContent.class);
			any.getContents()
					.add(converter.toEntity(dto.getContent(), options).setType(MessagePartTypeDefault.Content));
			if (dto.isHasTitle()) {
				converter = registry().search(dto.getTitle(), MessageContent.class);
				any.getContents()
						.add(converter.toEntity(dto.getTitle(), options).setType(MessagePartTypeDefault.Title));
			}
			//
			for (MessageContentDto c : dto.getJoinded()) {
				converter = registry().search(c, MessageContent.class);
				any.getContents().add(converter.toEntity(c, options).setType(MessagePartTypeDefault.Joined));
			}
			//
			any.getReceivers().clear();
			for (CommunicationActorDto actor : dto.getReceivers()) {
				DtoConverter<CommunicationActorDto, CommunicationActor> converter1 = registry().search(actor,
						CommunicationActor.class);
				any.getReceivers().add(converter1.toEntity(actor, options));
			}
			//
			DaoCommunication daoCOmm = ApplicationUtils.getBean(DaoCommunication.class);
			any.setSender(daoCOmm.getOrCreateAny());
			//
			any.getMediums().clear();
			for (CommunicationType type : dto.getType()) {
				any.getMediums().add(daoCOmm.getOrCreateMedium(type));
			}
			//
			return any;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
