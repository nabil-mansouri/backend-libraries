package com.nm.products.dtos.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Objects;
import com.nm.products.constants.ProductNodeType;
import com.nm.utils.paths.PathFinderCriteria;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ProductAsListDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private List<ProductNodeDto> nodes = new ArrayList<ProductNodeDto>();

	public List<ProductNodeDto> getNodes() {
		return nodes;
	}

	public void setNodes(List<ProductNodeDto> nodes) {
		this.nodes = nodes;
	}

	public ProductNodeDto node(int i) {
		return this.nodes.get(i);
	}

	public static class PathFinderCriteriaNode implements PathFinderCriteria<ProductNodeDto> {
		private Long idProduct;
		private Long idPart;
		private ProductNodeType type;

		public PathFinderCriteriaNode() {
		}

		public PathFinderCriteriaNode setProduct(Long idProduct) {
			this.idProduct = idProduct;
			this.idPart = null;
			this.type = ProductNodeType.PRODUCT;
			return this;
		}

		public PathFinderCriteriaNode setProductPart(Long idProduct, Long idPart) {
			this.idProduct = idProduct;
			this.idPart = idPart;
			this.type = ProductNodeType.PRODUCT_PART;
			return this;
		}

		public PathFinderCriteriaNode setPart(Long idPart) {
			this.idPart = idPart;
			this.idProduct = null;
			this.type = ProductNodeType.PART;
			return this;
		}

		public boolean test(ProductNodeDto t) {
			switch (type) {
			case INGREDIENT:
				break;
			case PART:
				return t.getPart() != null && Objects.equal(idPart, t.getPart().getId());
			case PRODUCT:
				return t.getProduct() != null && Objects.equal(idProduct, t.getProduct().getId());
			case PRODUCT_PART:
				return t.getProduct() != null && Objects.equal(idProduct, t.getProduct().getId()) && t.getPart() != null
						&& Objects.equal(idPart, t.getPart().getId());
			}
			return false;
		}
	}

}
