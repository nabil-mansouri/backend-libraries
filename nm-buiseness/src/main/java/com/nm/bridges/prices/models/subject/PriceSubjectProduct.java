package com.nm.bridges.prices.models.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nm.prices.model.subject.PriceSubject;
import com.nm.products.model.ProductDefinition;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_price_subject_product")
public class PriceSubjectProduct extends PriceSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected ProductDefinition product;

	@Column(nullable = false, insertable = false, updatable = false, name = "product_id")
	private Long productId;

	public PriceSubjectProduct() {
		setRoot(true);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProductDefinition getProduct() {
		return product;
	}

	public PriceSubjectProduct setProduct(ProductDefinition product) {
		this.product = product;
		return this;
	}

}
