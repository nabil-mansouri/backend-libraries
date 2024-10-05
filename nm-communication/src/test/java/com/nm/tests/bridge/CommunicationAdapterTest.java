package com.nm.tests.bridge;

import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.dtos.CommunicationDto;
import com.nm.comms.dtos.CommunicationDtoImpl;
import com.nm.comms.dtos.CommunicationSubjectDto;
import com.nm.comms.dtos.CommunicationSubjectDtoImpl;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.CommunicationActorMail;
import com.nm.comms.models.CommunicationSubject;
import com.nm.comms.models.CommunicationSubjectSimple;
import com.nm.comms.soa.CommunicationAdapter;
import com.nm.templates.dtos.TemplateDto;
import com.nm.templates.dtos.TemplateDtoImpl;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CommunicationAdapterTest implements CommunicationAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Class<? extends TemplateDto> templateDtoClass() {
		return TemplateDtoImpl.class;
	}

	public Class<? extends CommunicationSubject> subjectClass() {
		return CommunicationSubjectSimple.class;
	}

	public Class<? extends CommunicationActor> actorClass() {
		return CommunicationActorMail.class;
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
