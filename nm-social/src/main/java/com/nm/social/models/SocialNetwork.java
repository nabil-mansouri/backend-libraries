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

import com.google.common.collect.Sets;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.dtos.SocialNetworkDto;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema = "mod_social", name = "nm_social_network", uniqueConstraints = @UniqueConstraint(columnNames = { "type",
		"uuid" }) )
public class SocialNetwork extends ModelTimeable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_social_network", sequenceName = "seq_social_network", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_social_network")
	private Long id;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AuthenticationProvider type;
	@Column(nullable = false)
	private String uuid;
	@Column(nullable = false, columnDefinition = "TEXT")
	@Type(type = HibernateFieldConverterNetwork.EE)
	private SocialNetworkDto details;
	@ManyToMany()
	@JoinTable(schema = "mod_social", name = "nm_social_network_owners")
	private Collection<SocialUser> owners = Sets.newHashSet();
	@ManyToMany()
	@JoinTable(schema = "mod_social", name = "nm_social_network_users")
	private Collection<SocialUser> users = Sets.newHashSet();

	public Collection<SocialUser> getOwners() {
		return owners;
	}

	public void setOwners(Collection<SocialUser> owners) {
		this.owners = owners;
	}

	public Collection<SocialUser> getUsers() {
		return users;
	}

	public void setUsers(Collection<SocialUser> friends) {
		this.users = friends;
	}

	public void clearUsers() {
		this.users.clear();
	}

	public void clearOwners() {
		this.users.clear();
	}

	public void addOwners(SocialUser user) {
		this.getOwners().add(user);
		user.getNetworks().add(this);
	}

	public void addOwners(Collection<SocialUser> user) {
		for (SocialUser u : user) {
			this.addOwners(u);
		}
	}

	public void addUser(SocialUser user) {
		this.getUsers().add(user);
		user.getNetworks().add(this);
	}

	public void addUsers(Collection<SocialUser> user) {
		for (SocialUser u : user) {
			this.addUser(u);
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

	public SocialNetworkDto getDetails() {
		return details;
	}

	public void setDetails(SocialNetworkDto details) {
		this.details = details;
	}

}
