package com.nm.social.operations.google;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationOAuth2;
import com.nm.social.templates.google.GoogleTemplateCustom;
import com.nm.utils.dtos.DtoConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialOperationEventLoading extends SocialOperationAbstract implements SocialOperationOAuth2 {
	protected DtoConverterRegistry registry;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public boolean accept(AuthenticationProvider en) {
		return en.equals(AuthenticationProviderDefault.Google);
	}

	public Collection<String> scopes() {
		return Arrays.asList(CalendarScopes.CALENDAR_READONLY);
	}

	public boolean accept(SocialOperationEnum en) {
		return en.equals(SocialOperationEnumDefault.LoadEvents);
	}

	public SocialUser operation(final OAuth2AccessToken operation, Object... params) throws Exception {
		final GoogleTemplateCustom templates = template(operation);
		SocialUser user = me(templates);
		final Calendar template = calendar(operation);
		String pageToken = null;
		do {
			CalendarList calendarList = template.calendarList().list().setPageToken(pageToken).execute();
			List<CalendarListEntry> items = calendarList.getItems();
			for (CalendarListEntry calendarListEntry : items) {
				System.out.println(calendarListEntry.getSummary());
			}
			pageToken = calendarList.getNextPageToken();
		} while (pageToken != null);
		return user;
	}
}
