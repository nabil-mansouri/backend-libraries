package com.nm.comms.history;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nm.app.history.HistoryActor;
import com.nm.comms.models.CommunicationActor;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
@Entity
@Table(name = "nm_communication_history_actor")
public class HistoryActorComm extends HistoryActor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToOne(optional = false)
	private CommunicationActor actor;

	public CommunicationActor getActor() {
		return actor;
	}

	public void setActor(CommunicationActor actor) {
		this.actor = actor;
	}
}
