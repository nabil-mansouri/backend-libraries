package com.nm.users.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "user_group")
public class UserGroup {

	@Id
	@SequenceGenerator(name = "user_group_seq", sequenceName = "user_group_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_group_seq")
	private Long id;
	@Column(updatable = false)
	private Date created = new Date();
	@UpdateTimestamp
	private Date updated = new Date();
	@Column(nullable = false)
	private String name;
	@ManyToMany()
	private List<User> users = new ArrayList<User>();

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getUpdated() {
		return updated;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void remove(User bean) {
		while (users.contains(bean)) {
			users.remove(bean);
		}
	}
}
