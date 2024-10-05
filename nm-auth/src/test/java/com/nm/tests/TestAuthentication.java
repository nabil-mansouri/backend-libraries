package com.nm.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.auths.dtos.DtoAuthenticationDefault;
import com.nm.auths.models.Authentication;
import com.nm.auths.soa.SoaAuthentication;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestAuth.class)
public class TestAuthentication {

	//
	protected Log log = LogFactory.getLog(getClass());
	@Autowired
	private SoaAuthentication soa;

	@Before
	public void setup() {
	}

	@Test(expected = BadCredentialsException.class)
	@Transactional
	public void testShouldNotAuthenticateUserPassBecauseNotExists() throws Throwable {
		try {
			DtoAuthenticationDefault dto = new DtoAuthenticationDefault("user1@example.com", "user1");
			soa.authenticate(dto, new OptionsList());
		} catch (Exception e) {
			throw e.getCause();
		}
	}

	@Test(expected = BadCredentialsException.class)
	@Transactional
	public void testShouldAuthenticateUserPassShouldFail() throws Throwable {
		try {
			DtoAuthenticationDefault dto = new DtoAuthenticationDefault("user1@example.com", "user1");
			soa.saveOrUpdate(dto, new OptionsList());
			dto = new DtoAuthenticationDefault("user1@example.com", "user2");
			soa.authenticate(dto, new OptionsList());
		} catch (Exception e) {
			throw e.getCause();
		}
	}

	@Test()
	@Transactional
	public void testShouldAuthenticateUserPass() throws Exception {
		DtoAuthenticationDefault dto = new DtoAuthenticationDefault("user1@example.com", "user1");
		soa.saveOrUpdate(dto, new OptionsList());
		dto = new DtoAuthenticationDefault("user1@example.com", "user1");
		AbstractGenericDao.get(Authentication.class).flush();
		soa.authenticate(dto, new OptionsList());
		AbstractGenericDao.get(Authentication.class).flush();
	}

}
