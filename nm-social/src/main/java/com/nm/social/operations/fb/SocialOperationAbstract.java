package com.nm.social.operations.fb;

import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.google.common.collect.Lists;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.models.SocialUser;
import com.nm.social.templates.fb.FacebookTemplateCustom;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class SocialOperationAbstract {
	protected FacebookTemplateCustom template(OAuth2AccessToken token) {
		return new FacebookTemplateCustom(token.getValue());
	}

	public SocialUser me(FacebookTemplateCustom factory) throws NoDataFoundException {
		String uuid = factory.userOperations().getUserProfile().getId();
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		QueryBuilderSocialUser query = QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Facebook).withUUID(uuid);
		Collection<SocialUser> users = dao.find(query);
		if (users.isEmpty()) {
			throw new NoDataFoundException("Could not find facebook user with id:" + uuid);
		} else {
			return users.iterator().next();
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> extractParams(Class<T> clazz, Object... params) {
		List<T> l = Lists.newArrayList();
		for (Object o : params) {
			if (clazz.isInstance(o)) {
				l.add((T) o);
			}
		}
		return l;
	}

}
