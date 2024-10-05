package com.nm.carts.operations;

import java.util.HashMap;
import java.util.Map;

import com.nm.carts.constants.CartOperation;
import com.nm.carts.contract.CartAdapter;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemCompatibleDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.dtos.impl.CartRequestDtoImpl;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartOperationChainContext {
	public static final String PARENT = "CartOperationChainSorted.PARENT";
	private CartRequestDtoImpl request = new CartRequestDtoImpl();
	private CartAdapter adapter;
	private CartDto cart;
	private CartItemDto item;
	private CartItemCompatibleDto toPush;
	private Map<String, Object> extra = new HashMap<String, Object>();

	public CartItemCompatibleDto getToPush() {
		return toPush;
	}

	public CartOperationChainContext setToPush(CartItemCompatibleDto toPush) {
		this.toPush = toPush;
		return this;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public Object getExtra(String key) {
		return extra.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getExtra(String key, Class<T> claz) {
		return (T) extra.get(key);
	}

	public CartOperationChainContext setExtra(Map<String, Object> extra) {
		this.extra = extra;
		return this;
	}

	public CartItemDto getItem() {
		return item;
	}

	public CartOperationChainContext setItem(CartItemDto item) {
		this.item = item;
		return this;
	}

	public CartDto getCart() {
		return cart;
	}

	public CartOperationChainContext setCart(CartDto cart) {
		this.cart = cart;
		return this;
	}

	public CartAdapter getAdapter() {
		return adapter;
	}

	public CartOperationChainContext setAdapter(CartAdapter adapter) {
		this.adapter = adapter;
		return this;
	}

	public CartRequestDtoImpl getRequest() {
		return request;
	}

	public CartOperationChainContext setRequest(CartRequestDtoImpl request) {
		this.request = request;
		return this;
	}

	public CartOperationChainContext setOperation(CartOperation operation) {
		request.setOperation(operation);
		return this;
	}
}
