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
import com.nm.carts.dtos.impl.CartOwnerDtoImpl;
import com.nm.carts.models.DaoCart;
import com.nm.carts.operations.CartOperationChainContext;
import com.nm.carts.operations.CartOperationChainSorted;
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
public class TestCartChainSorted {
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
		dto.getOptions().add(CartItemOptions.Sort);
		//
		produ.setRoot(true);
		produ.setGroup(1l);
		produ.setProduct(1l);
		item = (CartItemDtoExtImpl) produ.toItem(dto.createItem(true));
	}

	@Test()
	@Transactional
	public void testShouldPushGroups() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push).setItem(item);
		new CartOperationChainSorted().onBefore(context);
		Assert.assertNotNull(context.getExtra(CartOperationChainContext.PARENT));
		Assert.assertEquals(1, dto.childrens().size());
		//
		CartItemDtoExtImpl item = (CartItemDtoExtImpl) dto.childrens().get(0);
		Assert.assertEquals(1, item.childrens().size());
		Assert.assertEquals(produ.getGroup(), item.getGroup());
		Assert.assertNull(item.getProduct());
		//
		item = (CartItemDtoExtImpl) item.childrens().get(0);
		Assert.assertEquals(0, item.childrens().size());
		Assert.assertEquals(produ.getProduct(), item.getProduct());
		Assert.assertNull(item.getGroup());
	}

	@Test()
	@Transactional
	public void testShouldPushGroupsOnlyOnce() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push).setItem(item);
		new CartOperationChainSorted().onBefore(context);
		new CartOperationChainSorted().onBefore(context);
		new CartOperationChainSorted().onBefore(context);
		//
		Assert.assertNotNull(context.getExtra(CartOperationChainContext.PARENT));
		Assert.assertEquals(1, dto.childrens().size());
		//
		CartItemDtoExtImpl item = (CartItemDtoExtImpl) dto.childrens().get(0);
		Assert.assertEquals(1, item.childrens().size());
		Assert.assertEquals(produ.getGroup(), item.getGroup());
		Assert.assertNull(item.getProduct());
		//
		item = (CartItemDtoExtImpl) item.childrens().get(0);
		Assert.assertEquals(0, item.childrens().size());
		Assert.assertEquals(produ.getProduct(), item.getProduct());
		Assert.assertNull(item.getGroup());
	}

	@Test()
	@Transactional
	public void testShouldPushGroupsOnlyHalf() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push).setItem(item);
		new CartOperationChainSorted().onBefore(context);
		CartItemDtoExtImpl clonse = (CartItemDtoExtImpl) this.item.clone();
		clonse.setProduct(2l);
		context.setItem(clonse);
		new CartOperationChainSorted().onBefore(context);
		//
		Assert.assertNotNull(context.getExtra(CartOperationChainContext.PARENT));
		Assert.assertEquals(1, dto.childrens().size());
		//
		CartItemDtoExtImpl item = (CartItemDtoExtImpl) dto.childrens().get(0);
		Assert.assertEquals(2, item.childrens().size());
		Assert.assertEquals(produ.getGroup(), item.getGroup());
		Assert.assertNull(item.getProduct());
		//
		item = (CartItemDtoExtImpl) item.childrens().get(1);
		Assert.assertEquals(0, item.childrens().size());
		Assert.assertEquals(clonse.getProduct(), item.getProduct());
		Assert.assertNull(item.getGroup());
	}

	@Test()
	@Transactional
	public void testShouldFindParent() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push).setItem(item);
		new CartOperationChainSorted().onBefore(context);
		Assert.assertNotNull(context.getExtra(CartOperationChainContext.PARENT));
		CartItemDtoExtImpl prod = context.getExtra(CartOperationChainContext.PARENT, CartItemDtoExtImpl.class);
		Assert.assertEquals(prod.getProduct(), produ.getProduct());
		Assert.assertNull(prod.getGroup());
	}

	@Test()
	@Transactional
	public void testShouldFindParentWithHalf() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push).setItem(item);
		new CartOperationChainSorted().onBefore(context);
		CartItemDtoExtImpl clonse = (CartItemDtoExtImpl) this.item.clone();
		clonse.setProduct(2l);
		context.setItem(clonse);
		new CartOperationChainSorted().onBefore(context);
		//
		Assert.assertNotNull(context.getExtra(CartOperationChainContext.PARENT));
		CartItemDtoExtImpl prod = context.getExtra(CartOperationChainContext.PARENT, CartItemDtoExtImpl.class);
		Assert.assertNull(prod.getGroup());
		Assert.assertEquals(clonse.getProduct(), prod.getProduct());
	}

	@Test()
	@Transactional
	public void testShouldPushWithoutSort() throws Exception {
		dto.getOptions().remove(CartItemOptions.Sort);
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push).setItem(item);
		new CartOperationChainSorted().onBefore(context);
		Assert.assertNull(context.getExtra(CartOperationChainContext.PARENT));
		Assert.assertEquals(0, dto.childrens().size());
	}
}
