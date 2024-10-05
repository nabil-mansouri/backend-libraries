package com.nm.comms;

import java.io.Serializable;

import com.nm.comms.dtos.CommunicationActorDto;
import com.nm.comms.dtos.MessageDto;

/***
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface AdapterCommunication extends Serializable {
	public Class<? extends CommunicationActorDto> actorDtoClass();

	public Class<? extends MessageDto> messageDtoClass();
}
