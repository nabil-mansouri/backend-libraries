package com.nm.carts.contract;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.dtos.impl.CartDtoImpl;
import com.nm.carts.states.CartStateComputerChain;
import com.nm.carts.states.CartStateComputerForce;
import com.nm.carts.states.CartStateComputerOperation;
import com.nm.carts.states.CartStateComputerValidating;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartAdapterDefault implements CartAdapter {

	public List<CartStateComputerChain> stateChain() {
		List<CartStateComputerChain> chain = new ArrayList<CartStateComputerChain>();
		chain.add(new CartStateComputerForce());
		chain.add(new CartStateComputerOperation());
		chain.add(new CartStateComputerValidating());
		return chain;
	}

	public Class<? extends CartDto> dtoClass() {
		return CartDtoImpl.class;
	}

	public LinkedList<CartItemDto> toGroups(CartItemDto root) {
		return new LinkedList<CartItemDto>();
	}

}
