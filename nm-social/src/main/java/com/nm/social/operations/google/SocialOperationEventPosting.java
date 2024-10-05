package com.nm.social.operations.google;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.base.Strings;
import com.google.inject.internal.Lists;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialEventStatus;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.daos.QueryBuilderSocialCalendar;
import com.nm.social.models.SocialCalendar;
import com.nm.social.models.SocialEvent;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth2;
import com.nm.social.operations.args.SocialEventInfo;
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
public class SocialOperationEventPosting extends SocialOperationAbstract implements SocialOperationOAuth2 {
	protected DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Google);
	}

	public Collection<String> scopes() {
		return Arrays.asList(CalendarScopes.CALENDAR);
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.PublishEvents);
	}

	public SocialUser operation(final OAuth2AccessToken operation, Object... params) throws Exception {
		final Calendar template = calendar(operation);
		SocialUser me = me(template(operation));
		IGenericDao<SocialEvent, Long> daoE = AbstractGenericDao.get(SocialEvent.class);
		IGenericDao<SocialCalendar, Long> dao = AbstractGenericDao.get(SocialCalendar.class);
		DtoConverter<com.google.api.services.calendar.model.Calendar, SocialCalendar> conv = registry
				.search(com.google.api.services.calendar.model.Calendar.class, SocialCalendar.class);
		DtoConverter<com.google.api.services.calendar.model.Event, SocialEvent> convE = registry
				.search(com.google.api.services.calendar.model.Event.class, SocialEvent.class);
		// BUILD CUSTOM CALENDAR IF NOT EXISTS
		SocialEventInfo info = extractFirstParam(SocialEventInfo.class, params);
		QueryBuilderSocialCalendar query = QueryBuilderSocialCalendar.get()//
				.withUUID(info.calendarUuid()).withOwnerId(me.getUuid())//
				.withAuthProvider(AuthenticationProviderDefault.Google);
		SocialCalendar calendarModel = dao.findFirstOrNull(query);
		com.google.api.services.calendar.model.Calendar calendar = null;
		if (calendarModel == null) {
			calendar = new com.google.api.services.calendar.model.Calendar();
			calendar.setSummary(info.calendarName());
			calendar = template.calendars().insert(calendar).execute();
			//
			calendarModel = conv.toEntity(calendar, new OptionsList());
			calendarModel.addOwner(me);
			dao.saveOrUpdate(calendarModel);
		} else {
			calendar = template.calendars().get(calendarModel.getUuid()).execute();
		}
		// SAVE EVENTS
		Event event = new Event().setSummary(info.title()).setLocation(info.place()).setDescription(info.description());
		EventDateTime start = new EventDateTime().setDateTime(new DateTime(info.start()));
		event.setStart(start);
		EventDateTime end = new EventDateTime().setDateTime(new DateTime(info.end()));
		event.setEnd(end);
		// TODO build recurrence
		// String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=2" };
		// event.setRecurrence(Arrays.asList(recurrence));
		event.setAnyoneCanAddSelf(info.isPrivate());
		List<EventAttendee> attendee = Lists.newArrayList();
		for (SocialUser u : info.invite()) {
			if (Objects.equals(u.getType(), AuthenticationProviderDefault.Google)) {
				attendee.add(new EventAttendee().setId(u.getUuid()));
			} else if (!Strings.isNullOrEmpty(u.getEmail())) {
				attendee.add(new EventAttendee().setEmail(u.getEmail()));
			} else {
				// CANNOT ADD IT
			}
		}
		// TODO ATTENDEES GENERATE FORBIDDEN
		// event.setAttendees(attendee);
		Event.Reminders reminders = new Event.Reminders().setUseDefault(true);
		event.setReminders(reminders);
		event = template.events().insert(calendar.getId(), event).execute();
		SocialEvent socialEvent = convE.toEntity(event, new OptionsList());
		daoE.saveOrUpdate(socialEvent);
		me.addEvent(SocialEventStatus.Created, socialEvent);
		return me;
	}
}
