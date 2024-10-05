package com.nm.tests.bridge;

import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.dtos.impl.CartDtoImpl;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartDtoExtImpl extends CartDtoImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String shop;
	private String mode;

	public String getShop() {
		return shop;
	}

	public String getMode() {
		return mode;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public CartItemDto createItem(boolean root) {
		CartItemDtoExtImpl t = new CartItemDtoExtImpl();
		t.setRoot(root);
		return t;
	}

}
