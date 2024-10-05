package com.nm.tests.cart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.carts.contract.CartAdapter;
import com.nm.carts.dtos.impl.CartOwnerDtoImpl;
import com.nm.carts.models.Cart;
import com.nm.carts.models.DaoCart;
import com.nm.carts.soa.SoaCart;
import com.nm.tests.bridge.CartAdapterImpl;
import com.nm.tests.bridge.CartDtoExtImpl;
import com.nm.tests.bridge.ConfigurationTestCart;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestCart.class)
public class TestCartCrud {
	@Autowired
	private SoaCart soaCart;
	@Autowired
	private DaoCart daoCart;
	//
	private CartAdapter adapter = new CartAdapterImpl();

	@Before
	public void setup() {

	}

	@Test
	@Transactional
	public void testShouldCreateCartWithOwner() throws Exception {
		// Pass context creation as arguents
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		Assert.assertNotNull(dto);
		Assert.assertNotNull(dto.getId());
		Assert.assertEquals("SESSIONID", owner.getSessionId());
		daoCart.flush();
		Assert.assertNotNull(daoCart.get(dto.getId()).getOwner());
	}

	@Test
	@Transactional
	public void testShouldGetOrCreateCartWithOwner() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		soaCart.refresh(dto, adapter);
		daoCart.flush();
		Cart cart = daoCart.get(dto.getId());
		Assert.assertNotNull(cart.getOwner());
		//
		CartDtoExtImpl dto2 = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		Assert.assertEquals(dto.getId(), dto2.getId());
	}

	@Test
	@Transactional
	public void testShouldRemoveCart() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		Assert.assertNotNull(dto.getId());
		Cart cart = daoCart.get(dto.getId());
		Assert.assertNotNull(cart);
		soaCart.remove(dto);
		boolean failed = false;
		try {
			cart = daoCart.get(dto.getId());
		} catch (Exception e) {
			failed = true;
		}
		Assert.assertTrue(failed);
	}
}
