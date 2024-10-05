package com.nm.users.creators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nm.datas.daos.DaoAppData;
import com.nm.datas.models.AppData;
import com.nm.soa.UserService;
import com.nm.users.dtos.UserForm;
import com.nm.users.models.User;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Component(UserCreator.DEFAULT)
@Qualifier(UserCreator.STRATEGY)
public class UserCreatorDefault implements UserCreator {
	@Autowired
	protected UserService userService;
	@Autowired
	private DaoAppData appDataDao;

	public User createOrUpdate(User user, UserForm userForm) {
		IGenericDao<User, Long> userDao = AbstractGenericDao.get(User.class);
		user.setCivility(userForm.getType());
		user.setEmail(userForm.getEmail());
		user.setFirstname(userForm.getFirstname());
		user.setMaster(false);
		user.setName(userForm.getName());
		user.setPersonnalEmail(userForm.getPersonnalEmail());
		user.setPhone(userForm.getPhone());
		// New user => enable by default
		if (userForm.getId() == null) {
			user.setEnable(true);
		}
		//
		if (userForm.getIdAvatar() != null) {
			AppData data = appDataDao.load(userForm.getIdAvatar());
			user.setAvatar(data);
		}
		userDao.saveOrUpdate(user);
		userForm.setId(user.getId());
		return user;
	}

}
