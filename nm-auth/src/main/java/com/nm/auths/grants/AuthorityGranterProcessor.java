package com.nm.auths.grants;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.google.inject.internal.Lists;
import com.nm.auths.models.Authentication;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AuthorityGranterProcessor {
	private Collection<AuthorityGranter> granters;

	public void setGranters(Collection<AuthorityGranter> granters) {
		this.granters = granters;
	}

	public List<GrantedAuthority> processor(Authentication auth) {
		List<GrantedAuthority> li = Lists.newArrayList();
		if (granters != null) {
			for (AuthorityGranter g : granters) {
				g.buildUserAuthority(auth, li);
			}
		}
		return li;
	}
}
