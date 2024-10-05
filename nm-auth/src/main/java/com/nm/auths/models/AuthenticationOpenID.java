package com.nm.auths.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationType;
import com.nm.auths.constants.AuthenticationType.AuthenticationTypeDefault;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(schema = "mod_auth", name = "nm_auth_authentication_openid", uniqueConstraints = @UniqueConstraint(columnNames = {
		"openid", "provider" }) )
@Inheritance(strategy = InheritanceType.JOINED)
public class AuthenticationOpenID extends Authentication {

	private static final long serialVersionUID = 1978550118107172866L;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String openid;
	@Column(columnDefinition = "TEXT")
	private String email;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AuthenticationProvider provider;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AuthenticationProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthenticationProvider provider) {
		this.provider = provider;
	}

	@Override
	public AuthenticationType getType() {
		return AuthenticationTypeDefault.OpenId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
