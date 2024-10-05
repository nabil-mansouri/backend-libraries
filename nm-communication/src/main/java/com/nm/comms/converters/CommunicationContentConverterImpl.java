package com.nm.comms.converters;

import java.util.Collection;

import com.nm.comms.dtos.MessageContentDtoImpl;
import com.nm.comms.models.MessageContent;
import com.nm.comms.models.MessageContentFile;
import com.nm.comms.models.MessageContentTemplate;
import com.nm.comms.models.MessageContentText;
import com.nm.datas.daos.DaoAppData;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.templates.dtos.TemplateDto;
import com.nm.templates.models.Template;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CommunicationContentConverterImpl extends DtoConverterDefault<MessageContentDtoImpl, MessageContent> {
	private DaoAppData dao;

	public void setDao(DaoAppData dao) {
		this.dao = dao;
	}

	public Collection<Class<? extends MessageContent>> managedEntity() {
		return ListUtils.all(MessageContent.class, MessageContentFile.class, MessageContentText.class,
				MessageContentTemplate.class);
	}

	public MessageContentDtoImpl toDto(MessageContent entity, OptionsList options) throws DtoConvertException {
		try {
			MessageContentDtoImpl dto = new MessageContentDtoImpl();
			dto.setId(entity.getId());
			if (entity instanceof MessageContentText) {
				dto.setContentText(((MessageContentText) entity).getText());
			} else if (entity instanceof MessageContentTemplate) {
				DtoConverter<TemplateDto, Template> conv = registry().search(dto.getTemplate(), Template.class);
				dto.setTemplate(conv.toDto(((MessageContentTemplate) entity).getTemplate(), options));
			} else if (entity instanceof MessageContentFile) {
				dto.setContent(new AppDataDtoImpl(((MessageContentFile) entity).getData().getId()));
			} else {
				throw new IllegalArgumentException("Could not found entity for type:" + entity.getClass());
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public MessageContentDtoImpl toDto(MessageContentDtoImpl dto, MessageContent entity, OptionsList options)
			throws DtoConvertException {
		return toDto(entity, options);
	}

	public MessageContent toEntity(MessageContentDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			switch (dto.getType()) {
			case File: {
				MessageContentFile entity = new MessageContentFile();
				entity.setData(dao.load(dto.getContent().getId()));
				return entity;
			}
			case Template: {
				MessageContentTemplate entity = new MessageContentTemplate();
				DtoConverter<TemplateDto, Template> conv = registry().search(dto.getTemplate(), Template.class);
				entity.setTemplate(conv.toEntity(dto.getTemplate(), options));
				return entity;
			}
			case Text:
				MessageContentText entity = new MessageContentText();
				entity.setText(dto.getContentText());
				return entity;
			}
			throw new IllegalArgumentException("Could not found entity for type:" + dto.getType());
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
