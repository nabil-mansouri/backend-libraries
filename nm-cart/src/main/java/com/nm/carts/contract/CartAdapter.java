package com.nm.carts.contract;

import java.util.LinkedList;
import java.util.List;

import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.states.CartStateComputerChain;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface CartAdapter {
	public List<CartStateComputerChain> stateChain();

	public Class<? extends CartDto> dtoClass();

	public LinkedList<CartItemDto> toGroups(CartItemDto root);
}
