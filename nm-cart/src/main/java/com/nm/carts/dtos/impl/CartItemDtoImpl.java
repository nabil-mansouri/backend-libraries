package com.nm.carts.dtos.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nm.app.utils.MathUtil;
import com.nm.carts.constants.CartRowType;
import com.nm.carts.dtos.CartItemDto;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.IGraphIdentifier;

/**
 * 
 * @author nabilmansouri
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class CartItemDtoImpl implements CartItemDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double subtotal = 0d;
	private int quantity;
	private boolean root;
	private CartRowType type;
	private CartValueDtoImpl value = new CartValueDtoImpl();
	private String uuid = UUIDUtils.UUID();
	private List<CartItemDto> children = new ArrayList<CartItemDto>();

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public CartValueDtoImpl getValue() {
		return value;
	}

	public void setValue(CartValueDtoImpl value) {
		this.value = value;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String uuid() {
		return getUuid();
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CartRowType getType() {
		return type;
	}

	public void setType(CartRowType type) {
		this.type = type;
	}

	public CartItemDto createChild(CartItemDto item) {
		this.children.add(item);
		return item;
	}

	public CartItemDto createChild(boolean reverse) {
		CartItemDtoImpl t = (CartItemDtoImpl) this.createChild();
		if (reverse) {
			this.children.add(0, t);
		} else {
			this.children.add(t);
		}
		return t;
	}

	public CartItemDto createChild() {
		return new CartItemDtoImpl();
	}

	public boolean root() {
		return isRoot();
	}

	public Collection<CartItemDto> getChildren() {
		return children;
	}

	public void setChildren(List<CartItemDto> children) {
		this.children = children;
	}

	public List<? extends IGraph> childrens() {
		return children;
	}

	public CartItemDto clone() {
		CartItemDtoImpl clone = SerializationUtils.clone(this);
		clone.setUuid(UUIDUtils.UUID());
		return clone;
	}

	public boolean filter(IGraphIdentifier id) {
		if (id instanceof CartRowIdentifier) {
			CartRowIdentifier ii = (CartRowIdentifier) id;
			if (ii.getType() != null && !ii.getType().equals(this.getType())) {
				return false;
			}
		}
		return true;
	}

	public boolean manage(IGraphIdentifier id) {
		if (id instanceof CartRowIdentifier) {
			return true;
		}
		return false;
	}

	public Double compute() {
		Double total = MathUtil.mult(this.getQuantity(), this.getValue().getValue());
		for (CartItemDto c : this.children) {
			total = MathUtil.sum(c.compute(), total);
		}
		setSubtotal(total);
		return total;
	}
}
