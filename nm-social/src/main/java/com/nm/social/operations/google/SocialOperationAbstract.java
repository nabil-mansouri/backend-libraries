package com.nm.social.operations.google;

import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.plus.Plus;
import com.google.api.services.plusDomains.PlusDomains;
import com.google.common.collect.Lists;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.models.SocialUser;
import com.nm.social.templates.google.GoogleTemplateCustom;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.NotFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class SocialOperationAbstract {
	protected GoogleTemplateCustom template(OAuth2AccessToken token) {
		return new GoogleTemplateCustom(token.getValue());
	}

	protected Plus plus(OAuth2AccessToken token) {
		GoogleCredential credential = new GoogleCredential().setAccessToken(token.getValue());
		return new Plus.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
	}

	protected PlusDomains plusDomain(OAuth2AccessToken token) {
		GoogleCredential credential = new GoogleCredential().setAccessToken(token.getValue());
		return new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
	}

	protected Calendar calendar(OAuth2AccessToken token) {
		GoogleCredential credential = new GoogleCredential().setAccessToken(token.getValue());
		return new Calendar.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
	}

	public SocialUser me(GoogleTemplateCustom factory) throws NoDataFoundException {
		String uuid = factory.plusOperations().getGoogleProfile().getId();
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		QueryBuilderSocialUser query = QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Google).withUUID(uuid);
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

	protected <T> T extractFirstParam(Class<T> clazz, Object... params) throws NotFoundException {
		List<T> l = extractParams(clazz, params);
		if (l.isEmpty()) {
			throw new NotFoundException("Could not found params of type:" + clazz);
		} else {
			return l.iterator().next();
		}
	}

}
