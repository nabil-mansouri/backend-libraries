package com.nm.auths.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.nm.auths.constants.AuthenticationType;
import com.nm.auths.constants.AuthenticationType.AuthenticationTypeDefault;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(schema="mod_auth",name = "nm_auth_authentication_simple", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }) )
@Inheritance(strategy = InheritanceType.JOINED)
public class AuthenticationSimple extends Authentication {

	private static final long serialVersionUID = 1978550118107172866L;
	@Column(nullable = false, length = 512)
	private String username;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String password;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public AuthenticationType getType() {
		return AuthenticationTypeDefault.UsernamePwd;
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
