package com.nm.products.dtos.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.IGraphIdentifiable;
import com.nm.utils.graphs.finder.IGraphIdentifier;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ProductAsTreeDto implements Serializable, IGraph, IGraphIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private ProductNodeDto node = new ProductNodeDto();
	private List<ProductAsTreeDto> children = new ArrayList<ProductAsTreeDto>();
	private boolean root;
	private String uuid = UUIDUtils.UUID();

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String uuid() {
		return getUuid();
	}

	public boolean root() {
		return isRoot();
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public ProductAsTreeDto createChild(boolean reverse) {
		ProductAsTreeDto t = new ProductAsTreeDto();
		if (reverse) {
			this.children.add(0, t);
		} else {
			this.children.add(t);
		}
		return t;
	}

	public java.util.List<? extends IGraph> childrens() {
		return children;
	};

	public ProductNodeDto getNode() {
		return node;
	}

	public void setNode(ProductNodeDto node) {
		this.node = node;
	}

	public List<ProductAsTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<ProductAsTreeDto> children) {
		this.children = children;
	}

	public boolean filter(IGraphIdentifier id) {
		if (id instanceof ProductAsTreeIdentifier) {
			ProductAsTreeIdentifier ii = (ProductAsTreeIdentifier) id;
			if (!ii.getTypes().isEmpty() && !ii.getTypes().contains(this.node.getType())) {
				return false;
			}
			//
			if (ii.getProduct() != null && !ii.getProduct().equals(this.node.getProduct())) {
				return false;
			}
		}
		return true;
	}

	public boolean manage(IGraphIdentifier id) {
		if (id instanceof ProductAsTreeIdentifier) {
			return true;
		}
		return false;
	}

}
