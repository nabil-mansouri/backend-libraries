package com.nm.bridges.prices.queries;

import org.hibernate.criterion.Restrictions;

import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.models.filters.PriceValueFilterOrderType;
import com.nm.bridges.prices.models.filters.PriceValueFilterRestaurant;
import com.nm.prices.dao.impl.PriceValueFilterQueryBuilder;
import com.nm.prices.model.filter.PriceValueFilter;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CustomPriceValueFilterQueryBuilder extends PriceValueFilterQueryBuilder {

	protected CustomPriceValueFilterQueryBuilder(Class<? extends PriceValueFilter> clazz) {
		super(clazz);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static CustomPriceValueFilterQueryBuilder getOrderType() {
		return new CustomPriceValueFilterQueryBuilder(PriceValueFilterOrderType.class);
	}

	public static CustomPriceValueFilterQueryBuilder getRestaurant() {
		return new CustomPriceValueFilterQueryBuilder(PriceValueFilterRestaurant.class);
	}

	public PriceValueFilterQueryBuilder withOrderType(OrderType ids) {
		criteria.add(Restrictions.eq("orderType", ids));
		return this;
	}

	public PriceValueFilterQueryBuilder withRestaurant(Long ids) {
		createAlias("restaurant", "restaurant");
		criteria.add(Restrictions.eq("restaurant.id", ids));
		return this;
	}

	public PriceValueFilterQueryBuilder withNotRestaurant(Long ids) {
		createAlias("restaurant", "restaurant");
		criteria.add(Restrictions.ne("restaurant.id", ids));
		return this;
	}
}
