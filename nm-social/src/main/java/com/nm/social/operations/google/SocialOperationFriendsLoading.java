package com.nm.social.operations.google;

import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.google.api.plus.PeoplePage;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;

import com.google.api.services.plus.PlusScopes;
import com.google.common.collect.Sets;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth2;
import com.nm.social.templates.google.GoogleTemplateCustom;
import com.nm.social.templates.google.PagingIterator;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialOperationFriendsLoading extends SocialOperationAbstract implements SocialOperationOAuth2 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Google);
	}

	public Collection<String> scopes() {
		return PlusScopes.all();
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadFriend);
	}

	public SocialUser operation(final OAuth2AccessToken operation, Object... params) throws Exception {
		final GoogleTemplateCustom template = template(operation);
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		SocialUser currentModel = me(template);
		// LOADING
		final PlusOperations op = template(operation).plusOperations();
		List<Person> friends = (new PagingIterator<Person>() {
			PeoplePage listByCircle = op.getPeopleInCircles("me", null);

			@Override
			public String nextPageToken() {
				return listByCircle.getNextPageToken();
			}

			@Override
			public List<Person> call() {
				return listByCircle.getItems();
			}

			@Override
			public List<Person> call(String nextPageToken) throws Exception {
				listByCircle = op.getPeopleInCircles("me", nextPageToken);
				return listByCircle.getItems();
			}
		}.iterate());
		Collection<SocialUser> friendsModel = Sets.newHashSet();
		OptionsList opt = new OptionsList();
		DtoConverter<Person, SocialUser> conv = registry.search(Person.class, SocialUser.class);
		for (Person u : friends) {
			friendsModel.add(conv.toEntity(u, opt));
		}
		// SAVING
		dao.saveOrUpdate(friendsModel);
		currentModel.clear();
		currentModel.addFriends(friendsModel);
		dao.saveOrUpdate(currentModel);
		return currentModel;
	}
}
