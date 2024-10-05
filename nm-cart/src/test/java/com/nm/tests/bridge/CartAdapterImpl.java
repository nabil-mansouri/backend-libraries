package com.nm.tests.bridge;

import java.util.LinkedList;
import java.util.List;

import com.nm.carts.contract.CartAdapterDefault;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.states.CartStateComputerChain;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartAdapterImpl extends CartAdapterDefault {
	public static final String REQUIREDSHOP = "REQUIREDSHOP";
	public static final String REQUIREDMODE = "REQUIREDMODE";

	@Override
	public List<CartStateComputerChain> stateChain() {
		List<CartStateComputerChain> all = super.stateChain();
		all.add(new CartStateComputerShop());
		return all;
	}

	public LinkedList<CartItemDto> toGroups(CartItemDto root) {
		CartItemDtoExtImpl ext = (CartItemDtoExtImpl) root;
		LinkedList<CartItemDto> list = new LinkedList<CartItemDto>();
		list.add(new CartItemDtoExtImpl().setGroup(ext.getGroup()));
		list.add(new CartItemDtoExtImpl().setProduct(ext.getProduct()));
		return list;
	}

	public Class<? extends CartDto> dtoClass() {
		return CartDtoExtImpl.class;
	}

}
