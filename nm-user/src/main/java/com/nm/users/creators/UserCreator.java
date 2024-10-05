package com.nm.users.creators;

import com.nm.users.dtos.UserForm;
import com.nm.users.models.User;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface UserCreator {
	public static final String STRATEGY = "UserCreator.STRATEGY";
	public static final String DEFAULT = "UserCreator.DEFAULT";
	public static final String GROUP = "UserCreator.GROUP";
	public static final String ORG = "UserCreator.ORG";

	public abstract User createOrUpdate(User user, UserForm form) throws Exception;
}
