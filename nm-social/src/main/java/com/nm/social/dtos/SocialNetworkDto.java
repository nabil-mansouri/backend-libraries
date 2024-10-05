package com.nm.social.dtos;

import java.io.Serializable;

import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.twitter.api.UserList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SocialNetworkDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Group facebook;
	private Page facebookPage;
	private UserList twitter;

	public UserList getTwitter() {
		return twitter;
	}

	public void setTwitter(UserList twitter) {
		this.twitter = twitter;
	}

	public Page getFacebookPage() {
		return facebookPage;
	}

	public void setFacebookPage(Page facebookPage) {
		this.facebookPage = facebookPage;
	}

	public Group getFacebook() {
		return facebook;
	}

	public void setFacebook(Group facebook) {
		this.facebook = facebook;
	}
}
