package com.nm.app.history;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_app_history_actor", schema = "mod_app")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class HistoryActor extends ModelTimeable {

	private static final long serialVersionUID = 1978550118107172866L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_app_history_actor", schema = "mod_app", sequenceName = "seq_nm_app_history_actor", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_app_history_actor")
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//

}
