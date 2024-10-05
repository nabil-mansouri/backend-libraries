package com.nm.bridges.prices.models.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nm.prices.model.subject.PriceSubject;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_price_subject_product_part")
public class PriceSubjectProductPart extends PriceSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected ProductDefinition product;
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected ProductDefinitionPart part;
	@Column(nullable = false, insertable = false, updatable = false, name = "part_id")
	private Long partId;
	@Column(nullable = false, insertable = false, updatable = false, name = "product_id")
	private Long productId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public ProductDefinition getProduct() {
		return product;
	}

	public void setProduct(ProductDefinition product) {
		this.product = product;
	}

	public ProductDefinitionPart getPart() {
		return part;
	}

	public void setPart(ProductDefinitionPart part) {
		this.part = part;
	}
}
