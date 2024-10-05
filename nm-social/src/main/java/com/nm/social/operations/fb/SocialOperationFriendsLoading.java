package com.nm.social.operations.fb;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.User;

import com.google.common.collect.Sets;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth2;
import com.nm.social.templates.fb.FacebookTemplateCustom;
import com.nm.social.templates.fb.PagingIterator;
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
		return en.equals(AuthenticationProviderDefault.Facebook);
	}

	public Collection<String> scopes() {
		return Arrays.asList("user_friends");
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadFriend);
	}

	public SocialUser operation(OAuth2AccessToken operation, Object... params) throws Exception {
		final FacebookTemplateCustom template = template(operation);
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		OptionsList op = new OptionsList();
		DtoConverter<User, SocialUser> conv = registry.search(User.class, SocialUser.class);
		SocialUser currentModel = me(template);
		// LOADING
		List<User> users = new PagingIterator<User>() {
			@Override
			public PagedList<User> call() {
				return template.friendOperations().getFriendProfiles();
			}

			@Override
			public PagedList<User> call(PagingParameters p) {
				return template.friendOperations().getFriendProfiles(p);
			}
		}.iterate();
		Collection<SocialUser> friendsModel = Sets.newHashSet();
		for (User u : users) {
			friendsModel.add(conv.toEntity(u, op));
		}
		// SAVING
		dao.saveOrUpdate(friendsModel);
		currentModel.clear();
		currentModel.addFriends(friendsModel);
		dao.saveOrUpdate(currentModel);
		return currentModel;
	}
}
