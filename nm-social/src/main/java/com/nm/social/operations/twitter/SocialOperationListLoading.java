package com.nm.social.operations.twitter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserList;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.google.common.collect.Sets;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialNetwork;
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
public class SocialOperationListLoading extends SocialOperationAbstract implements SocialOperationOAuth1 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Collection<String> scopes() {
		return Arrays.asList();
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Twitter);
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadNetworks);
	}

	public SocialUser operation(OAuthConsumerToken operation, Object... params) throws Exception {
		final TwitterTemplate template = template(operation);
		IGenericDao<SocialUser, Long> daoU = AbstractGenericDao.get(SocialUser.class);
		IGenericDao<SocialNetwork, Long> daoG = AbstractGenericDao.get(SocialNetwork.class);
		OptionsList op = new OptionsList();
		DtoConverter<TwitterProfile, SocialUser> convU = registry.search(TwitterProfile.class, SocialUser.class);
		DtoConverter<UserList, SocialNetwork> convG = registry.search(UserList.class, SocialNetwork.class);
		SocialUser currentModel = me(template);
		// LOADING
		List<UserList> lists = template.listOperations().getLists();
		//
		Collection<SocialNetwork> networksModel = Sets.newHashSet();
		for (final UserList f : lists) {
			List<TwitterProfile> friends = new PagingIterator<TwitterProfile>() {

				@Override
				public CursoredList<TwitterProfile> call() {
					return template.listOperations().getListMembers(f.getId());
				}

				@Override
				public CursoredList<TwitterProfile> call(long cursor) {
					return template.listOperations().getListMembersInCursor(f.getId(), cursor);
				}
			}.iterate();
			SocialNetwork groupM = convG.toEntity(f, op);
			groupM.addOwners(currentModel);
			for (TwitterProfile p : friends) {
				SocialUser user = convU.toEntity(p, op);
				daoU.saveOrUpdate(user);
				groupM.addUser(user);
			}
			networksModel.add(groupM);
		}
		// SAVING
		daoG.saveOrUpdate(networksModel);
		return currentModel;
	}
}
