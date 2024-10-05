package com.nm.tests.permissions;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.nm.auths.dtos.DtoAuthenticationDefault;
import com.nm.auths.models.Authentication;
import com.nm.auths.models.User;
import com.nm.auths.models.UserGroup;
import com.nm.auths.soa.SoaAuthentication;
import com.nm.permissions.SoaPermission;
import com.nm.permissions.dtos.DtoPermissionEntry;
import com.nm.permissions.dtos.DtoPermissionsView;
import com.nm.permissions.dtos.DtoSubjectDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = { ConfigurationTestPermission.class })
public class TestGranter {

	@Autowired
	private SoaAuthentication soa;
	@Autowired
	private SoaPermission soaPermission;
	@Autowired
	private FakeService fakeService;
	//
	protected Log log = LogFactory.getLog(getClass());
	private User user = new User();
	private UserGroup group = new UserGroup();

	@org.junit.Before
	public void setup() throws Exception {
		// CREATE USER AND GROUP
		{
			DtoAuthenticationDefault dto = new DtoAuthenticationDefault("user1@example.com", "user1");
			soa.saveOrUpdate(dto, new OptionsList());
			user = AbstractGenericDao.get(User.class).get(dto.getUserId());
			group.setName("GROUP1");
			group.add(user);
			AbstractGenericDao.get(UserGroup.class).save(group);
		}
		// AUTHENTICATE
		DtoAuthenticationDefault dto = new DtoAuthenticationDefault("user1@example.com", "user1");
		soa.authenticate(dto, new OptionsList());
		AbstractGenericDao.get(Authentication.class).flush();
	}

	private Collection<DtoPermissionEntry> permission() throws Exception {
		DtoSubjectDefault dtoSubject = new DtoSubjectDefault();
		dtoSubject.setIdUser(user.getId());
		soaPermission.getOrCreate(dtoSubject, new OptionsList());
		DtoPermissionsView view = (DtoPermissionsView) soaPermission.viewGrid(dtoSubject, new OptionsList());
		//
		Collection<DtoPermissionEntry> entires = Lists.newArrayList();
		for (DtoPermissionEntry entry : view.getValues()) {
			if (entry.getSelectedSafe()) {
				entires.add(entry);
			}
		}
		return entires;
	}

	@Test
	public void testSouldGrantAuthorities() throws Exception {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Assert.assertFalse(authorities.isEmpty());
		Assert.assertEquals(permission().size(), authorities.size());
	}

	@Test
	public void testSouldAcceptCallingBecauseOfPermission() throws Exception {
		fakeService.authorized();
	}

	@Test(expected = org.springframework.security.access.AccessDeniedException.class)
	public void testSouldRefuseCallingBecauseNoPermission() throws Exception {
		fakeService.notAuthorized();
	}
}
