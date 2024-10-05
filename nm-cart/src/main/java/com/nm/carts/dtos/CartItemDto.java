package com.nm.carts.dtos;

import java.util.Collection;

import com.nm.carts.constants.CartRowType;
import com.nm.utils.dtos.Dto;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.IGraphIdentifiable;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface CartItemDto extends Dto, IGraphIdentifiable, IGraph {
	public void setType(CartRowType type);

	public CartItemDto createChild(boolean reverse);

	public CartItemDto createChild();

	public CartItemDto clone();

	public void setRoot(boolean b);

	public int getQuantity();

	public void setQuantity(int quantity);

	public Collection<CartItemDto> getChildren();

	public CartItemDto createChild(CartItemDto item);

	public Double compute();

}
