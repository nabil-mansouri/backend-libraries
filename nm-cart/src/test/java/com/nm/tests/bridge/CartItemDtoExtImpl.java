package com.nm.tests.bridge;

import com.google.common.base.Objects;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.dtos.impl.CartItemDtoImpl;
import com.nm.carts.dtos.impl.CartRowIdentifier;
import com.nm.utils.graphs.finder.IGraphIdentifier;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartItemDtoExtImpl extends CartItemDtoImpl {

	private static final long serialVersionUID = 1L;
	private Long product;
	private Long group;

	public Long getGroup() {
		return group;
	}

	public CartItemDtoExtImpl setGroup(Long group) {
		this.group = group;
		return this;
	}

	public Long getProduct() {
		return product;
	}

	public CartItemDtoExtImpl setProduct(Long product) {
		this.product = product;
		return this;
	}

	@Override
	public CartItemDto createChild() {
		return new CartItemDtoExtImpl();
	}

	@Override
	public boolean filter(IGraphIdentifier id) {
		if (id instanceof CartRowIdentifier) {
			CartRowIdentifier ii = (CartRowIdentifier) id;
			CartItemDto item = ii.getItem();
			if (item instanceof CartItemDtoExtImpl) {
				CartItemDtoExtImpl itemExt = (CartItemDtoExtImpl) item;
				if (itemExt.getProduct() != null && !Objects.equal(itemExt.getProduct(), this.getProduct())) {
					return false;
				}
				if (itemExt.getGroup() != null && !Objects.equal(itemExt.getGroup(), this.getGroup())) {
					return false;
				}
			}
		}
		return super.filter(id);
	}

}
