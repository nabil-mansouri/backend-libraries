package com.nm.bridges.prices.queries;

import java.util.List;

import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.models.filters.PriceFilterOrderType;
import com.nm.bridges.prices.models.filters.PriceFilterRestaurant;
import com.nm.prices.dao.impl.PriceFilterQueryBuilder;
import com.nm.prices.model.filter.PriceFilter;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CustomPriceFilterQueryBuilder extends PriceFilterQueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPriceFilterQueryBuilder(Class<? extends PriceFilter> clazz) {
		super(clazz);
	}

	public static CustomPriceFilterQueryBuilder getRestaurant() {
		return new CustomPriceFilterQueryBuilder(PriceFilterRestaurant.class);
	}

	public static CustomPriceFilterQueryBuilder getOrder() {
		return new CustomPriceFilterQueryBuilder(PriceFilterOrderType.class);
	}

	public PriceFilterQueryBuilder withRestaurants(List<Long> ids) {
		if (!ids.isEmpty()) {
			createAlias(wrapMain("list"), "list");
			createAlias("list.shop", "shop");
			Disjunction or = Restrictions.or();
			for (Long i : ids) {
				or.add(Restrictions.eq("shop.id", i));
			}
		}
		return this;
	}

	public PriceFilterQueryBuilder withOrderType(OrderType t) {
		createAlias("orderType", "orderType");
		criteria.add(Restrictions.eq("orderType.elements", t));
		return this;
	}

	public PriceFilterQueryBuilder withRestaurant(Long ids) {
		createAlias(wrapMain("list"), "list");
		createAlias("list.shop", "shop");
		criteria.add(Restrictions.eq("shop.id", ids));
		return this;
	}

	public PriceFilterQueryBuilder withNotRestaurant(Long ids) {
		createAlias(wrapMain("list"), "list");
		createAlias("list.shop", "shop");
		criteria.add(Restrictions.not(Restrictions.eq("shop.id", ids)));
		return this;
	}

}
