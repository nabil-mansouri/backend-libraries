package com.nm.carts.dtos.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.nm.carts.contract.CartItemOptions;
import com.nm.carts.contract.CartState;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author Nabil
 * 
 */
public class CartDtoImpl implements CartDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
	private String uuid = UUIDUtils.UUID();
	private Double subtotal = 0d;
	private List<CartItemDto> rows = new ArrayList<CartItemDto>();
	private Collection<CartItemOptions> options = new HashSet<CartItemOptions>();
	private CartState state = CartStateDefault.NotReady;
	private Collection<String> constraints = new HashSet<String>();
	private CartOwnerDtoImpl owner = new CartOwnerDtoImpl();

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Collection<CartState> states() {
		return Arrays.asList(getState());
	}

	public CartState getState() {
		return state;
	}

	public void setState(CartState state) {
		this.state = state;
	}

	public Collection<String> getConstraints() {
		return constraints;
	}

	public void setConstraints(Collection<String> constraints) {
		this.constraints = constraints;
	}

	public CartOwnerDtoImpl getOwner() {
		return owner;
	}

	public void setOwner(CartOwnerDtoImpl owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<CartItemOptions> getOptions() {
		return options;
	}

	public void setOptions(Collection<CartItemOptions> options) {
		this.options = options;
	}

	public CartItemDto createItem(boolean root) {
		CartItemDtoImpl t = new CartItemDtoImpl();
		t.setRoot(root);
		return t;
	}

	public CartItemDto createChild(CartItemDto item) {
		this.rows.add(item);
		return item;
	}

	public CartItemDto createChild(boolean reverse) {
		CartItemDto t = this.createItem(false);
		if (reverse) {
			this.rows.add(0, t);
		} else {
			this.rows.add(t);
		}
		return t;
	}

	public List<? extends IGraph> childrens() {
		return getRows();
	}

	public boolean root() {
		return true;
	}

	public String uuid() {
		return uuid;
	}

	public List<CartItemDto> getRows() {
		return rows;
	}

	public void setRows(List<CartItemDto> rows) {
		this.rows = rows;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void compute() {
		Double total = 0d;
		for (CartItemDto item : this.getRows()) {
			total += item.compute();
		}
		setSubtotal(total);
	}
}
