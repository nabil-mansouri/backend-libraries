package com.nm.carts.operations;

import com.nm.carts.exceptions.CartManagementException;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class CartOperationChainFinally extends CartOperationChain {

	@Override
	public CartOperationChainContext onAfter(CartOperationChainContext context) throws CartManagementException {
		context.getCart().compute();
		return after(context);
	}

	@Override
	public CartOperationChainContext onBefore(CartOperationChainContext context) throws CartManagementException {
		return before(context);
	}

	@Override
	public CartOperationChainContext onExecute(CartOperationChainContext context) throws CartManagementException {
		return execute(context);
	}
}
