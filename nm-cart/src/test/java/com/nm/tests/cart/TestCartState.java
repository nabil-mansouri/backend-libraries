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

import com.nm.carts.constants.CartOperation;
import com.nm.carts.contract.CartAdapter;
import com.nm.carts.contract.CartEventDefault;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.dtos.impl.CartOwnerDtoImpl;
import com.nm.carts.dtos.impl.CartRequestDtoImpl;
import com.nm.carts.models.DaoCart;
import com.nm.carts.soa.SoaCart;
import com.nm.tests.bridge.CartAdapterImpl;
import com.nm.tests.bridge.CartDtoExtImpl;
import com.nm.tests.bridge.CartItemDtoExtImpl;
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
public class TestCartState {
	@Autowired
	private SoaCart soaCart;
	@Autowired
	protected DaoCart daoCart;
	//
	private CartAdapter adapter = new CartAdapterImpl();

	@Before
	public void setup() {

	}

	@Test
	@Transactional
	public void testShouldInitCart() throws Exception {
		// Pass context creation as arguents
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NotReady));
		Assert.assertTrue(dto.getConstraints().contains(CartAdapterImpl.REQUIREDMODE));
		Assert.assertTrue(dto.getConstraints().contains(CartAdapterImpl.REQUIREDSHOP));
	}

	@Test
	@Transactional
	public void testShouldRefresheGettedCart() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		soaCart.refresh(dto, adapter);
		Assert.assertFalse(dto.getConstraints().contains(CartAdapterImpl.REQUIREDMODE));
		daoCart.flush();
		//
		CartDtoExtImpl dto2 = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		Assert.assertFalse(dto2.getConstraints().contains(CartAdapterImpl.REQUIREDMODE));
	}

	@Test
	@Transactional
	public void testShouldChangeLockedStateOnRefresh() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NotReady));
		Assert.assertTrue(dto.getConstraints().contains(CartAdapterImpl.REQUIREDMODE));
		Assert.assertTrue(dto.getConstraints().contains(CartAdapterImpl.REQUIREDSHOP));
		//
		dto.setMode("1");
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NotReady));
		Assert.assertFalse(dto.getConstraints().contains(CartAdapterImpl.REQUIREDMODE));
		Assert.assertTrue(dto.getConstraints().contains(CartAdapterImpl.REQUIREDSHOP));
		//
		dto.setShop("1");
		soaCart.refresh(dto, adapter);
		Assert.assertFalse(dto.states().contains(CartStateDefault.NotReady));
		Assert.assertFalse(dto.getConstraints().contains(CartAdapterImpl.REQUIREDMODE));
		Assert.assertFalse(dto.getConstraints().contains(CartAdapterImpl.REQUIREDSHOP));
	}

	@Test
	@Transactional
	public void testShouldSetNotReady() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		dto.setShop("1");
		soaCart.refresh(dto, adapter);
		Assert.assertFalse(dto.states().contains(CartStateDefault.NotReady));
		dto.setShop(null);
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NotReady));
		//
		dto.setShop("1");
		dto.getRows().add(new CartItemDtoExtImpl());
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NonEmpty));
		//
		dto.setShop(null);
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NotReady));
	}

	@Test
	@Transactional
	public void testShouldSetReady() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		dto.setShop("1");
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.Ready));
	}

	@Test
	@Transactional
	public void testShouldSetNonEmpty() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		dto.setShop("1");
		dto.getRows().add(new CartItemDtoExtImpl());
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NonEmpty));
	}

	@Test
	@Transactional
	public void testShouldSetEmpty() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		dto.setShop("1");
		dto.getRows().add(new CartItemDtoExtImpl());
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NonEmpty));
		dto.getRows().clear();
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.Ready));
	}

	// TODO order with prepayment or not
	@Test
	@Transactional
	public void testShouldSetValidating() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		dto.setShop("1");
		dto.getRows().add(new CartItemDtoExtImpl());
		soaCart.request(dto, adapter, new CartRequestDtoImpl().setOperation(CartOperation.Validate)
				.setEvent(CartEventDefault.ValidatingEvent));
		System.out.println("-------------------");
		System.out.println(dto.states());
		Assert.assertTrue(dto.states().contains(CartStateDefault.Validating));
	}

	@Test
	@Transactional
	public void testShouldSetReadyAfterValid() throws Exception {
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		CartDtoExtImpl dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.setMode("1");
		dto.setShop("1");
		dto.getRows().add(new CartItemDtoExtImpl());
		soaCart.request(dto, adapter, new CartRequestDtoImpl().setOperation(CartOperation.Validate)
				.setEvent(CartEventDefault.ValidatingEvent));
		Assert.assertTrue(dto.states().contains(CartStateDefault.Validating));
		//
		soaCart.request(dto, adapter, new CartRequestDtoImpl().setOperation(CartOperation.Add).setNumber(1l)
				.setUuid(dto.getRows().iterator().next().uuid()).setEvent(CartEventDefault.ValidatingEvent));
		Assert.assertTrue(dto.states().contains(CartStateDefault.NonEmpty));
	}
}
