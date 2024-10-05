package com.nm.tests.triggers;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.nm.app.triggers.TriggerSubject;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_app_trigger_subject_test")
@Inheritance(strategy = InheritanceType.JOINED)
public class TriggerSubjectTest extends TriggerSubject {

	private static final long serialVersionUID = 1978550118107172866L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
