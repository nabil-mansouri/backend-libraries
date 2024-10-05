package com.nm.social.dtos;

import java.io.Serializable;

import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.twitter.api.TwitterProfile;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User facebook;
	private Person google;
	private TwitterProfile twitter;

	public TwitterProfile getTwitter() {
		return twitter;
	}

	public void setTwitter(TwitterProfile twitter) {
		this.twitter = twitter;
	}

	public Person getGoogle() {
		return google;
	}

	public void setGoogle(Person google) {
		this.google = google;
	}

	public User getFacebook() {
		return facebook;
	}

	public void setFacebook(User facebook) {
		this.facebook = facebook;
	}
}
