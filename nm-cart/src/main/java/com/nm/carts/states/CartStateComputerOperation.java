package com.nm.carts.states;

import com.nm.carts.contract.CartEventDefault;
import com.nm.carts.contract.CartStateDefault;

/**
 * Check wether client information are required and setted
 * 
 * @author Nabil
 * 
 */
public class CartStateComputerOperation extends CartStateComputerChain {

	@Override
	public CartStateContext onPrepare(CartStateContext context) throws Exception {
		context.getTransConf().withExternal().source(CartStateDefault.Validating).target(CartStateDefault.NonEmpty)
				.event(CartEventDefault.UnlockEvent);
		context.getTransConf().withExternal().source(CartStateDefault.Validated).target(CartStateDefault.NonEmpty)
				.event(CartEventDefault.UnlockEvent);
		context.getTransConf().withExternal().source(CartStateDefault.Ready).target(CartStateDefault.NonEmpty)
				.event(CartEventDefault.OperationEvent);
		context.getTransConf().withExternal().source(CartStateDefault.NonEmpty).target(CartStateDefault.Ready)
				.event(CartEventDefault.EmptyEvent);
		return prepare(context);
	}

	@Override
	public CartStateContext onTransition(CartStateContext context) {
		if (context.getCart().getRows().isEmpty()) {
			context.getMachine().sendEvent(CartEventDefault.EmptyEvent);
		} else {
			context.getMachine().sendEvent(CartEventDefault.OperationEvent);
		}
		//
		if (context.getRequest().getOperation() != null) {
			switch (context.getRequest().getOperation()) {
			case Validate:
				break;
			default:
				context.getMachine().sendEvent(CartEventDefault.UnlockEvent);
				break;
			}
		}
		return transition(context);
	}

}
