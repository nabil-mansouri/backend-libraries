package com.nm.tests;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import com.nm.auths.constants.AuthenticationOptions;
import com.nm.auths.dtos.DtoAuthenticationOAuth;
import com.nm.auths.models.Authentication;
import com.nm.auths.oauth.TokenProcessor;
import com.nm.auths.oauth.manager.OAuthContext;
import com.nm.auths.oauth.twitter.TokenProcessorTwitter;
import com.nm.auths.soa.SoaAuthentication;
import com.nm.auths.tests.TwitterWebTestUtils;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.dates.URLUtilsExt;
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
public class TestTwitter {

	//
	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected MockHttpServletRequest request;

	@Autowired
	protected MockHttpServletResponse response;

	@Autowired
	protected MockHttpSession session;
	@Autowired
	private SoaAuthentication soa;

	public TestTwitter() {
	}

	// TODO remember me
	// TODO openid (yahoo)
	@Test
	@Transactional
	public void testShouldCreateAuthenticateUserPass() throws Exception {
		DtoAuthenticationOAuth dto = new DtoAuthenticationOAuth();
		String urlRedirect = null;
		OptionsList options = new OptionsList();
		options.pushOverrides(TokenProcessor.class, new TokenProcessorTwitter());
		OAuthContext currentWeb = ApplicationUtils.getBean(OAuthContext.class);
		try {
			currentWeb.setCurrentURL("http://blog.localhost/");
			soa.saveOrUpdate(dto, options);
		} catch (Exception e) {
			Throwable ee = ExceptionUtils.getRootCause(e);
			e.printStackTrace();
			Assert.assertTrue(ee instanceof UserRedirectRequiredException);
			UserRedirectRequiredException eee = (UserRedirectRequiredException) ee;
			urlRedirect = TwitterWebTestUtils.simulate(eee);
		}
		try {
			currentWeb.setParameters(URLUtilsExt.getQueryParamsMap(urlRedirect));
			soa.saveOrUpdate(dto, options.withOption(AuthenticationOptions.RedirectFromProvider));
			AbstractGenericDao.get(Authentication.class).flush();
			soa.authenticate(dto, options);
		} catch (HttpClientErrorException e) {
			System.out.println(e.getResponseBodyAsString());
			throw e;
		}
	}

}
