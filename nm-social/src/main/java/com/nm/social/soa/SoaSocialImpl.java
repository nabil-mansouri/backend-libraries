package com.nm.social.soa;

import java.util.Collection;
import java.util.List;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.dtos.DtoSocialState;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationLauncher;
import com.nm.social.operations.SocialOperationListener;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SoaSocialImpl implements SoaSocial {
	private SocialOperationLauncher launcher;
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void setLauncher(SocialOperationLauncher launcher) {
		this.launcher = launcher;
	}

	@Override
	public Collection<DtoSocialState> fetch(QueryBuilderSocialUser query, OptionsList options) throws SocialException {
		try {
			Collection<SocialUser> users = AbstractGenericDao.get(SocialUser.class).find(query);
			DtoConverter<DtoSocialState, SocialUser> converter = registry.search(DtoSocialState.class,
					SocialUser.class);
			Collection<DtoSocialState> dto = converter.toDto(users, options);
			return dto;
		} catch (Exception ee) {
			throw new SocialException(ee);
		}
	}

	@Override
	public void oauth1Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			Object... params) throws SocialException {
		try {
			launcher.oauth1(providers, e, params);
		} catch (Exception ee) {
			throw new SocialException(ee);
		}
	}

	@Override
	public void oauth1Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			SocialOperationListener listener, Object... params) throws SocialException {
		try {
			launcher.oauth1(providers, e, listener, params);
		} catch (Exception ee) {
			throw new SocialException(ee);
		}
	}

	@Override
	public void oauth2Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			Object... params) throws SocialException {
		try {
			launcher.oauth2(providers, e, params);
		} catch (Exception ee) {
			throw new SocialException(ee);
		}
	}

	@Override
	public void oauth2Operation(List<? extends AuthenticationProvider> providers, List<? extends SocialOperationEnum> e,
			SocialOperationListener listener, Object... params) throws SocialException {
		try {
			launcher.oauth2(providers, e, listener, params);
		} catch (Exception ee) {
			throw new SocialException(ee);
		}
	}

}
