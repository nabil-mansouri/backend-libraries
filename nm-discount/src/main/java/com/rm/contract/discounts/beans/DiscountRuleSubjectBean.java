package com.rm.contract.discounts.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.prices.dtos.forms.old.TaxDefFormBean;
import com.nm.products.dtos.forms.ProductFormDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountRuleSubjectBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private boolean concernedOrder;
	private boolean concernedProduct;
	private boolean concernedAdditionnal;
	private boolean concernedTaxs;
	//
	private boolean allTax;
	private Collection<TaxDefFormBean> taxs = new HashSet<TaxDefFormBean>();
	//
	private boolean allProducts;
	private Collection<ProductFormDto> products = new HashSet<ProductFormDto>();
	//
	private Collection<ProductFormDto> specials = new HashSet<ProductFormDto>();
	private Collection<ProductFormDto> gifts = new HashSet<ProductFormDto>();

	//
	public boolean isConcernedOrder() {
		return concernedOrder;
	}

	public DiscountRuleSubjectBean setConcernedOrder(boolean concernedOrder) {
		this.concernedOrder = concernedOrder;
		return this;
	}

	public boolean isConcernedProduct() {
		return concernedProduct;
	}

	public DiscountRuleSubjectBean setConcernedProduct(boolean concernedProduct) {
		this.concernedProduct = concernedProduct;
		return this;
	}

	public boolean isConcernedAdditionnal() {
		return concernedAdditionnal;
	}

	public DiscountRuleSubjectBean setConcernedAdditionnal(boolean concernedAdditionnal) {
		this.concernedAdditionnal = concernedAdditionnal;
		return this;
	}

	public boolean isConcernedTaxs() {
		return concernedTaxs;
	}

	public DiscountRuleSubjectBean setConcernedTaxs(boolean concernedTaxs) {
		this.concernedTaxs = concernedTaxs;
		return this;
	}

	public boolean isAllTax() {
		return allTax;
	}

	public DiscountRuleSubjectBean setAllTax(boolean allTax) {
		this.allTax = allTax;
		return this;
	}

	public Collection<TaxDefFormBean> getTaxs() {
		return taxs;
	}

	public DiscountRuleSubjectBean setTaxs(Collection<TaxDefFormBean> taxs) {
		this.taxs = taxs;
		return this;
	}

	public boolean isAllProducts() {
		return allProducts;
	}

	public DiscountRuleSubjectBean setAllProducts(boolean allProducts) {
		this.allProducts = allProducts;
		return this;
	}

	public Collection<ProductFormDto> getProducts() {
		return products;
	}

	public DiscountRuleSubjectBean setProducts(Collection<ProductFormDto> products) {
		this.products = products;
		return this;
	}

	public Collection<ProductFormDto> getSpecials() {
		return specials;
	}

	public DiscountRuleSubjectBean setSpecials(Collection<ProductFormDto> specials) {
		this.specials = specials;
		return this;
	}

	public Collection<ProductFormDto> getGifts() {
		return gifts;
	}

	public DiscountRuleSubjectBean setGifts(Collection<ProductFormDto> gifts) {
		this.gifts = gifts;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
