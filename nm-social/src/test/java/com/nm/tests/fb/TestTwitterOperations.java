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
import com.nm.auths.tests.TwitterWebTestUtils;
import com.nm.social.constants.SocialOperationEnum;
import com.nm.social.constants.SocialOperationEnum.SocialOperationEnumDefault;
import com.nm.social.daos.QueryBuilderSocialUser;
import com.nm.social.models.SocialNetwork;
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
public class TestTwitterOperations {
	private String mainUserId;
	private IGenericDao<SocialUser, Long> dao;
	@Autowired
	private SocialOperationLauncher launcher;
	private List<? extends AuthenticationProvider> providers = Arrays.asList(AuthenticationProviderDefault.Twitter);

	//
	@Before
	public void setup() throws Exception {
		dao = AbstractGenericDao.get(SocialUser.class);
		mainUserId = "";
		new TwitterWebTestUtils.CallTwitter() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadMe);
				launcher.oauth1(providers, op);
				dao.flush();
			}
		}.call();
	}

	@Test
	@Transactional
	public void testShouldSaveMe() throws Exception {
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Twitter).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
	}

	@Test
	@Transactional
	public void testShouldSaveUsers() throws Exception {
		new TwitterWebTestUtils.CallTwitter() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadFriend);
				launcher.oauth1(providers, op);
				dao.flush();
			}
		}.call();
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Twitter).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
		SocialUser soc = users.iterator().next();
		Assert.assertFalse(soc.getFriends().isEmpty());
		System.out.println(soc.getFriends().size());
		Assert.assertNotEquals(0, soc.getFriends().size());
		Assert.assertNotNull(soc.getDetails());
		Assert.assertEquals(mainUserId, String.valueOf(soc.getDetails().getTwitter().getId()));
	}

	@Test
	@Transactional
	public void testShouldSaveList() throws Exception {
		new TwitterWebTestUtils.CallTwitter() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadNetworks);
				launcher.oauth1(providers, op);
				dao.flush();
			}
		}.call();
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Twitter).withUUID(mainUserId));
		Assert.assertEquals(1, users.size());
		SocialUser soc = users.iterator().next();
		dao.refresh(soc);
		Assert.assertFalse(soc.getNetworksOwned().isEmpty());
		SocialNetwork net = soc.getNetworksOwned().iterator().next();
		Assert.assertFalse(net.getUsers().isEmpty());
	}

	@Test
	@Transactional
	public void testShouldPostObject() throws Exception {
		// LOAD NETWORKS TO POST ON IT
		new TwitterWebTestUtils.CallTwitter() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.LoadFriend,
						SocialOperationEnumDefault.LoadNetworks);
				launcher.oauth1(providers, op);
				dao.flush();
			}
		}.call();
		// TWEET IT INTO WALL AND LIST
		final FakeModelMessage model = new FakeModelMessage();
		model.setDesc("Description.................");
		model.setImage("http://nabil.com/downloads/images/fbapi_1.png");
		model.setTitle("Title.........");
		// URL MUST CONTAINS IMAGE TO BE SEEN
		model.setUrl("http://nabil.com/2013/04/13/javascript-facebook-graph-api/");
		model.setWall(true);
		//
		Collection<SocialUser> users = dao.find(QueryBuilderSocialUser.get()
				.withAuthProvider(AuthenticationProviderDefault.Twitter).withUUID(mainUserId));
		SocialUser soc = users.iterator().next();
		dao.refresh(soc);
		Assert.assertFalse(soc.getNetworksOwned().isEmpty());
		model.setNetworks(soc.getNetworksOwned());
		model.setDirect(true);
		//
		new TwitterWebTestUtils.CallTwitter() {
			@Override
			public void operation() throws Exception {
				List<? extends SocialOperationEnum> op = Arrays.asList(SocialOperationEnumDefault.PublishPost);
				launcher.oauth1(providers, op, model);
				dao.flush();
			}
		}.call();
	}
}
