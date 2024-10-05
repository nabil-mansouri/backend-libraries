package com.nm.dictionnary.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.nm.dictionnary.constants.EnumDictionnaryDomain;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_dictionnary", schema = "mod_dictionnary", uniqueConstraints = @UniqueConstraint(columnNames = { "domain" }) )
public class Dictionnary extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "nm_app_dictionnary_seq", schema = "mod_dictionnary", sequenceName = "nm_app_dictionnary_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nm_app_dictionnary_seq")
	private Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private EnumDictionnaryDomain domain;

	@Override
	public Long getId() {
		return id;
	}

	public EnumDictionnaryDomain getDomain() {
		return domain;
	}

	public void setDomain(EnumDictionnaryDomain domain) {
		this.domain = domain;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
