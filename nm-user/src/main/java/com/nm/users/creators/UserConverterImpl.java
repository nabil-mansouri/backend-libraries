package com.nm.users.creators;

import org.springframework.stereotype.Component;

import com.nm.users.constants.UsersOptions;
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
@Component
public class UserConverterImpl implements UserConverter {

	public UserForm get(User user, OptionsList options) {
		UserForm form = new UserForm();
		form.setEmail(user.getEmail());
		form.setEnable(user.isEnable());
		form.setFirstname(user.getFirstname());
		form.setHasAuthentication(false);
		form.setId(user.getId());
		form.setName(user.getName());
		form.setPersonnalEmail(user.getPersonnalEmail());
		form.setPhone(user.getPhone());
		form.setType(user.getCivility());
		if (user.getAvatar() != null) {
			form.setIdAvatar(user.getAvatar().getId());
		}
		return form;
	}

	public UserGroupForm get(UserGroup group, OptionsList options) {
		UserGroupForm form = new UserGroupForm();
		form.setId(group.getId());
		form.setName(group.getName());
		if (options.contains(UsersOptions.GroupSize)) {
			form.setNbUsers(group.getUsers().size());
		}
		return form;
	}

}
