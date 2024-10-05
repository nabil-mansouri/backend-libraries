package com.nm.social.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum SocialOptions implements ModelOptions {
	FriendsStates, NetworkStates, EventStates;

	public ModelOptionsType getType() {
		return null;
	}

}