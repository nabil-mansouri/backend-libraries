package com.nm.comms;

import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.dtos.MessageDto;
import com.nm.comms.dtos.MessageDtoAsync;

/**
 * 
 * @author Nabil
 * 
 */
public class AdapterCommunicationDefault implements AdapterCommunication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Class<? extends CommunicationActorDto> actorDtoClass() {
		return CommunicationActorDtoImpl.class;
	}

	public Class<? extends MessageDto> messageDtoClass() {
		return MessageDtoAsync.class;
	}

}
