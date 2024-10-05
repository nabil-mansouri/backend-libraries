package com.rm.model.discounts.rules.subject;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule_subject_product")
public class DiscountRuleSubjectProduct extends DiscountRuleSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToMany()
	@JoinTable(name = "nm_discount_rule_subject_product_join")
	private Collection<ProductDefinition> products = new HashSet<ProductDefinition>();
	@Column(nullable = false, name = "select_all")
	private boolean all;

	public Collection<ProductDefinition> getProducts() {
		return products;
	}

	public void setProducts(Collection<ProductDefinition> products) {
		this.products = products;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	@AssertFalse
	protected boolean assertTrue() {
		return all && products.size() > 1;
	}
}
