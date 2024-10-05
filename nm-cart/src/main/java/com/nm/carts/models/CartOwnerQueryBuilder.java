package com.nm.carts.models;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CartOwnerQueryBuilder extends AbstractQueryBuilder<CartOwnerQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DetachedCriteria criteria;

	public static CartOwnerQueryBuilder getSession() {
		return new CartOwnerQueryBuilder(CartOwnerSession.class);
	}

	protected CartOwnerQueryBuilder(Class<? extends CartOwner> clazz) {
		this(clazz, "CartOwnerSession");
	}

	protected CartOwnerQueryBuilder(Class<? extends CartOwner> clazz, String alias) {
		criteria = DetachedCriteria.forClass(clazz, alias);
		withMainAlias(alias);
	}

	public CartOwnerQueryBuilder withSession(String session) {
		this.criteria.add(Restrictions.eq("session", session));
		return this;
	}

	public String getIdName() {
		return wrapMain("id");
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
