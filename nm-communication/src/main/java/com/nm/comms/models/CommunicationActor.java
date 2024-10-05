package com.nm.comms.models;

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
@Table(name = "nm_communication_actor")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CommunicationActor extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_communication_actor", sequenceName = "seq_communication_actor", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_communication_actor")
	private Long id;

	//

	public Long getId() {
		return id;
	}

	public CommunicationActor setId(Long id) {
		this.id = id;
		return this;
	}

}
