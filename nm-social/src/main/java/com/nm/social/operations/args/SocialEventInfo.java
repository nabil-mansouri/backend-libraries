package com.nm.social.operations.args;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.nm.social.models.SocialUser;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface SocialEventInfo extends Serializable {
	String calendarUuid();

	String calendarName();

	String title();

	String place();

	String description();

	Date start();

	Date end();

	Collection<SocialUser> invite();

	boolean isPrivate();
}
