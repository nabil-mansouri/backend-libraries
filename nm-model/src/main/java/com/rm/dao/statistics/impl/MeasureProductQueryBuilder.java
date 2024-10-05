package com.rm.dao.statistics.impl;

import java.util.Collection;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.nm.app.stats.AbstractMeasureQueryBuilder;
import com.nm.app.stats.DimensionType;
import com.nm.app.stats.MeasureType;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.model.statistics.MeasureProduct;

/**
 * 
 * @author Nabil
 * 
 */
public class MeasureProductQueryBuilder extends AbstractMeasureQueryBuilder {

	protected MeasureProductQueryBuilder(Class<?> clazz) {
		super(clazz);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static MeasureProductQueryBuilder get() {
		return new MeasureProductQueryBuilder(MeasureProduct.class);
	}

	public MeasureProductQueryBuilder withCountProductInorder() {
		this.projections.add(Projections.alias(Projections.count("idProduct"), MeasureType.CountProduct.getSqlName()));
		return this;
	}

	public MeasureProductQueryBuilder withByProduct() {
		this.projections.add(Projections.groupProperty("idProduct"), DimensionType.Product.getSqlName());
		return this;
	}

	public MeasureProductQueryBuilder withFilter(StatisticsFilterBean filter) {
		super.withFilter(filter);
		//
		if (filter.getIdProduct() != null) {
			this.withProduct(filter.getIdProduct());
		}
		//

		for (DimensionType dim : filter.getDimensions().keySet()) {
			switch (dim) {
			case Period:
			case PeriodTransaction:
			case Order:
				// Super
				break;
			case Product:
				withByProduct();
				break;
			}
		}
		//
		for (MeasureType type : filter.getMeasureTypes()) {
			switch (type) {
			case CountProduct:
				withCountProductInorder();
				break;
			case TotalAmountProduct:
				withSumProductPrice();
				break;
			case AvgProductPrice:
				withAvgProductPrice();
				break;
			case CountOrder:
			case TotalAmountOrder:
			case AvgOrderPrice:
				// No order (super)
				break;
			}
		}
		return this;
	}

	public MeasureProductQueryBuilder withProduct(Collection<Long> product) {
		// Or is null => because of full outer join
		if (!product.isEmpty()) {
			this.criteria.add(Restrictions.or(Restrictions.in("idProduct", product), Restrictions.isNull("idProduct")));
		}
		return this;
	}

	public MeasureProductQueryBuilder withAvgProductPrice() {
		this.projections.add(Projections.alias(Projections.avg("priceOfProduct"), MeasureType.AvgProductPrice.getSqlName()));
		return this;
	}

	public MeasureProductQueryBuilder withSumProductPrice() {
		this.projections.add(Projections.alias(Projections.sum("priceOfProduct"), MeasureType.TotalAmountProduct.getSqlName()));
		return this;
	}

}
