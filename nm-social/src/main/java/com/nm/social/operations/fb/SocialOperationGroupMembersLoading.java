package com.nm.social.operations.fb;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth2;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialOperationGroupMembersLoading extends SocialOperationAbstract implements SocialOperationOAuth2 {
	// private DtoConverterRegistry registry;

	// public void setRegistry(DtoConverterRegistry registry) {
	// this.registry = registry;
	// }
	// TODO cant access group using fb api (autorisaation)
	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Facebook);
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadNetworks);
	}

	public Collection<String> scopes() {
		return Arrays.asList("user_managed_groups");
	}

	public SocialUser operation(OAuth2AccessToken operation, Object... params) throws Exception {
		// final FacebookTemplateCustom template = template(operation);
		// IGenericDao<SocialNetwork, Long> daoG =
		// AbstractGenericDao.get(SocialNetwork.class);
		// OptionsList op = new OptionsList();
		// DtoConverter<Group, SocialNetwork> convG =
		// registry.search(Group.class, SocialNetwork.class);
		// DtoConverter<User, SocialUser> conv = registry.search(User.class,
		// SocialUser.class);
		// SocialUser currentModel = me(template);
		// //
		// Collection<SocialNetwork> groupsM = Sets.newHashSet();
		// groupsM.addAll(currentModel.getNetworks());
		// groupsM.addAll(currentModel.getNetworksOwned());
		// for (SocialNetwork s : groupsM) {
		// // LOADING
		// String url = null;
		// Collection<SocialNetwork> networksModel = Sets.newHashSet();
		// PagedList<User> members =
		// template.groupOperations().getMemberProfiles(s.getUuid());
		// PagedList<GroupMemberReference> memberRed =
		// factory.template().groupOperations().getMembers(s.getUuid());
		// TODO
		// do {
		// for (User f : members) {
		// Group group =
		// factory.template().groupOperations().getGroup(f.getId());
		// SocialNetwork groupM = convG.toEntity(group, op);
		// networksModel.add(groupM);
		// if (f.isAdministrator()) {
		// groupM.addOwners(currentModel);
		// } else {
		// groupM.addUser(currentModel);
		// }
		// }
		// if (groups.getNextPage() != null) {
		// url = groups.getNextPage().getAfter();
		// }
		// } while (!Strings.isNullOrEmpty(url));
		// }

		// SAVING
		// daoG.saveOrUpdate(networksModel);
		return null;
	}
}
