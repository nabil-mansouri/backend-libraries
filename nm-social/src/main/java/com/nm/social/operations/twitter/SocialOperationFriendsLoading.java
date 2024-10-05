package com.nm.social.operations.twitter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.social.twitter.api.TwitterProfile;

import com.google.api.services.plus.PlusScopes;
import com.google.common.collect.Sets;
import com.google.inject.internal.Maps;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth1;
import com.nm.social.templates.twitter.PagingIterator;
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
public class SocialOperationFriendsLoading extends SocialOperationAbstract implements SocialOperationOAuth1 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Twitter);
	}

	public Collection<String> scopes() {
		return PlusScopes.all();
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadFriend);
	}

	public SocialUser operation(OAuthConsumerToken token, Object... params) throws Exception {
		IGenericDao<SocialUser, Long> dao = AbstractGenericDao.get(SocialUser.class);
		OptionsList op = new OptionsList();
		DtoConverter<TwitterProfile, SocialUser> conv = registry.search(TwitterProfile.class, SocialUser.class);
		final FriendOperations friendsOp = template(token).friendOperations();
		List<TwitterProfile> friends = new PagingIterator<TwitterProfile>() {

			@Override
			public CursoredList<TwitterProfile> call() {
				return friendsOp.getFriends();
			}

			@Override
			public CursoredList<TwitterProfile> call(long cursor) {
				return friendsOp.getFriendsInCursor(cursor);
			}
		}.iterate();
		// ADD FOLLOWERS TO FRIENDS LIST
		friends.addAll(new PagingIterator<TwitterProfile>() {

			@Override
			public CursoredList<TwitterProfile> call() {
				return friendsOp.getFollowers();
			}

			@Override
			public CursoredList<TwitterProfile> call(long cursor) {
				return friendsOp.getFollowers(cursor);
			}
		}.iterate());
		//
		Collection<SocialUser> friendsModel = Sets.newHashSet();
		Map<Long, TwitterProfile> profilesUniq = Maps.newHashMap();
		for (TwitterProfile u : friends) {
			profilesUniq.put(u.getId(), u);
		}
		for (TwitterProfile u : profilesUniq.values()) {
			friendsModel.add(conv.toEntity(u, op));
		}
		// SAVING
		dao.saveOrUpdate(friendsModel);
		SocialUser currentModel = me(template(token));
		currentModel.clear();
		currentModel.addFriends(friendsModel);
		dao.saveOrUpdate(currentModel);
		return currentModel;
	}

}
