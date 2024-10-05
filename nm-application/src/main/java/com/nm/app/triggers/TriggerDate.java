package com.nm.app.triggers;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_app_trigger_date", schema = "mod_app")
public class TriggerDate extends Trigger {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
