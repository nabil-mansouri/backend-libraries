package com.nm.shop.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.shop.dtos.ShopFilterDto;
import com.nm.shop.model.Shop;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class ShopQueryBuilder extends AbstractQueryBuilder<ShopQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);

	public static ShopQueryBuilder get() {
		return new ShopQueryBuilder();
	}

	public ShopQueryBuilder withId(Long idProductDef) {
		criteria.add(Restrictions.idEq(idProductDef));
		return this;
	}

	public ShopQueryBuilder withFilter(ShopFilterDto filter) {
		if (filter.getId() != null) {
			this.withId(filter.getId());
		}
		withRange(filter);
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
