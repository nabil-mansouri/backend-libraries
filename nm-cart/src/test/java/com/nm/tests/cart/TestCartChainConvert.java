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
import com.nm.carts.operations.CartOperationChainConvert;
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
public class TestCartChainConvert {
	@Autowired
	private SoaCart soaCart;
	@Autowired
	protected DaoCart daoCart;
	//
	private CartAdapter adapter = new CartAdapterImpl();
	private CartDtoExtImpl dto;
	private CartItemCompatibleImpl produ = new CartItemCompatibleImpl();

	@Before
	public void setup() throws Exception {
		// Pass context creation as arguents
		CartOwnerDtoImpl owner = new CartOwnerDtoImpl();
		owner.setSessionId("SESSIONID");
		dto = (CartDtoExtImpl) soaCart.getOrCreate(owner, adapter);
		dto.getOptions().add(CartItemOptions.Unique);
		//
		produ.setRoot(true);
		produ.setPrice(1.0);
		produ.setProduct(1l);
		//
		CartItemCompatibleImpl child = produ.createChild();
		child.setPrice(2D);
		child.setProduct(2l);
		CartItemCompatibleImpl cchild = child.createChild();
		cchild.setPrice(3D);
		cchild.setProduct(3l);
		cchild = child.createChild();
		cchild.setPrice(3D);
		cchild.setProduct(3l);
	}

	@Test()
	@Transactional
	public void testShouldCOnvertTree() throws Exception {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(dto)
				.setToPush(produ).setOperation(CartOperation.Push);
		new CartOperationChainConvert().onBefore(context);
		CartItemDtoExtImpl item = (CartItemDtoExtImpl) context.getItem();
		Assert.assertEquals(1D, item.getValue().getValue(), 0d);
		Assert.assertEquals(1l, item.getProduct(), 0);
		Assert.assertEquals(1l, item.getChildren().size());
		//
		item = (CartItemDtoExtImpl) item.getChildren().iterator().next();
		Assert.assertEquals(2D, item.getValue().getValue(), 0d);
		Assert.assertEquals(2l, item.getProduct(), 0);
		Assert.assertEquals(2l, item.getChildren().size());
		//
		item = (CartItemDtoExtImpl) item.getChildren().iterator().next();
		Assert.assertEquals(3D, item.getValue().getValue(), 0d);
		Assert.assertEquals(3l, item.getProduct(), 0);
		Assert.assertEquals(0l, item.getChildren().size());
	}

}
