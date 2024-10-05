package com.nm.tests.permissions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.util.Lists;
import com.nm.datas.models.AppDataByte;
import com.nm.permissions.acl.AclException;
import com.nm.permissions.acl.EnumObjectID;
import com.nm.permissions.acl.EnumPermission;
import com.nm.permissions.acl.EnumSID;
import com.nm.permissions.acl.PermissionFactoryCustom;
import com.nm.permissions.acl.SoaAcl;
import com.nm.permissions.acl.dtos.DtoAclImpl;
import com.nm.permissions.acl.dtos.DtoAclPermission;
import com.nm.permissions.configurations.ConfigurationPermissionsAcl;
import com.nm.permissions.constants.ActionDefault;
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
public class TestAcl {

	@Autowired
	private SoaAcl soaAcl;
	@Autowired
	private PermissionFactoryCustom factory;
	private DtoAclImpl dto;

	@org.junit.Before
	public void setup() throws Exception {
		dto = new DtoAclImpl();
		dto.setIdentity(EnumObjectID.JavaClass);
		dto.setClazz(getClass());
		dto.setId(getId());
		//
		dto.setPermission(EnumPermission.ByAction);
		dto.setPermissionAction(ActionDefault.Access);
		//
		dto.setSid(EnumSID.CurrentUser);
		//
		dto.setGrant(true);
		// FAKE AUTH
		Collection<? extends GrantedAuthority> col = Arrays
				.asList(new SimpleGrantedAuthority(ConfigurationPermissionsAcl.ROLE_ADMIN));
		Authentication auth = new UsernamePasswordAuthenticationToken("username", null, col);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@After
	public void tearDown() throws AclException {
		// MUST CLEAR WHEN LAUNCHING ALL TEST
		soaAcl.deleteAcl(dto, true);
	}

	@Test
	public void testShouldSaveAcl() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
	}

	@Test
	public void testShouldDeleteAcl() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
		soaAcl.deleteAcl(dto, true);
	}

	// NEED IT FOR TEST
	public Long getId() {
		return 1l;
	}

	@Test
	public void testShouldAuthorize() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
		Permission p = factory.buildFromAction(ActionDefault.Access);
		soaAcl.checkAuthorization(this, p);
	}

	@Test(expected = AccessDeniedException.class)
	public void testShouldNotAuthorizeDelete() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
		soaAcl.deleteAcl(dto, true);
		Permission p = factory.buildFromAction(ActionDefault.Access);
		soaAcl.checkAuthorization(this, p);
	}

	@Test(expected = AccessDeniedException.class)
	public void testShouldNotAuthorizeUnkwnownAction() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
		Permission p = factory.buildFromAction(ActionDefault.Export);
		soaAcl.checkAuthorization(this, p);
	}

	@Test(expected = AccessDeniedException.class)
	public void testShouldNotAuthorizeUnkwnownObject() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
		Permission p = factory.buildFromAction(ActionDefault.Export);
		soaAcl.checkAuthorization(new AppDataByte(), p);
	}

	@Test
	public void testShouldAuthorizeDto() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
		Collection<? extends DtoAclPermission> p = Arrays.asList(dto);
		soaAcl.checkAuthorization(dto, p);
	}

	@Test
	public void testShouldFilterList() throws Exception {
		Serializable id = soaAcl.saveAcl(dto);
		Assert.assertNotNull(id);
		Collection<Object> list = Lists.newArrayList();
		list.add(this);
		list.add(new TestAcl() {
			@Override
			public Long getId() {
				return 2l;
			}
		});
		Permission p = factory.buildFromAction(ActionDefault.Access);
		list = soaAcl.filter(list, p);
		Assert.assertEquals(1, list.size());
		Assert.assertTrue(this.getClass().isInstance(list.iterator().next()));
	}
}
