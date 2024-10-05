package com.nm.carts.states;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

import com.nm.carts.contract.CartAdapter;
import com.nm.carts.contract.CartEvent;
import com.nm.carts.contract.CartState;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.impl.CartRequestDtoImpl;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartStateContext {
	private CartAdapter cartAdapter;
	private StateConfigurer<CartState, CartEvent> stateConf;
	private StateMachineTransitionConfigurer<CartState, CartEvent> transConf;
	private StateMachine<CartState, CartEvent> machine;
	private CartDto cart;
	private CartRequestDtoImpl request = new CartRequestDtoImpl();

	public StateMachine<CartState, CartEvent> getMachine() {
		return machine;
	}

	public CartStateContext setMachine(StateMachine<CartState, CartEvent> machine) {
		this.machine = machine;
		return this;
	}

	public StateMachineTransitionConfigurer<CartState, CartEvent> getTransConf() {
		return transConf;
	}

	public CartStateContext setTransConf(StateMachineTransitionConfigurer<CartState, CartEvent> transConf) {
		this.transConf = transConf;
		return this;
	}

	public CartDto getCart() {
		return cart;
	}

	public CartStateContext setCart(CartDto cart) {
		this.cart = cart;
		return this;
	}

	public CartAdapter getAdapter() {
		return cartAdapter;
	}

	public CartAdapter getCartAdapter() {
		return cartAdapter;
	}

	public CartStateContext setCartAdapter(CartAdapter cartAdapter) {
		this.cartAdapter = cartAdapter;
		return this;
	}

	public StateConfigurer<CartState, CartEvent> getStateConf() {
		return stateConf;
	}

	public CartStateContext setStateConf(StateConfigurer<CartState, CartEvent> stateConf) {
		this.stateConf = stateConf;
		return this;
	}

	public CartRequestDtoImpl getRequest() {
		return request;
	}

	public CartStateContext setRequest(CartRequestDtoImpl request) {
		this.request = request;
		return this;
	}

}
