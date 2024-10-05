package com.nm.tests.history;

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
public class AdapterHistoryTest implements AdapterHistory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Class<? extends DtoHistory> historyClass() {
		return DtoHistoryDefault.class;
	}

	public Class<? extends DtoHistoryActor> historyActorClass() {
		return DtoHistoryTest.class;
	}

	public Class<? extends DtoHistorySubject> historySubjectClass() {
		return DtoHistoryTest.class;
	}

}
