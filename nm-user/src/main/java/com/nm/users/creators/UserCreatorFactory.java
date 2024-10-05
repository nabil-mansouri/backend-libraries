package com.nm.users.creators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nm.users.dtos.UserForm;
import com.nm.users.models.User;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Component
public class UserCreatorFactory {
	@Autowired
	@Qualifier(UserCreator.STRATEGY)
	private Map<String, UserCreator> strategies = new HashMap<String, UserCreator>();
	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	public UserCreator get(String strategy) {
		return strategies.get(strategy);
	}

	public Map<String, UserCreator> getStrategies() {
		return strategies;
	}

	@Transactional(readOnly = true)
	public User create(UserForm form) throws Exception {
		IGenericDao<User, Long> userDao = AbstractGenericDao.get(User.class);
		User user = new User();
		if (form.getId() != null) {
			user = userDao.get(form.getId());
		}
		//
		List<String> st = new ArrayList<String>();
		st.add(UserCreator.DEFAULT);
		st.add(UserCreator.ORG);
		st.add(UserCreator.GROUP);
		for (String s : st) {
			get(s).createOrUpdate(user, form);
		}
		return user;
	}

}
