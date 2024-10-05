package com.nm.comms.soa;

import java.util.Collection;

import com.nm.app.triggers.SoaTrigger;
import com.nm.comms.CommunicationException;
import com.nm.comms.constants.CommunicationOptions;
import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.CommunicationSubjectDto;
import com.nm.comms.dtos.MessageContentDto;
import com.nm.comms.dtos.MessageDto;
import com.nm.comms.models.Communication;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationSubject;
import com.nm.comms.models.Message;
import com.nm.templates.constants.TemplateNameEnum;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaCommunicationImpl implements SoaCommunication {

	private SoaTrigger soaTrigger;
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void setSoaTrigger(SoaTrigger soaTrigger) {
		this.soaTrigger = soaTrigger;
	}

	public CommunicationDto create(CommunicationActorDto ownerDto, CommunicationSubjectDto subjectDto,
			CommunicationAdapter adapter) throws CommunicationException {
		try {
			OptionsList options = new OptionsList();
			options.put(CommunicationOptions.ADAPTER_KEY, adapter);
			DtoConverter<CommunicationSubjectDto, CommunicationSubject> converter = registry.search(subjectDto,
					adapter.subjectClass());
			CommunicationSubject subject = converter.toEntity(subjectDto, options);
			if (subject.getId() == null) {
				AbstractGenericDao.get(CommunicationSubject.class).insert(subject);
			}
			//
			DtoConverter<CommunicationActorDto, CommunicationActor> converter2 = registry.search(ownerDto,
					adapter.actorClass());
			CommunicationActor owner = converter2.toEntity(ownerDto, options);
			if (owner.getId() == null) {
				AbstractGenericDao.get(CommunicationActor.class).insert(owner);
			}
			//
			Communication channel = new Communication();
			channel.setAbout(subject);
			channel.setOwner(owner);
			AbstractGenericDao.get(Communication.class).saveOrUpdate(channel);
			//
			DtoConverter<CommunicationDto, Communication> converter3 = registry.search(adapter.channelDtoClass(),
					channel.getClass());
			return converter3.toDto(channel, options);
		} catch (Exception e) {
			throw new CommunicationException(e);
		}
	}

	public CommunicationDto push(CommunicationDto channel, MessageDto dto, CommunicationAdapter adapter)
			throws CommunicationException {
		try {
			IGenericDao<Communication, Long> dao = AbstractGenericDao.get(Communication.class);
			IGenericDao<Message, Long> dao1 = AbstractGenericDao.get(Message.class);
			DtoConverter<MessageDto, Message> converter = registry.search(dto, Message.class);
			OptionsList options = new OptionsList();
			dto.setChannelId(channel.getId());
			Message entity = converter.toEntity(dto, options);
			dao.get(channel.getId()).add(entity);
			dao1.saveOrUpdate(entity);
			//
			soaTrigger.saveOrUpdate(entity.getTrigger(), dto.getTrigger(), options);
			//
			dto.setId(entity.getId());
			return channel;
		} catch (Exception e) {
			throw new CommunicationException(e);
		}
	}

	public void saveTemplate(CommunicationActorDto owner, TemplateNameEnum name, MessageContentDto content) {
		// TODO Auto-generated method stub

	}

	public Collection<Message> fetchLast(CommunicationDto channel, CommunicationActorDto actor) {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(Collection<MessageDto> communications) {
		// TODO Auto-generated method stub

	}

}
