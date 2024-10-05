package com.nm.users.dtos;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserGroupDetailForm {
	private Long id;
	private Collection<UserForm> users = new ArrayList<UserForm>();
	private Collection<UserForm> available = new ArrayList<UserForm>();

	public Collection<UserForm> getAvailable() {
		return available;
	}

	public void setAvailable(Collection<UserForm> available) {
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<UserForm> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserForm> users) {
		this.users = users;
	}

}
