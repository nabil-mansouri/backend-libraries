package com.nm.tests;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.api.client.util.Lists;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.args.SocialEventInfo;
import com.nm.utils.dates.DateUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FakeModelEvents implements SocialEventInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String calendId;
	Collection<SocialUser> invite = Lists.newArrayList();

	public void setInvite(Collection<SocialUser> invite) {
		this.invite = invite;
	}

	public void setCalendId(String calendId) {
		this.calendId = calendId;
	}

	public String calendarUuid() {
		return calendId;
	}

	public String calendarName() {
		return "MON CALENDRIER";
	}

	public String title() {
		return "TITRE EVT";
	}

	public String place() {
		return "avenue de la république Lyon";
	}

	public String description() {
		return "MA DESCRIPTION.....";
	}

	public Date start() {
		LocalDate d = new LocalDate();
		LocalTime time = new LocalTime(DateUtilsExt.from(0, 0, 17, 1, 1, 2016));
		return d.toDateTime(time).toDate();
	}

	public Date end() {
		LocalDate d = new LocalDate();
		LocalTime time = new LocalTime(DateUtilsExt.from(0, 0, 18, 1, 1, 2016));
		return d.toDateTime(time).toDate();
	}

	public Collection<SocialUser> invite() {
		return invite;
	}

	public boolean isPrivate() {
		return false;
	}

}
