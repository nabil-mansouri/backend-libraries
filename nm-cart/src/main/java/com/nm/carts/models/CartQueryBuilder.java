package com.nm.carts.models;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CartQueryBuilder extends AbstractQueryBuilder<CartQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DetachedCriteria criteria;

	public static CartQueryBuilder get() {
		return new CartQueryBuilder(Cart.class);
	}

	protected CartQueryBuilder(Class<? extends Cart> clazz) {
		this(clazz, "Cart");
	}

	protected CartQueryBuilder(Class<? extends Cart> clazz, String alias) {
		criteria = DetachedCriteria.forClass(clazz, alias);
		withMainAlias(alias);
	}

	public CartQueryBuilder withOwner(CartOwner owner) {
		this.criteria.add(Restrictions.eq("owner", owner));
		return this;
	}

	public String getIdName() {
		return wrapMain("id");
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
