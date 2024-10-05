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
import com.nm.carts.contract.CartItemOptions;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.dtos.impl.CartOwnerDtoImpl;
import com.nm.carts.models.DaoCart;
import com.nm.carts.operations.CartOperationChain;
import com.nm.carts.operations.CartOperationChainContext;
import com.nm.carts.soa.SoaCart;
import com.nm.tests.bridge.CartAdapterImpl;
import com.nm.tests.bridge.CartDtoExtImpl;
import com.nm.tests.bridge.CartItemCompatibleImpl;
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
public class TestChainFinally {
	@Autowired
	private SoaCart soaCart;
	@Autowired
	protected DaoCart daoCart;
	//
	private CartAdapter adapter = new CartAdapterImpl();
	private CartDtoExtImpl dto;
	private CartItemCompatibleImpl produ = new CartItemCompatibleImpl();
	private CartItemDtoExtImpl item;

	@Before
	public void setup() throws Exception {
		// Pass context creation as arguents
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.getOptions().add(CartItemOptions.Duplicate);
		dto.setMode("1");
		dto.setShop("11");
		soaCart.refresh(dto, adapter);
		Assert.assertNotNull(dto.getId());
		Assert.assertFalse(dto.states().contains(CartStateDefault.NotReady));
		//
		produ.setRoot(true);
		produ.setPrice(2.0);
		produ.setProduct(1l);
		CartItemCompatibleImpl c = produ.createChild();
		c.setProduct(1l);
		c.setPrice(2d);
	}

	@Test
	@Transactional
	public void testShouldEmptyTotal() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Validate);
		CartOperationChain.process(context);
		//
		Assert.assertEquals(0, dto.getSubtotal(), 0d);
	}

	@Test
	@Transactional
	public void testShouldComputeTotal() throws Exception {
		Assert.assertEquals(0, dto.childrens().size());
		//
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push);
		CartOperationChain.process(context);
		//
		Assert.assertEquals(4d, dto.getSubtotal(), 0d);
	}

	@Test
	@Transactional
	public void testShouldComputeSubTotals() throws Exception {
		Assert.assertEquals(0, dto.childrens().size());
		//
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push);
		CartOperationChain.process(context);
		//
		Assert.assertEquals(4d, dto.getSubtotal(), 0d);
		CartItemDtoExtImpl ext = (CartItemDtoExtImpl) dto.getRows().get(0);
		Assert.assertEquals(4d, ext.getSubtotal(), 0d);
		ext = (CartItemDtoExtImpl) ext.getChildren().iterator().next();
		Assert.assertEquals(2d, ext.getSubtotal(), 0d);
	}

}
