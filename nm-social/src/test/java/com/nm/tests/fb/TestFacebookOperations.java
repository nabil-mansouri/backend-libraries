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
import com.nm.auths.tests.FacebookWebTestUtils.CallFB;
import com.nm.social.constants.SocialEventStatus;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.models.SocialEvent;
import com.nm.social.models.SocialUser;
import com.nm.social.operations.SocialOperationLauncher;
import com.nm.tests.ConfigurationTestSocial;
import com.nm.tests.FakeModelMessage;
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
public class TestFacebookOperations {
	private String mainUserId;
	private IGenericDao<SocialUser, Long> dao;
	@Autowired
	private SocialOperationLauncher launcher;
	private List<? extends AuthenticationProvider> providers = Arrays.asList(AuthenticationProviderDefault.Facebook);

	//
	@Before
	public void setup() throws Exception {
		dao = AbstractGenericDao.get(SocialUser.class);
		mainUserId = "103487830069675";
		new CallFB() {
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
				.withAuthProvider(AuthenticationProviderDefault.Facebook).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
	}

	@Test
	@Transactional
	public void testShouldSaveUsers() throws Exception {
		new CallFB() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadFriend);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Facebook).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
		SocialUser soc = users.iterator().next();
		Assert.assertFalse(soc.getFriends().isEmpty());
		System.out.println(soc.getFriends().size());
		Assert.assertEquals(29, soc.getFriends().size());
		Assert.assertNotNull(soc.getDetails());
		Assert.assertEquals(mainUserId, soc.getDetails().getFacebook().getId());
	}

	@Test
	@Transactional
	public void testShouldSaveUsersOnlyOnce() throws Exception {
		for (int i = 0; i < 3; i++) {
			new CallFB() {
				@Override
				public void operation() throws Exception {
					List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadFriend);
					launcher.oauth2(providers, op);
					dao.flush();
				}
			}.call();
		}
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Facebook).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
		SocialUser soc = users.iterator().next();
		Assert.assertFalse(soc.getFriends().isEmpty());
		System.out.println(soc.getFriends().size());
		Assert.assertEquals(29, soc.getFriends().size());
		Assert.assertNotNull(soc.getDetails());
		Assert.assertEquals(mainUserId, soc.getDetails().getFacebook().getId());
	}

	// SAVE GROUPS IS NOT YET AVAILABLE
	@Test
	@Transactional
	public void testShouldSavePages() throws Exception {
		new CallFB() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadNetworks);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Facebook).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
		SocialUser soc = users.iterator().next();
		dao.flush();
		dao.refresh(soc);
		Assert.assertFalse(soc.getNetworksOwned().isEmpty());
	}

	@Test
	@Transactional
	public void testShouldLoadEvents() throws Exception {
		new CallFB() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadEvents);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Facebook).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
		SocialUser soc = users.iterator().next();
		Assert.assertFalse(soc.getEvents().isEmpty());
		Assert.assertEquals(1, soc.getEvents().get(SocialEventStatus.Created).getEvents().size());
		Assert.assertEquals(1, soc.getEvents().get(SocialEventStatus.Attending).getEvents().size());
		SocialEvent ev1 = soc.getEvents().get(SocialEventStatus.Created).getEvents().iterator().next();
		SocialEvent ev2 = soc.getEvents().get(SocialEventStatus.Created).getEvents().iterator().next();
		Assert.assertEquals(ev1.getId(), ev2.getId());
	}

	@Test
	@Transactional
	public void testShouldPostObject() throws Exception {
		// LOAD NETWORKS TO POST ON IT
		new CallFB() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadNetworks);
				launcher.oauth2(providers, op);
				dao.flush();
			}
		}.call();
		// POST INTO WALL AND PAGE
		final FakeModelMessage model = new FakeModelMessage();
		model.setDesc("Description.................");
		model.setImage("http://nabil.e-devservice.com/downloads/images/fbapi_1.png");
		model.setTitle("Title.........");
		// URL MUST CONTAINS IMAGE TO BE SEEN
		model.setUrl("http://nabil.e-devservice.com/2013/04/13/javascript-facebook-graph-api/");
		model.setWall(true);
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Facebook).withUUID(mainUserId));
		SocialUser soc = users.iterator().next();
		dao.flush();
		dao.refresh(soc);
		Assert.assertFalse(soc.getNetworksOwned().isEmpty());
		model.setNetworks(soc.getNetworksOwned());
		//
		new CallFB() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadFriend,
						SocialOperationEnumDefault.PublishPost);
				launcher.oauth2(providers, op, model);
				dao.flush();
			}
		}.call();
		dao.flush();
	}

}
