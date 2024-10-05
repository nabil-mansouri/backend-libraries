package com.nm.auths.openid;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.nm.auths.models.Authentication;
import com.nm.auths.oauth.IDUserDetails;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OpenIDUser extends IDUserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String openIdIdentifier = null;

	public OpenIDUser(Authentication id, String openId, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(id, openId, openId, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		setOpenIdIdentifier(openId);
	}

	public OpenIDUser(Authentication id, String openId, Collection<? extends GrantedAuthority> authorities) {
		super(id, openId, openId, authorities);
		setOpenIdIdentifier(openId);
	}

	public String getOpenIdIdentifier() {
		return openIdIdentifier;
	}

	public void setOpenIdIdentifier(String openIdIdentifier) {
		this.openIdIdentifier = openIdIdentifier;
	}
}