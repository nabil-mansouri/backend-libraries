package com.nm.comms.history;

import com.nm.app.history.AdapterHistory;
import com.nm.app.history.DtoHistory;
import com.nm.app.history.DtoHistoryActor;
import com.nm.app.history.DtoHistoryDefault;
import com.nm.app.history.DtoHistorySubject;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class AdapterHistoryComm implements AdapterHistory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Class<? extends DtoHistory> historyClass() {
		return DtoHistoryDefault.class;
	}

	public Class<? extends DtoHistoryActor> historyActorClass() {
		return DtoHistoryActorComm.class;
	}

	public Class<? extends DtoHistorySubject> historySubjectClass() {
		return DtoHistorySubjectComm.class;
	}

}
