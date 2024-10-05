package com.nm.soa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nm.users.creators.UserConverter;
import com.nm.users.creators.UserCreatorFactory;
import com.nm.users.daos.UserGroupQueryBuilder;
import com.nm.users.daos.UserHistoryQueryBuilder;
import com.nm.users.daos.UserQueryBuilder;
import com.nm.users.dtos.UserForm;
import com.nm.users.dtos.UserGroupForm;
import com.nm.users.exceptions.GroupExistsException;
import com.nm.users.models.User;
import com.nm.users.models.UserGroup;
import com.nm.users.models.UserHistory;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Component
public class UserServiceImpl implements UserService {
	public final class SessionIdentifierGenerator {
		private SecureRandom random = new SecureRandom();

		public String generateLogin() {
			return new BigInteger(130, random).toString(32).substring(0, 5);
		}

		public String generatePwd() {
			return new BigInteger(130, random).toString(32).substring(0, 15);
		}
	}

	protected Log log = LogFactory.getLog(getClass());
 
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private UserCreatorFactory userCreatorFactory;
	@Transactional(readOnly = true)
	public void addUser(UserGroup group, User user) {
		group.getUsers().add(user);
	}

	@Transactional
	public UserGroup createGroup(UserGroupForm groupBean) {
		IGenericDao<UserGroup, Long> userGroupDao = AbstractGenericDao.get(UserGroup.class);
		UserGroup group = new UserGroup();
		if (groupBean.getId() != null) {
			group = userGroupDao.load(groupBean.getId());
		}
		group.setName(groupBean.getName());
		userGroupDao.saveOrUpdate(group);
		groupBean.setId(group.getId());
		return group;
	}

	public UserForm createUser() {
		UserForm form = new UserForm();
		return form;
	}

	@Transactional
	public User createUser(UserForm form) throws Exception {
		return userCreatorFactory.create(form);
	}

	 

	@Transactional(readOnly = true)
	public Collection<UserGroupForm> getGroups(UserGroupQueryBuilder query) {
		IGenericDao<UserGroup, Long> userGroupDao = AbstractGenericDao.get(UserGroup.class);
		Collection<UserGroupForm> all = new HashSet<UserGroupForm>();
		Collection<UserGroup> groups = userGroupDao.find(query);
		for (UserGroup g : groups) {
			all.add(userConverter.get(g, new OptionsList()));
		}
		return all;
	}

	public UserHistory getOrCreateHistory(Long user) {
		IGenericDao<UserHistory, Long> userHistoryDao = AbstractGenericDao.get(UserHistory.class);
		IGenericDao<User, Long> userDao = AbstractGenericDao.get(User.class);
		Collection<UserHistory> histories = userHistoryDao.find(UserHistoryQueryBuilder.get().withUser(user));
		if (histories.isEmpty()) {
			UserHistory history = new UserHistory();
			history.setUser(userDao.load(user));
			userHistoryDao.saveOrUpdate(history);
			return history;
		} else {
			return histories.iterator().next();
		}
	}

	@Transactional
	public UserForm getUser(Long id) {
		IGenericDao<User, Long> userDao = AbstractGenericDao.get(User.class);
		User user = userDao.load(id);
		return userConverter.get(user, new OptionsList());
	}

	@Transactional(readOnly = true)
	public Collection<UserForm> getUserByGroup(UserGroup group) {
		Collection<User> users = group.getUsers();
		Collection<UserForm> all = new ArrayList<UserForm>();
		for (User user : users) {
			all.add(userConverter.get(user, new OptionsList()));
		}
		return all;
	}

	@Transactional(readOnly = true)
	public Collection<UserForm> getUsers(UserQueryBuilder query, OptionsList options) {
		IGenericDao<User, Long> userDao = AbstractGenericDao.get(User.class);
		Collection<UserForm> usersForm = new HashSet<UserForm>();
		Collection<User> users = userDao.find(query);
		for (User u : users) {
			usersForm.add(userConverter.get(u, options));
		}
		return usersForm;
	}

	@Transactional
	public void remove(User user) {
		IGenericDao<User, Long> userDao = AbstractGenericDao.get(User.class);
		userDao.delete((user));

	}

	@Transactional
	public void remove(UserGroup group) {
		IGenericDao<UserGroup, Long> userGroupDao = AbstractGenericDao.get(UserGroup.class);
		userGroupDao.delete((group));
	}

	@Transactional(readOnly = true)
	public void removeUserFromGroup(UserGroup group, User user) {
		group.getUsers().remove(user);
	}

	 

	public UserGroup saveOrUpdate(UserGroupForm form) throws GroupExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	public UserForm enable(User user, boolean enable) {
		// TODO Auto-generated method stub
		return null;
	}

}
