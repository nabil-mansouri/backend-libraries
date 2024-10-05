package com.nm.auths.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoAuthenticationDefault implements DtoAuthentication, DtoUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String fullName;
	private String username;
	private String password;
	private String oldPassword;
	private Long userId;

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(Long id) {
		this.userId = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public DtoAuthenticationDefault() {
	}

	public DtoAuthenticationDefault(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nm.auths.dtos.AuthenticationDto#getId()
	 */
	public Long getId() {
		return id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
