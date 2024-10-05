package com.nm.comms.history;

import com.nm.app.history.DtoHistoryActor;
import com.nm.comms.dtos.CommunicationActorDto;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DtoHistoryActorComm implements DtoHistoryActor {
	private Long id;
	private CommunicationActorDto actor;

	public CommunicationActorDto getActor() {
		return actor;
	}

	public DtoHistoryActorComm setActor(CommunicationActorDto actor) {
		this.actor = actor;
		return this;
	}

	public Long getId() {
		return id;
	}

	public DtoHistoryActorComm setId(Long id) {
		this.id = id;
		return this;
	}

}
