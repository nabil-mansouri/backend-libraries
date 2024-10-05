package com.nm.auths.oauth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.nm.auths.models.Authentication;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class IDUserDetails extends User {
	private Long userId;

	public IDUserDetails(Authentication id, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		setUserId(id.getUser().getId());
	}

	public IDUserDetails(Authentication id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		setUserId(id.getUser().getId());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long id) {
		this.userId = id;
	}
}
