package com.nm.carts.operations;

import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.utils.graphs.GraphInfo;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartOperationChainHelper {

	public static void push(CartOperationChainContext context) {
		CartItemDto root = context.getItem();
		if (context.getExtra().containsKey(CartOperationChainContext.PARENT)) {
			root.setRoot(false);
			CartItemDto prev = (CartItemDto) context.getExtra().get(CartOperationChainContext.PARENT);
			prev.getChildren().add(root);
		} else {
			root.setRoot(true);
			context.getCart().getRows().add(root);
		}
	}

	public static void push(CartOperationChainContext context, GraphInfo row, CartItemDto item) {
		if (row.hasParent()) {
			if (row.getParent().isCurrent(CartDto.class)) {
				CartDto cart = row.getParent().getCurrent(CartDto.class);
				cart.getRows().add(item);
			} else {
				CartItemDto cart = row.getParent().getCurrent(CartItemDto.class);
				cart.getChildren().add(item);
			}
		} else {
			context.getCart().getRows().add(item);
		}
	}
}
