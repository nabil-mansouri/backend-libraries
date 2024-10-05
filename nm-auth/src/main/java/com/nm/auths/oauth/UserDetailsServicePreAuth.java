package com.nm.auths.oauth;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.nm.auths.constants.AuthenticationState;
import com.nm.auths.daos.QueryBuilderAuthentication;
import com.nm.auths.dtos.DtoAuthenticationOAuth;
import com.nm.auths.grants.AuthorityGranterProcessor;
import com.nm.auths.models.AuthenticationOpenID;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class UserDetailsServicePreAuth
		implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	private AuthorityGranterProcessor processor;

	public void setProcessor(AuthorityGranterProcessor processor) {
		this.processor = processor;
	}

	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		DtoAuthenticationOAuth cred = (DtoAuthenticationOAuth) token.getPrincipal();
		String username = cred.getOpenid();
		QueryBuilderAuthentication query = QueryBuilderAuthentication.getOpenID().withState(AuthenticationState.Enabled)
				.withOpenID(username);
		try {
			AuthenticationOpenID user = AbstractGenericDao.get(AuthenticationOpenID.class).findFirst(query);
			List<GrantedAuthority> authorities = processor.processor(user);
			return new IDUserDetails(user, username, "", authorities);
		} catch (NoDataFoundException e) {
			throw new UsernameNotFoundException("Failed to find user", e);
		}

	}

}
