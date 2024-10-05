package com.nm.comms.soa;

import java.util.Collection;

import com.nm.comms.CommunicationException;
import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.MessageContentDto;
import com.nm.comms.dtos.MessageDto;
import com.nm.comms.dtos.CommunicationSubjectDto;
import com.nm.comms.models.Message;
import com.nm.templates.constants.TemplateNameEnum;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaCommunication {

	public CommunicationDto create(CommunicationActorDto owner, CommunicationSubjectDto about,
			CommunicationAdapter adapter) throws CommunicationException;

	public CommunicationDto push(CommunicationDto channel, MessageDto comm,
			CommunicationAdapter adapter) throws CommunicationException;

	public Collection<Message> fetchLast(CommunicationDto channel, CommunicationActorDto actor);

	public void send(Collection<MessageDto> communications);

	public void saveTemplate(CommunicationActorDto owner, TemplateNameEnum name, MessageContentDto content);

}
