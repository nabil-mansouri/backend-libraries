package com.nm.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.nm.auths.constants.AuthenticationOptions;
import com.nm.auths.dtos.DtoAuthenticationOAuth;
import com.nm.auths.models.Authentication;
import com.nm.auths.oauth.TokenProcessor;
import com.nm.auths.oauth.google.TokenProcessorGoogle;
import com.nm.auths.soa.SoaAuthentication;
import com.nm.auths.tests.GoogleWebTestUtils;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestAuth.class)
public class TestGoogle {

	//
	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected MockHttpServletRequest request;

	@Autowired
	protected MockHttpSession session;
	@Autowired
	private SoaAuthentication soa;

	public TestGoogle() {
	}

	// TODO remember me
	// TODO openid (yahoo)
	@Test
	@Transactional
	public void testShouldCreateAuthenticateUserPass() throws Exception {
		DtoAuthenticationOAuth dto = new DtoAuthenticationOAuth();
		OptionsList options = new OptionsList();
		options.pushOverrides(TokenProcessor.class, new TokenProcessorGoogle());
		new GoogleWebTestUtils.CallGoogle() {

			@Override
			public void operation() throws Exception {
				soa.saveOrUpdate(dto, options.withOption(AuthenticationOptions.RedirectFromProvider));
				AbstractGenericDao.get(Authentication.class).flush();
			}
		}.call();
		new GoogleWebTestUtils.CallGoogle() {

			@Override
			public void operation() throws Exception {
				soa.authenticate(dto, options);
			}
		}.call();
		Assert.assertTrue(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
	}

}
