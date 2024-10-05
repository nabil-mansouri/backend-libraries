package com.nm.carts.dtos;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nm.carts.contract.CartItemOptions;
import com.nm.carts.contract.CartState;
import com.nm.utils.dtos.Dto;
import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author nabilmansouri
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface CartDto extends Dto, IGraph {
	public Long getId();

	public CartItemDto createItem(boolean root);

	public CartItemDto createChild(boolean reverse);

	public Collection<CartItemDto> getRows();

	public Collection<CartItemOptions> getOptions();

	public CartState getState();

	public void setState(CartState s);

	public Collection<CartState> states();

	public CartItemDto createChild(CartItemDto item);

	public void compute();
}
