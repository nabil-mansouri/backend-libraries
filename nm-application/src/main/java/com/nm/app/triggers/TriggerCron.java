package com.nm.app.triggers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_app_trigger_cron", schema = "mod_app")
public class TriggerCron extends Trigger {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	private String cron;

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	@Override
	public String toString() {
		return "CommunicationTimeCron [next=" + ", cron=" + cron + "]";
	}
}
