package com.nm.carts.states;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;

import com.google.common.base.Objects;
import com.nm.carts.contract.CartAdapter;
import com.nm.carts.contract.CartEvent;
import com.nm.carts.contract.CartState;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.exceptions.CartManagementException;

/**
 * 
 * @author Nabil
 * 
 */
public abstract class CartStateComputerChain {
	Log log = LogFactory.getLog(getClass());
	private CartStateComputerChain next;

	public void setNext(CartStateComputerChain next) {
		this.next = next;
	}

	public boolean hasNext() {
		return next != null;
	}

	public abstract CartStateContext onPrepare(CartStateContext context) throws Exception;

	public abstract CartStateContext onTransition(CartStateContext context);

	public CartStateContext prepare(CartStateContext context) throws Exception {
		if (hasNext()) {
			return next.onPrepare(context);
		} else {
			return context;
		}
	}

	public CartStateContext transition(CartStateContext context) {
		if (hasNext()) {
			return next.onTransition(context);
		} else {
			return context;
		}
	}

	public static void process(CartStateContext context) throws CartManagementException {
		CartAdapter adapter = context.getAdapter();
		List<CartStateComputerChain> all = adapter.stateChain();
		for (int i = 0; i < (all.size() - 1); i++) {
			all.get(i).setNext(all.get(i + 1));
		}
		all.get(all.size() - 1).setNext(null);
		//
		try {
			boolean hasChanged = false;
			do {
				Builder<CartState, CartEvent> builder = StateMachineBuilder.builder();
				builder.configureConfiguration().withConfiguration().autoStartup(true)
						.taskExecutor(new SyncTaskExecutor());
				//
				CartState initial = CartStateDefault.NotReady;
				if (context.getCart().getState() != null) {
					initial = context.getCart().getState();
				}
				context.setStateConf(builder.configureStates().withStates().initial(initial)
						.end(CartStateDefault.Complete).states(CartStateDefault.all()));
				context.setTransConf(builder.configureTransitions());
				//
				all.get(0).onPrepare(context);
				//
				context.setMachine(builder.build());
				//
				all.get(0).onTransition(context);
				//
				if (context.getMachine().getState() != null) {
					context.getCart().setState(context.getMachine().getState().getId());
				}
				//
				hasChanged = (!Objects.equal(initial, context.getCart().getState()));
			} while (hasChanged);
		} catch (CartManagementException e) {
			throw e;
		} catch (Exception e) {
			throw new CartManagementException(e);
		}
	}
}
