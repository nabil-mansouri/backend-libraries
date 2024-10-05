package com.nm.auths.openid;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.nm.auths.constants.AuthenticationState;
import com.nm.auths.daos.QueryBuilderAuthentication;
import com.nm.auths.grants.AuthorityGranterProcessor;
import com.nm.auths.models.AuthenticationOpenID;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OpenIdUserDetailsServiceImpl implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	private AuthorityGranterProcessor processor;

	public void setProcessor(AuthorityGranterProcessor processor) {
		this.processor = processor;
	}

	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
		QueryBuilderAuthentication query = QueryBuilderAuthentication.getOpenID().withState(AuthenticationState.Enabled)
				.withOpenID(token.getName());
		//
		AuthenticationOpenID user;
		try {
			user = AbstractGenericDao.get(AuthenticationOpenID.class).findFirst(query);
			List<GrantedAuthority> authorities = processor.processor(user);
			return new OpenIDUser(user, user.getOpenid(), authorities);
		} catch (NoDataFoundException e) {
			throw new UsernameNotFoundException("Failed to find user", e);
		}
	}

}