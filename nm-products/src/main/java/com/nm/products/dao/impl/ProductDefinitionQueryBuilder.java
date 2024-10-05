package com.nm.products.dao.impl;

import java.util.Arrays;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.products.constants.ProductType;
import com.nm.products.dtos.filters.ProductFilterDto;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductDefinitionQueryBuilder extends AbstractQueryBuilder<ProductDefinitionQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProductDefinitionQueryBuilder() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(ProductDefinition.class);

	public static ProductDefinitionQueryBuilder get() {
		return new ProductDefinitionQueryBuilder();
	}

	public ProductDefinitionQueryBuilder withType(ProductType type) {
		this.criteria.add(Restrictions.eq("type", type));
		return this;
	}

	public ProductDefinitionQueryBuilder withIds(Collection<Long> ids) {
		this.criteria.add(Restrictions.in("id", ids));
		return this;
	}

	public ProductDefinitionQueryBuilder withFilter(ProductFilterDto request) {
		if (request.getType() != null) {
			this.withType(request.getType());
		}
		if (request.getIdCategory() != null) {
			this.withCategory(request.getIdCategory());
		}
		if (request.getId() != null) {
			this.withIds(Arrays.asList(request.getId()));
		}
		if (request.getIds() != null && !request.getIds().isEmpty()) {
			withIds(request.getIds());
		}
		withRange(request);
		return this;
	}

	public ProductDefinitionQueryBuilder withCategory(Long idCat) {
		this.criteria.createAlias("categories", "categories");
		this.criteria.add(Restrictions.eq("categories.id", idCat));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	@Override
	public ProductDefinitionQueryBuilder withId(Long id) {
		super.withId(id);
		return this;
	}

	public ProductDefinitionQueryBuilder withNotId(Long id) {
		super.withNotId(id);
		return this;
	}
}
