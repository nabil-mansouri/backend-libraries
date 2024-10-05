package com.nm.comms.soa;

import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.CommunicationDtoImpl;
import com.nm.comms.dtos.CommunicationSubjectDto;
import com.nm.comms.dtos.CommunicationSubjectDtoImpl;
//nabil_mansouri@bitbucket.org/nabil_mansouri/my-libraries.git
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorAnonymous;
import com.nm.comms.models.CommunicationSubject;
import com.nm.comms.models.CommunicationSubjectAnything;
import com.nm.templates.dtos.TemplateDto;
import com.nm.templates.dtos.TemplateDtoImpl;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CommunicationAdapterDefault implements CommunicationAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	public Class<? extends TemplateDto> templateDtoClass() {
		return TemplateDtoImpl.class;
	}
 
	public Class<? extends CommunicationSubject> subjectClass() {
		return CommunicationSubjectAnything.class;
	}

	public Class<? extends CommunicationActor> actorClass() {
		return CommunicationActorAnonymous.class;
	}

	public Class<? extends CommunicationDto> channelDtoClass() {
		return CommunicationDtoImpl.class;
	}

	public Class<? extends CommunicationSubjectDto> subjectDtoClass() {
		return CommunicationSubjectDtoImpl.class;
	}

	public Class<? extends CommunicationActorDto> actorDtoClass() {
		return CommunicationActorDtoImpl.class;
	}

}
