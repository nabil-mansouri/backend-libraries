package com.nm.auths.dtos;

import com.nm.auths.constants.AuthenticationProvider;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoAuthenticationOAuth implements DtoAuthentication, DtoUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String openid;
	private String email;
	private String fullName;
	private AuthenticationProvider provider;
	private Long userId;

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(Long id) {
		this.userId = id;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AuthenticationProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthenticationProvider provider) {
		this.provider = provider;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
