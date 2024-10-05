package com.nm.social.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.collect.Sets;
import com.nm.social.constants.SocialEventStatus;
import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema = "mod_social", name = "nm_social_event_relation")
public class SocialEventRelation extends ModelTimeable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_social_event_relation", sequenceName = "seq_social_event_relation", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_social_event_relation")
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialEventStatus status;
	@ManyToMany()
	@JoinTable(schema = "mod_social", name = "nm_social_event_relation_events")
	private Collection<SocialEvent> events = Sets.newHashSet();

	public Collection<SocialEvent> getEvents() {
		return events;
	}

	public void setEvents(Collection<SocialEvent> events) {
		this.events = events;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SocialEventStatus getStatus() {
		return status;
	}

	public void setStatus(SocialEventStatus status) {
		this.status = status;
	}

}
