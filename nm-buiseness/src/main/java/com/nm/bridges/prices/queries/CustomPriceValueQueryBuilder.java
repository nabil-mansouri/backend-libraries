package com.nm.bridges.prices.queries;

import com.nm.bridges.prices.constants.PriceFilterValueEnumExtra;
import com.nm.bridges.prices.dtos.CustomPriceFilterDto;
import com.nm.prices.dao.impl.PriceValueQueryBuilder;
import com.nm.prices.dtos.filters.PriceFilterDto;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CustomPriceValueQueryBuilder extends PriceValueQueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPriceValueQueryBuilder() {
		super();
	}

	public static CustomPriceValueQueryBuilder get() {
		return new CustomPriceValueQueryBuilder();
	}

	public PriceValueQueryBuilder withFilter(PriceFilterDto r) {
		if (r instanceof CustomPriceFilterDto) {
			CustomPriceFilterDto request = (CustomPriceFilterDto) r;
			if (request.getIdRestaurant() != null) {
				this.withFilterOrNotExists(
						CustomPriceValueFilterQueryBuilder.getRestaurant().withRestaurant(request.getIdRestaurant()),
						PriceFilterValueEnumExtra.LimitRestaurant);
			}
			if (request.getOrderType() != null) {
				this.withFilterOrNotExists(
						CustomPriceValueFilterQueryBuilder.getOrderType().withOrderType(request.getOrderType()),
						PriceFilterValueEnumExtra.LimitOrderType);
			}
		}
		super.withFilter(r);
		return this;
	}

}
