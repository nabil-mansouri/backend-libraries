package com.nm.comms.soa;

import java.io.Serializable;

import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.CommunicationSubjectDto;
//nabil_mansouri@bitbucket.org/nabil_mansouri/my-libraries.git
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationSubject;
import com.nm.templates.dtos.TemplateDto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface CommunicationAdapter extends Serializable {
 
	Class<? extends TemplateDto> templateDtoClass();

 
	Class<? extends CommunicationSubject> subjectClass();

	Class<? extends CommunicationSubjectDto> subjectDtoClass();

	Class<? extends CommunicationActor> actorClass();

	Class<? extends CommunicationActorDto> actorDtoClass();

	Class<? extends CommunicationDto> channelDtoClass();

}
