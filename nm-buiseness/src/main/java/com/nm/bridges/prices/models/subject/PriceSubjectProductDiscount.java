package com.nm.bridges.prices.models.subject;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nm.prices.model.subject.PriceSubject;
import com.nm.products.model.ProductDefinition;

@Entity
@Table(name = "nm_price_subject_product_discount")
public class PriceSubjectProductDiscount extends PriceSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(optional = false)
	protected ProductDefinition product;

	public ProductDefinition getProduct() {
		return product;
	}

	public void setProduct(ProductDefinition product) {
		this.product = product;
	}

}
