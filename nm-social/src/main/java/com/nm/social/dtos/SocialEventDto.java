package com.nm.social.dtos;

import java.io.Serializable;

import org.springframework.social.facebook.api.Event;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialEventDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Event facebook;
	private com.google.api.services.calendar.model.Event google;

	public void setGoogle(com.google.api.services.calendar.model.Event google) {
		this.google = google;
	}

	public com.google.api.services.calendar.model.Event getGoogle() {
		return google;
	}

	public Event getFacebook() {
		return facebook;
	}

	public void setFacebook(Event facebook) {
		this.facebook = facebook;
	}
}
