package com.nm.auths.models;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema="mod_auth",name = "user_user")
public class User {

	@Id
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	private Long id;
	private String name;
	@ManyToMany(mappedBy = "users")
	private Collection<UserGroup> groups = new HashSet<UserGroup>();
	@OneToMany(mappedBy = "user")
	@Size(min = 1, message = "user.auth.size")
	private Collection<Authentication> authentications = new HashSet<Authentication>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Authentication> getAuthentications() {
		return authentications;
	}

	public void setAuthentications(Collection<Authentication> authentications) {
		this.authentications = authentications;
	}

	public Collection<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(Collection<UserGroup> groups) {
		this.groups = groups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
