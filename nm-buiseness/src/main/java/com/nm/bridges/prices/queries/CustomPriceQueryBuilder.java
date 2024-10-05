package com.nm.bridges.prices.queries;

import com.nm.bridges.prices.constants.PriceFilterEnumExtra;
import com.nm.bridges.prices.dtos.CustomPriceFilterDto;
import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.model.Price;
import com.nm.products.dao.impl.ProductDefinitionQueryBuilder;
import com.nm.products.dtos.filters.ProductFilterDto;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CustomPriceQueryBuilder extends PriceQueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPriceQueryBuilder() {
		super(Price.class);
	}

	public static CustomPriceQueryBuilder get() {
		return new CustomPriceQueryBuilder();
	}

	public CustomPriceQueryBuilder withFilter(PriceFilterDto r) {
		if (r instanceof CustomPriceFilterDto) {
			CustomPriceFilterDto request = (CustomPriceFilterDto) r;
			if (request.isOnlyCurrent()) {
				withOnlyCurrent();
			}
			if (request.getIdRestaurant() != null) {
				this.withFilterOrNotExists(
						CustomPriceFilterQueryBuilder.getRestaurant().withRestaurant(request.getIdRestaurant()),
						PriceFilterEnumExtra.LimitRestaurants);
			}
			if (request.getOrderType() != null) {
				this.withFilterOrNotExists(
						CustomPriceFilterQueryBuilder.getOrder().withOrderType(request.getOrderType()),
						PriceFilterEnumExtra.LimitOrderType);
			}
			//
			if (request.hasProductFilter()) {
				ProductFilterDto filter = request.toProductFilter();
				ProductDefinitionQueryBuilder query = ProductDefinitionQueryBuilder.get().withFilter(filter);
				this.withSubject(CustomPriceSubjectQueryBuilder.getProduct().withProducts(query));
			}
		}
		super.withFilter(r);
		return this;
	}
}
