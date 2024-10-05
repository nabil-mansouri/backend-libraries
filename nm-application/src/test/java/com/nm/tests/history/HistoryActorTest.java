package com.nm.tests.history;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.nm.app.history.HistoryActor;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
@Entity
@Table(name = "test_app_history_actor")
public class HistoryActorTest extends HistoryActor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
