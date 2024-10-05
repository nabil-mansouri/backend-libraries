package com.nm.carts.operations;

import com.nm.carts.exceptions.CartManagementException;
import com.nm.carts.states.CartStateComputerChain;
import com.nm.carts.states.CartStateContext;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class CartOperationChainState extends CartOperationChain {

	@Override
	public CartOperationChainContext onAfter(CartOperationChainContext context) throws CartManagementException {
		CartStateContext sContext = new CartStateContext().setCart(context.getCart())
				.setCartAdapter(context.getAdapter()).setRequest(context.getRequest());
		CartStateComputerChain.process(sContext);
		return after(context);
	}

	@Override
	public CartOperationChainContext onBefore(CartOperationChainContext context) throws CartManagementException {
		CartStateContext sContext = new CartStateContext().setCart(context.getCart())
				.setCartAdapter(context.getAdapter()).setRequest(context.getRequest());
		CartStateComputerChain.process(sContext);
		return before(context);
	}

	@Override
	public CartOperationChainContext onExecute(CartOperationChainContext context) throws CartManagementException {
		return execute(context);
	}
}
