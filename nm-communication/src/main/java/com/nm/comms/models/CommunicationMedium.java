package com.nm.comms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.comms.constants.CommunicationType;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_communication_medium")
public class CommunicationMedium extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_communication_medium", sequenceName = "seq_communication_medium", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_communication_medium")
	private Long id;
	//
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private CommunicationType type;

	public CommunicationType getType() {
		return type;
	}

	public CommunicationMedium setType(CommunicationType type) {
		this.type = type;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
