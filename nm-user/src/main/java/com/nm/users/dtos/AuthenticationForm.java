package com.nm.users.dtos;

import java.io.Serializable;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class AuthenticationForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String login;
	private String password;
	private String confirmation;
	private boolean hasChanged;
	private boolean enable;
	private UserGroupForm group = new UserGroupForm();

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public UserGroupForm getGroup() {
		return group;
	}

	public void setGroup(UserGroupForm group) {
		this.group = group;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public boolean isHasChanged() {
		return hasChanged;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
