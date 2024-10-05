package com.nm.tests.history;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.nm.app.history.HistorySubject;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
@Entity
@Table(name = "test_app_history_subject")
public class HistorySubjectTest extends HistorySubject {
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
