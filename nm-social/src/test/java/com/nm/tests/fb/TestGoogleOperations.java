package com.nm.tests.fb;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nm.auths.constants.AuthenticationProvider;
import com.nm.auths.constants.AuthenticationProvider.AuthenticationProviderDefault;
import com.nm.auths.tests.GoogleWebTestUtils;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationLauncher;
import com.nm.tests.ConfigurationTestSocial;
import com.nm.tests.FakeModelEvents;
import com.nm.utils.hibernate.IGenericDao;
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
@ContextConfiguration(classes = ConfigurationTestSocial.class)
public class TestGoogleOperations {
	private String mainUserId;
	private IGenericDao<SocialUser, Long> dao;
	@Autowired
	private SocialOperationLauncher launcher;
	private List<? extends AuthenticationProvider> providers = Arrays.asList(AuthenticationProviderDefault.Google);

	//
	@Before
	public void setup() throws Exception {
		dao = AbstractGenericDao.get(SocialUser.class);
		mainUserId = "112955911333328822675";
		new GoogleWebTestUtils.CallGoogle() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadMe);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
	}

	@Test
	@Transactional
	public void testShouldSaveMe() throws Exception {
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Google).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
	}

	@Test
	@Transactional
	public void testShouldSaveUsers() throws Exception {
		new GoogleWebTestUtils.CallGoogle() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadFriend);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Google).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
		SocialUser soc = users.iterator().next();
		Assert.assertFalse(soc.getFriends().isEmpty());
		System.out.println(soc.getFriends().size());
		Assert.assertEquals(1, soc.getFriends().size());
		Assert.assertNotNull(soc.getDetails());
		Assert.assertEquals(mainUserId, soc.getDetails().getGoogle().getId());
	}

	@Test
	@Transactional
	public void testShouldPostEvents() throws Exception {
		// LOAD NETWORKS TO POST ON IT
		new GoogleWebTestUtils.CallGoogle() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadFriend);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
		// POST INTO WALL AND PAGE
		final FakeModelEvents model = new FakeModelEvents();
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Google).withUUID(mainUserId));
		SocialUser soc = users.iterator().next();
		Assert.assertFalse(soc.getFriends().isEmpty());
		model.setInvite(soc.getFriends());
		//
		new GoogleWebTestUtils.CallGoogle() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.PublishEvents);
				launcher.oauth2(providers, op, model);
				dao.flush();
			}
		}.call();
	}

	@Test
	@Transactional
	public void testShouldPublishPost() throws Exception {
		// LOAD NETWORKS TO POST ON IT
		new GoogleWebTestUtils.CallGoogle() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.PublishPost);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
	}
}
