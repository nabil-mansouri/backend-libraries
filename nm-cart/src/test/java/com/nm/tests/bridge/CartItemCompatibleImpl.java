package com.nm.tests.bridge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nm.carts.dtos.CartItemCompatibleDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.graphs.IGraph;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartItemCompatibleImpl implements CartItemCompatibleDto {
	private Long product;
	private Double price;
	private Long group;
	private boolean root;
	private Collection<CartItemCompatibleImpl> child = new ArrayList<CartItemCompatibleImpl>();
	private String uuid = UUIDUtils.UUID();

	public CartItemCompatibleImpl() {
	}

	public CartItemCompatibleImpl(Long product, Double price) {
		super();
		this.product = product;
		this.price = price;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	public List<? extends IGraph> childrens() {
		return (List<? extends IGraph>) child;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public boolean root() {
		return root;
	}

	public String uuid() {
		return uuid;
	}

	public CartItemDto toItem(CartItemDto item) {
		CartItemDtoExtImpl it = (CartItemDtoExtImpl) item;
		it.getValue().setValue(price);
		it.setProduct(product);
		it.setGroup(this.group);
		it.setQuantity(1);
		return it;
	}

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Collection<CartItemCompatibleImpl> getChild() {
		return child;
	}

	public void setChild(Collection<CartItemCompatibleImpl> child) {
		this.child = child;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public CartItemCompatibleImpl createChild() {
		CartItemCompatibleImpl child = new CartItemCompatibleImpl();
		this.child.add(child);
		return child;
	}
}
