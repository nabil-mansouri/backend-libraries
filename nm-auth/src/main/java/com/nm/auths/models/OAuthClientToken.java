package com.nm.auths.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MUST BE IN DEFAuLT SCHEMA (BECAUSE OF SPRING SQL)
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "oauth_client_token")
public class OAuthClientToken {
	@Column(name = "token_id", length = 256)
	private String tokenId;
	@Column(name = "token", columnDefinition = "bytea")
	private byte[] token;
	@Id
	@Column(name = "authentication_id")
	private String authenticationId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "client_id")
	private String clientId;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
