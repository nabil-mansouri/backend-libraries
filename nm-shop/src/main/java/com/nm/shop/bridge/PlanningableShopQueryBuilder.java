package com.nm.shop.bridge;

import org.hibernate.criterion.Restrictions;

import com.nm.plannings.dao.impl.PlanningableQueryBuilder;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PlanningableShopQueryBuilder extends PlanningableQueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanningableShopQueryBuilder() {
		super(ShopPlanningable.class);
	}

	public PlanningableQueryBuilder withShop(Long restaurant) {
		createAlias("shop", "shop");
		criteria.add(Restrictions.eq("shop.id", restaurant));
		return this;
	}
}
