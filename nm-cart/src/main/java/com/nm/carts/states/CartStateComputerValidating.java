package com.nm.carts.states;

import com.nm.carts.dtos.CartDto;

/**
 * Check wether client information are required and setted
 * 
 * @author Nabil
 * 
 */
public class CartStateComputerValidating extends CartStateComputerChain {
	public void compute(CartDto dto) {
		// TODO Auto-generated method stub

	}
	// @Transactional(readOnly = true)
	// public void check(CartCheckerContext context, CartDto cart, String lang)
	// {
	// restaurantChecker.convert(cart.getRestaurant(),
	// cart.getRestaurant().getState());
	// }

	// TODO Auto-generated method stub
	// if (Objects.equals(cart.getType(), OrderType.Delivered)) {
	// if (cart.getClient() == null || cart.getClient().isIgnore()) {
	// cart.setNeedAddress(true);
	// }
	// }

	// @Transactional(readOnly = true)
	// public void check(CartCheckerContext context, CartDto cart, String lang)
	// {
	// cart.setAlreadyCommited(false);
	// if (cart.getIdOrder() != null) {
	// try {
	// Order order = daoOrder.loadById(cart.getIdOrder());
	// if (order.getTransaction() != null) {
	// if
	// (order.getTransaction().getLastState().getType().equals(TransactionStateType.Commit))
	// {
	// cart.setAlreadyCommited(true);
	// }
	// }
	// } catch (NoDataFoundException e) {
	// }
	// }
	// }

	@Override
	public CartStateContext onPrepare(CartStateContext context) throws Exception {
		// TODO Auto-generated method stub
		return prepare(context);
	}

	@Override
	public CartStateContext onTransition(CartStateContext context) {
		// TODO Auto-generated method stub
		return transition(context);
	}

}
