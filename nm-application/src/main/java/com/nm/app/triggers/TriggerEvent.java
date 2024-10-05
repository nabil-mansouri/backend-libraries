package com.nm.app.triggers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_app_trigger_event", schema = "mod_app")
public class TriggerEvent extends Trigger {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private TriggerEventEnum event;

	public TriggerEventEnum getEvent() {
		return event;
	}

	public void setEvent(TriggerEventEnum event) {
		this.event = event;
	}

}
