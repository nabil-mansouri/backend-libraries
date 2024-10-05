package com.nm.carts.states;

import com.nm.carts.contract.CartEventDefault;
import com.nm.carts.contract.CartStateDefault;

/**
 * Check wether client information are required and setted
 * 
 * @author Nabil
 * 
 */
public class CartStateComputerForce extends CartStateComputerChain {

	@Override
	public CartStateContext onPrepare(CartStateContext context) throws Exception {
		context.getTransConf().withExternal().source(CartStateDefault.NonEmpty).target(CartStateDefault.Validating)
				.event(CartEventDefault.ValidatingEvent);
		return prepare(context);
	}

	@Override
	public CartStateContext onTransition(CartStateContext context) {
		if (context.getRequest().getEvent() != null) {
			context.getMachine().sendEvent(context.getRequest().getEvent());
		}
		return transition(context);
	}

}
