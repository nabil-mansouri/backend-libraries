package com.nm.social.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.dtos.SocialEventDto;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema="mod_social",name = "nm_social_event", uniqueConstraints = @UniqueConstraint(columnNames = { "type", "uuid" }) )
public class SocialEvent extends ModelTimeable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_social_event", sequenceName = "seq_social_event", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_social_event")
	private Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AuthenticationProvider type;
	@Column(nullable = false)
	private String uuid;
	@Column(nullable = false, columnDefinition = "TEXT")
	@Type(type = HibernateFieldConverterEvent.EE)
	private SocialEventDto details;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthenticationProvider getType() {
		return type;
	}

	public void setType(AuthenticationProvider type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public SocialEventDto getDetails() {
		return details;
	}

	public void setDetails(SocialEventDto details) {
		this.details = details;
	}

}
