package com.nm.tests.bridge;

import com.google.common.base.Strings;
import com.nm.carts.contract.CartEventDefault;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.states.CartStateComputerChain;
import com.nm.carts.states.CartStateContext;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartStateComputerShop extends CartStateComputerChain {

	public void preparse(CartStateContext context) {

	}

	@Override
	public CartStateContext onPrepare(CartStateContext context) throws Exception {
		context.getTransConf().withExternal().source(CartStateDefault.NotReady).target(CartStateDefault.Ready)
				.event(CartEventDefault.ReadyEvent);
		for (CartStateDefault d : CartStateDefault.values()) {
			if (!d.equals(CartStateDefault.NotReady)) {
				context.getTransConf().withExternal().source(d).target(CartStateDefault.NotReady)
						.event(CartEventDefault.NotReadyEvent);
			}
		}
		return prepare(context);
	}

	@Override
	public CartStateContext onTransition(CartStateContext context) {
		if (context.getCart() instanceof CartDtoExtImpl) {
			boolean ok = true;
			CartDtoExtImpl d = ((CartDtoExtImpl) context.getCart());
			d.getConstraints().remove(CartAdapterImpl.REQUIREDSHOP);
			d.getConstraints().remove(CartAdapterImpl.REQUIREDMODE);
			if (Strings.isNullOrEmpty(d.getShop())) {
				d.getConstraints().add(CartAdapterImpl.REQUIREDSHOP);
				ok = false;
			}
			if (Strings.isNullOrEmpty(d.getMode())) {
				d.getConstraints().add(CartAdapterImpl.REQUIREDMODE);
				ok = false;
			}
			//
			if (ok) {
				context.getMachine().sendEvent(CartEventDefault.ReadyEvent);
			} else {
				context.getMachine().sendEvent(CartEventDefault.NotReadyEvent);
			}
		}
		return transition(context);
	}

}
