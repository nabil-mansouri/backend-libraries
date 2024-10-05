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
import com.nm.carts.exceptions.CartManagementException;
import com.nm.carts.models.DaoCart;
import com.nm.carts.operations.CartOperationChainContext;
import com.nm.carts.operations.CartOperationChainDuplicate;
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
public class TestChainDuplicate {
	@Autowired
	private SoaCart soaCart;
	@Autowired
	protected DaoCart daoCart;
	//
	private CartAdapter adapter = new CartAdapterImpl();
	private CartDtoExtImpl dto;
	private CartItemCompatibleImpl produ = new CartItemCompatibleImpl();
	private CartItemDtoExtImpl item;
	private CartOperationChainDuplicate chain = new CartOperationChainDuplicate();

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
		item = (CartItemDtoExtImpl) produ.toItem(dto.createItem(true));
	}

	@Test(expected = CartManagementException.class)
	@Transactional
	public void testShouldNotPushOnCartWithoutUnlocked() throws Exception {
		dto.setShop(null);
		dto.getOptions().add(CartItemOptions.Duplicate);
		soaCart.refresh(dto, adapter);
		Assert.assertTrue(dto.states().contains(CartStateDefault.NotReady));
		soaCart.push(dto, adapter, produ);
	}

	@Test
	@Transactional
	public void testShouldClearCart() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		//
		Assert.assertEquals(1, dto.childrens().size());
		context = new CartOperationChainContext().setAdapter(adapter).setCart(dto).setToPush(produ)
				.setOperation(CartOperation.Clear);
		chain.doAll(context);
		Assert.assertEquals(0, dto.childrens().size());
	}

	@Test
	@Transactional
	public void testShouldPushItem() throws Exception {
		Assert.assertEquals(0, dto.childrens().size());
		//
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		//
		Assert.assertEquals(1, dto.childrens().size());
	}

	@Test
	@Transactional
	public void testShouldPushItemMultiple() throws Exception {
		Assert.assertEquals(0, dto.childrens().size());
		//
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		//
		Assert.assertEquals(1, dto.childrens().size());
		CartItemDtoExtImpl ext = (CartItemDtoExtImpl) dto.childrens().get(0);
		Assert.assertEquals(1, ext.getQuantity());
		//
		chain.doAll(context);
		//
		Assert.assertEquals(2, dto.childrens().size());
		CartItemDtoExtImpl ext1 = (CartItemDtoExtImpl) dto.childrens().get(0);
		CartItemDtoExtImpl ext2 = (CartItemDtoExtImpl) dto.childrens().get(0);
		Assert.assertEquals(1, ext2.getQuantity());
		Assert.assertEquals(ext1.getProduct(), ext2.getProduct());
	}

	@Test
	@Transactional
	public void testShouldAddItem() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		//
		context.setOperation(CartOperation.Add).getRequest().setNumber(3l).setUuid(item.getUuid());
		//
		chain.doAll(context);
		//
		Assert.assertEquals(4, dto.childrens().size());
		CartItemDtoExtImpl ext1 = (CartItemDtoExtImpl) dto.childrens().get(0);
		CartItemDtoExtImpl ext2 = (CartItemDtoExtImpl) dto.childrens().get(0);
		Assert.assertEquals(1, ext2.getQuantity());
		Assert.assertEquals(ext1.getProduct(), ext2.getProduct());
	}

	@Test(expected = CartManagementException.class)
	@Transactional
	public void testShouldNotOperateItemOnNotReady() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		dto.setState(CartStateDefault.NotReady);
		chain.doAll(context);
	}

	@Test(expected = CartManagementException.class)
	@Transactional
	public void testShouldNotAddAbsentItem() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto);
		context.getRequest().setOperation(CartOperation.Add).setNumber(3l).setUuid(item.getUuid());
		//
		chain.doAll(context);
	}

	@Test
	@Transactional
	public void testShouldSubstractItem() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		context.setOperation(CartOperation.Add).getRequest().setNumber(3l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(4, dto.childrens().size());
		//
		context.setOperation(CartOperation.Substract).getRequest().setNumber(3l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(1, dto.childrens().size());
	}

	@Test
	@Transactional
	public void testShouldSubstractItemThenDeleteZero() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		context.setOperation(CartOperation.Add).getRequest().setNumber(3l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		CartItemDtoExtImpl ext = (CartItemDtoExtImpl) dto.childrens().get(0);
		Assert.assertEquals(4, dto.childrens().size());
		Assert.assertEquals(1, ext.getQuantity());
		//
		context.setOperation(CartOperation.Substract).getRequest().setNumber(4l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(0, dto.childrens().size());
	}

	@Test
	@Transactional
	public void testShouldChangeItemNumber() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		context.setOperation(CartOperation.Replace).getRequest().setNumber(4l).setUuid(item.getUuid());
		chain.doAll(context);
		Assert.assertEquals(4, dto.childrens().size());
		//
		context.setOperation(CartOperation.Replace).getRequest().setNumber(2l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(2, dto.childrens().size());
	}

	@Test
	@Transactional
	public void testShouldChangeItemNumberThenDeleteZero() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		context.setOperation(CartOperation.Add).getRequest().setNumber(3l).setUuid(item.getUuid());
		chain.doAll(context);
		Assert.assertEquals(4, dto.childrens().size());
		//
		context.setOperation(CartOperation.Replace).getRequest().setNumber(0l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(0, dto.childrens().size());
	}

	@Test
	@Transactional
	public void testShouldMultiplyItemNumber() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		context.setOperation(CartOperation.Add).getRequest().setNumber(3l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(4, dto.childrens().size());
		//
		context.setOperation(CartOperation.Multiply).getRequest().setNumber(2l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(8, dto.childrens().size());
	}

	@Test
	@Transactional
	public void testShouldMultiplyItemNumberThenDeleteZero() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setItem(item).setOperation(CartOperation.Push);
		chain.doAll(context);
		context.setOperation(CartOperation.Add).getRequest().setNumber(3l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(4, dto.childrens().size());
		//
		context.setOperation(CartOperation.Multiply).getRequest().setNumber(0l).setUuid(item.getUuid());
		chain.doAll(context);
		//
		Assert.assertEquals(0, dto.childrens().size());
	}
}
