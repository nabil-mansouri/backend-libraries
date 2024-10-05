package com.nm.social.operations.fb;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.facebook.api.Event;
import org.springframework.social.facebook.api.Invitation;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialEventStatus;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialEvent;
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
public class SocialOperationEventsLoading extends SocialOperationAbstract implements SocialOperationOAuth2 {
	private DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Collection<String> scopes() {
		return Arrays.asList("user_events");
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Facebook);
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadEvents);
	}

	public SocialUser operation(OAuth2AccessToken operation, Object... params) throws Exception {
		final FacebookTemplateCustom template = template(operation);
		IGenericDao<SocialEvent, Long> daoG = AbstractGenericDao.get(SocialEvent.class);
		OptionsList op = new OptionsList();
		DtoConverter<Event, SocialEvent> convG = registry.search(Event.class, SocialEvent.class);
		SocialUser currentModel = me(template);
		// LOADING
		MultiValueMap<SocialEventStatus, Invitation> m = new LinkedMultiValueMap<SocialEventStatus, Invitation>();
		map(m, SocialEventStatus.Attending, new PagingIterator<Invitation>() {
			@Override
			public PagedList<Invitation> call() {
				return template.eventOperations().getAttending();
			}

			@Override
			public PagedList<Invitation> call(PagingParameters p) {
				return template.eventOperations().getAttending(p);
			}
		});
		map(m, SocialEventStatus.Created, new PagingIterator<Invitation>() {
			@Override
			public PagedList<Invitation> call() {
				return template.eventOperations().getCreated();
			}

			@Override
			public PagedList<Invitation> call(PagingParameters p) {
				return template.eventOperations().getCreated(p);
			}
		});
		map(m, SocialEventStatus.Declinded, new PagingIterator<Invitation>() {
			@Override
			public PagedList<Invitation> call() {
				return template.eventOperations().getDeclined();
			}

			@Override
			public PagedList<Invitation> call(PagingParameters p) {
				return template.eventOperations().getDeclined(p);
			}
		});
		map(m, SocialEventStatus.MayBeAttending, new PagingIterator<Invitation>() {
			@Override
			public PagedList<Invitation> call() {
				return template.eventOperations().getMaybeAttending();
			}

			@Override
			public PagedList<Invitation> call(PagingParameters p) {
				return template.eventOperations().getMaybeAttending(p);
			}
		});
		map(m, SocialEventStatus.NotReplied, new PagingIterator<Invitation>() {
			@Override
			public PagedList<Invitation> call() {
				return template.eventOperations().getNoReplies();
			}

			@Override
			public PagedList<Invitation> call(PagingParameters p) {
				return template.eventOperations().getNoReplies(p);
			}
		});
		// CLEAN USER
		for (SocialEventStatus key : currentModel.getEvents().keySet()) {
			currentModel.getEvents().get(key).getEvents().clear();
		}
		// LOAD
		for (SocialEventStatus key : m.keySet()) {
			List<Invitation> invitations = m.get(key);
			for (Invitation i : invitations) {
				// TODO need to load an existing event? update it manually?
				Event e = template.customEventTemplateCustom().getEvent(i.getEventId());
				SocialEvent ev = convG.toEntity(e, op);
				daoG.saveOrUpdate(ev);
				currentModel.addEvent(key, ev);
			}
		}
		daoG.getHibernateTemplate().saveOrUpdate(currentModel);
		return currentModel;
	}

	private void map(MultiValueMap<SocialEventStatus, Invitation> m, SocialEventStatus soc,
			PagingIterator<Invitation> it) {
		for (Invitation i : it.iterate()) {
			m.add(soc, i);
		}
	}
}
