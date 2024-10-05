package com.nm.auths.simple;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nm.auths.constants.AuthenticationState;
import com.nm.auths.daos.QueryBuilderAuthentication;
import com.nm.auths.grants.AuthorityGranterProcessor;
import com.nm.auths.models.AuthenticationSimple;
import com.nm.auths.oauth.IDUserDetails;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class UserDetailsServiceUsername implements UserDetailsService {
	private AuthorityGranterProcessor processor;

	public void setProcessor(AuthorityGranterProcessor processor) {
		this.processor = processor;
	}

	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		QueryBuilderAuthentication query = QueryBuilderAuthentication.getSimple().withState(AuthenticationState.Enabled)
				.withUsername(username);
		try {
			AuthenticationSimple user = AbstractGenericDao.get(AuthenticationSimple.class).findFirst(query);
			List<GrantedAuthority> authorities = processor.processor(user);
			return new IDUserDetails(user, user.getUsername(), user.getPassword(), authorities);
		} catch (NoDataFoundException e) {
			throw new UsernameNotFoundException("Failed to find user", e);
		}

	}

}
