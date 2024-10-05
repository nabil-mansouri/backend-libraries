package com.nm.social.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.google.inject.internal.Sets;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema = "mod_social", name = "nm_social_calendar", uniqueConstraints = @UniqueConstraint(columnNames = { "type",
		"uuid" }) )
public class SocialCalendar extends ModelTimeable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_social_calendar", sequenceName = "seq_social_calendar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_social_calendar")
	private Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AuthenticationProvider type;
	@Column(nullable = false)
	private String uuid;
	@ManyToMany()
	@JoinTable(schema = "mod_social", name = "nm_social_calendar_owner")
	private Collection<SocialUser> owners = Sets.newHashSet();

	public void addOwner(SocialUser u) {
		this.owners.add(u);
		u.getCalendars().add(this);
	}

	public Collection<SocialUser> getOwners() {
		return owners;
	}

	public void setOwners(Collection<SocialUser> owners) {
		this.owners = owners;
	}

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

}
