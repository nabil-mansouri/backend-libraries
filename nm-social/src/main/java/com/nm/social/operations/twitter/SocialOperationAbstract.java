package com.nm.social.operations.twitter;

import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.google.common.collect.Lists;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.auths.constants.ModuleConfigKeyAuthentication;
import com.nm.config.SoaModuleConfig;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.models.SocialUser;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class SocialOperationAbstract {
	protected TwitterTemplate template(OAuthConsumerToken token) {
		SoaModuleConfig soa = ApplicationUtils.getBean(SoaModuleConfig.class);
		String consumer = soa.getText(ModuleConfigKeyAuthentication.TwitterClientId);
		String secret = soa.getText(ModuleConfigKeyAuthentication.TwitterSecret);
		TwitterTemplate template = new TwitterTemplate(consumer, secret, token.getValue(), token.getSecret());
		return template;
	}

	public SocialUser me(TwitterTemplate factory) throws NoDataFoundException {
		long uuid = factory.userOperations().getProfileId();
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		QueryBuilderSocialUser query = QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Twitter).withUUID(uuid);
		Collection<SocialUser> users = dao.find(query);
		if (users.isEmpty()) {
			throw new NoDataFoundException("Could not find google user with id:" + uuid);
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
