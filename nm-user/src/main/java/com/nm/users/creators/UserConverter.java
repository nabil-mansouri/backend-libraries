package com.nm.users.creators;

import com.nm.users.dtos.UserForm;
import com.nm.users.dtos.UserGroupForm;
import com.nm.users.models.User;
import com.nm.users.models.UserGroup;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface UserConverter {
	public UserForm get(User user, OptionsList options);

	public UserGroupForm get(UserGroup group, OptionsList options);
}
