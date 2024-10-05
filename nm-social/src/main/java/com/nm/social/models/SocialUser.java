package com.nm.social.models;

import java.util.Collection;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.google.common.collect.Sets;
import com.google.inject.internal.Maps;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.constants.SocialEventStatus;
import com.nm.social.dtos.SocialUserDto;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema = "mod_social", name = "nm_social_user", uniqueConstraints = @UniqueConstraint(columnNames = { "type",
		"uuid" }) )
public class SocialUser extends ModelTimeable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_social_user", sequenceName = "seq_social_user", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_social_user")
	private Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AuthenticationProvider type;
	@Column(nullable = false)
	private String uuid;
	@Column(nullable = true)
	private String email;
	@Column(nullable = false, columnDefinition = "TEXT")
	@Type(type = HibernateFieldConverterUser.EE)
	private SocialUserDto details;
	@ManyToMany()
	@JoinTable(schema = "mod_social", name = "nm_social_user_friends")
	private Collection<SocialUser> friends = Sets.newHashSet();
	@ManyToMany(mappedBy = "users")
	private Collection<SocialNetwork> networks = Sets.newHashSet();
	@ManyToMany(mappedBy = "owners")
	private Collection<SocialNetwork> networksOwned = Sets.newHashSet();
	@MapKey(name = "status")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(schema = "mod_social", name = "nm_social_user_event_relation")
	private Map<SocialEventStatus, SocialEventRelation> events = Maps.newHashMap();
	@ManyToMany(mappedBy = "owners") 
	private Collection<SocialCalendar> calendars = Sets.newHashSet();

	public Collection<SocialCalendar> getCalendars() {
		return calendars;
	}

	public void setCalendars(Collection<SocialCalendar> calendars) {
		this.calendars = calendars;
	}

	public void addEvent(SocialEventStatus status, SocialEvent ev) {
		SocialEventRelation relation = this.events.getOrDefault(status, new SocialEventRelation());
		relation.setStatus(status);
		relation.getEvents().add(ev);
		this.events.put(status, relation);
	}

	public Map<SocialEventStatus, SocialEventRelation> getEvents() {
		return events;
	}

	public void setEvents(Map<SocialEventStatus, SocialEventRelation> events) {
		this.events = events;
	}

	public Collection<SocialNetwork> getNetworksOwned() {
		return networksOwned;
	}

	public void setNetworksOwned(Collection<SocialNetwork> networksOwned) {
		this.networksOwned = networksOwned;
	}

	public Collection<SocialNetwork> getNetworks() {
		return networks;
	}

	public void setNetworks(Collection<SocialNetwork> networks) {
		this.networks = networks;
	}

	public Collection<SocialUser> getFriends() {
		return friends;
	}

	public void setFriends(Collection<SocialUser> friends) {
		this.friends = friends;
	}

	public void addFriend(SocialUser user) {
		this.getFriends().add(user);
		user.getFriends().add(this);
	}

	public void addFriends(Collection<SocialUser> user) {
		for (SocialUser u : user) {
			this.addFriend(u);
		}
	}

	@Override
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SocialUserDto getDetails() {
		return details;
	}

	public void setDetails(SocialUserDto details) {
		this.details = details;
	}

	public void clear() {
		this.friends.clear();
	}
}
