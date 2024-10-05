package com.nm.social.tests;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.facebook.api.TestUser;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import com.google.api.client.util.Lists;
import com.nm.auths.configurations.ConfigurationAuthenticationsSecurity;
import com.nm.social.templates.fb.FacebookTemplateCustom;
import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FacebookTestUtils {
	static java.util.Random randForName = new java.util.Random();

	static {
		// webClient.waitForBackgroundJavaScript(500);
		// webClient.waitForBackgroundJavaScriptStartingBefore(500);
	}

	public static TestUser findTestUser(FacebookTemplateCustom template, String id) {
		return template.testUserTemplateCustom().findTestUsers(id);
	}

	public static TestUser createMainUser(FacebookTemplate temlate) throws IOException {
		return createTestUsers(temlate, 1).iterator().next();
	}

	public static FacebookTemplateCustom createMainUserTemplate(TestUser testUser1) throws IOException {
		return new FacebookTemplateCustom(testUser1.getAccessToken());
	}

	public static void setFriends(FacebookTemplate template, TestUser main, Collection<TestUser> friends) {
		for (TestUser u : friends) {
			template.testUserOperations().sendConfirmFriends(main, u);
		}
	}

	public static Collection<TestUser> createTestUsers(FacebookTemplate temlate, int nb) throws IOException {
		List<String> names = FileUtils
				.readLines(new File(FacebookTestUtils.class.getResource("names.female.txt").getFile()));
		Collection<TestUser> users = Lists.newArrayList();
		for (int i = 0; i < nb; i++) {
			String first = StringUtils.substringBefore(names.get(randForName.nextInt(names.size())), " ");
			String last = StringUtils.substringBefore(names.get(randForName.nextInt(names.size())), " ");
			String name = String.format("%s %s", first, last);
			name = WordUtils.capitalizeFully(name);
			users.add(temlate.testUserOperations().createTestUser(true, "manage_friendlists", name));
		}
		return users;
	}

	public static void deleteTestUsers(FacebookTemplate temlate, TestUser... users) {
		for (TestUser u : users) {
			temlate.testUserOperations().deleteTestUser(u.getId());
		}
	}

	public static void deleteTestUsers(FacebookTemplate temlate, Collection<TestUser> users) {
		for (TestUser u : users) {
			temlate.testUserOperations().deleteTestUser(u.getId());
		}
	}

	public static FacebookTemplateCustom buildTemplate() {
		OAuth2RestOperations r = ApplicationUtils.getBean(ConfigurationAuthenticationsSecurity.FACEBOOK_REST_TEMPLATE);
		OAuth2AccessToken token = r.getAccessToken();
		//
		return new FacebookTemplateCustom(token.getValue());
	}

}
