package com.nm.users.creators;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nm.soa.UserService;
import com.nm.users.daos.UserGroupQueryBuilder;
import com.nm.users.dtos.UserForm;
import com.nm.users.dtos.UserGroupForm;
import com.nm.users.models.User;
import com.nm.users.models.UserGroup;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Component(UserCreator.GROUP)
@Qualifier(UserCreator.STRATEGY)
public class UserCreatorAuth implements UserCreator {
	@Autowired
	protected UserService userService;

	public User createOrUpdate(User user, UserForm userForm) throws Exception {
		IGenericDao<UserGroup, Long> userGroupDao = AbstractGenericDao.get(UserGroup.class);
		// Remove previous member of
		Collection<Long> membersOfAsUser = userGroupDao.findIds(UserGroupQueryBuilder.get().withUser(userForm.getId()));
		for (Long m : membersOfAsUser) {
			UserGroup org = userGroupDao.load(m);
			userService.removeUserFromGroup(org, user);
		}
		//
		if (userForm.isHasAuthentication()) {
			userForm.getAuthentication().setEnable(true);
			UserGroupForm groupForm = userForm.getAuthentication().getGroup();
			if (groupForm != null && groupForm.getId() != null) {
				// Set new member of
				UserGroup org = userGroupDao.load(groupForm.getId());
				userService.addUser(org, user);
			}
		} else {
		}
		return user;
	}

}
