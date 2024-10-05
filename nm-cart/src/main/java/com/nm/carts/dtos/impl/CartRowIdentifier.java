package com.nm.carts.dtos.impl;


import com.nm.carts.constants.CartRowType;
import com.nm.carts.dtos.CartItemDto;
import com.nm.utils.graphs.finder.IGraphIdentifier;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartRowIdentifier implements IGraphIdentifier {
	private CartRowType type;
	private CartItemDto item;

	public CartRowIdentifier(CartRowType type, CartItemDto item) {
		super();
		this.type = type;
		this.item = item;
	}

	public CartItemDto getItem() {
		return item;
	}

	public void setItem(CartItemDto item) {
		this.item = item;
	}

	public CartRowType getType() {
		return type;
	}

	public void setType(CartRowType type) {
		this.type = type;
	}
}
