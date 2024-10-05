package com.nm.social.soa;

import java.util.Collection;
import java.util.List;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.dtos.DtoSocialState;
import com.nm.social.operations.SocialOperationListener;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SoaSocial {
	Collection<DtoSocialState> fetch(QueryBuilderSocialUser query, OptionsList options) throws SocialException;

	void oauth1Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			Object... params) throws SocialException;

	void oauth1Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			SocialOperationListener listener, Object... params) throws SocialException;

	void oauth2Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			Object... params) throws SocialException;

	void oauth2Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			SocialOperationListener listener, Object... params) throws SocialException;
}
