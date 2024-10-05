package com.nm.soa;

import java.util.Collection;

import com.nm.users.daos.UserGroupQueryBuilder;
import com.nm.users.daos.UserQueryBuilder;
import com.nm.users.dtos.UserForm;
import com.nm.users.dtos.UserGroupForm;
import com.nm.users.exceptions.GroupExistsException;
import com.nm.users.models.User;
import com.nm.users.models.UserGroup;
import com.nm.users.models.UserHistory;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface UserService {

	public void addUser(UserGroup group, User user);

	public UserForm createUser();

	public User createUser(UserForm form) throws Exception;

	public UserForm enable(User user, boolean enable);

	public Collection<UserGroupForm> getGroups(UserGroupQueryBuilder query);

	public UserHistory getOrCreateHistory(Long user);

	public UserForm getUser(Long id);

	public Collection<UserForm> getUserByGroup(UserGroup group);

	public Collection<UserForm> getUsers(UserQueryBuilder user, OptionsList options);

	public void remove(User form);

	public void remove(UserGroup forms);

	public void removeUserFromGroup(UserGroup group, User user);

	public UserGroup saveOrUpdate(UserGroupForm form) throws GroupExistsException;
}
