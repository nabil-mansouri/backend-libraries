package com.nm.tests.history;

import com.nm.app.history.DtoHistoryActor;
import com.nm.app.history.DtoHistorySubject;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DtoHistoryTest implements DtoHistorySubject, DtoHistoryActor {
	private String name;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public DtoHistoryTest setName(String name) {
		this.name = name;
		return this;
	}
}
