package com.nm.carts.dtos;

import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface CartItemCompatibleDto extends IGraph {
	public CartItemDto toItem(CartItemDto item);
}
