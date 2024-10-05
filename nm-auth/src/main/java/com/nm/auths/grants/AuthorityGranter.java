package com.nm.auths.grants;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.nm.auths.models.Authentication;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface AuthorityGranter {
	List<GrantedAuthority> buildUserAuthority(Authentication auth, List<GrantedAuthority> authorities);
}
