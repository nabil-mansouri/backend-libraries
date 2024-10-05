package com.nm.app.history;

import java.io.Serializable;

/***
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface AdapterHistory extends Serializable {
	public Class<? extends DtoHistory> historyClass();

	public Class<? extends DtoHistoryActor> historyActorClass();

	public Class<? extends DtoHistorySubject> historySubjectClass();
}
