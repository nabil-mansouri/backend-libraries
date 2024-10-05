package com.nm.users.dtos;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserFilter {
	private Long id;
	private Long idAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserFilter withId(Long id) {
		setId(id);
		return this;
	}

	public Long getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Long idAccount) {
		this.idAccount = idAccount;
	}

}
