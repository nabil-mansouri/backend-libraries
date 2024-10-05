package com.nm.social.operations.fb;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;

import com.google.common.collect.Sets;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialNetwork;
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
public class SocialOperationPageLoading extends SocialOperationAbstract implements SocialOperationOAuth2 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Collection<String> scopes() {
		return Arrays.asList("manage_pages");
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Facebook);
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadNetworks);
	}

	public SocialUser operation(OAuth2AccessToken operation, Object... params) throws Exception {
		final FacebookTemplateCustom template = template(operation);
		IGenericDao<SocialNetwork, Long> daoG = AbstractGenericDao.get(SocialNetwork.class);
		OptionsList op = new OptionsList();
		DtoConverter<Page, SocialNetwork> convG = registry.search(Page.class, SocialNetwork.class);
		SocialUser currentModel = me(template);
		// LOADING
		List<Account> memberships = new PagingIterator<Account>() {
			@Override
			public PagedList<Account> call() {
				return template.customPageTemplateCustom().getAccounts();
			}

			@Override
			public PagedList<Account> call(PagingParameters p) {
				return template.customPageTemplateCustom().getAccounts(p);
			}
		}.iterate();
		//
		Collection<SocialNetwork> networksModel = Sets.newHashSet();
		for (Account f : memberships) {
			Page group = template.pageOperations().getPage(f.getId());
			SocialNetwork groupM = convG.toEntity(group, op);
			groupM.addOwners(currentModel);
			networksModel.add(groupM);
		}
		// SAVING
		daoG.saveOrUpdate(networksModel);
		return currentModel;
	}
}
