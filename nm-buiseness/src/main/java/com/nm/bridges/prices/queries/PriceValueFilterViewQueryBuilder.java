package com.nm.bridges.prices.queries;

import java.util.Collection;
import java.util.Date;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.dtos.CustomPriceFilterDto;
import com.nm.bridges.prices.models.filters.PriceValueFiltersView;
import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dao.impl.PriceSubjectQueryBuilder;
import com.nm.prices.dao.impl.PriceValueQueryBuilder;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PriceValueFilterViewQueryBuilder extends AbstractQueryBuilder<PriceValueFilterViewQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final DetachedCriteria criteria;

	protected PriceValueFilterViewQueryBuilder(Class<? extends PriceValueFiltersView> clazz) {
		this(clazz, "PriceValueFilterViewQueryBuilder");
	}

	private PriceValueFilterViewQueryBuilder(Class<? extends PriceValueFiltersView> clazz, String alias) {
		criteria = DetachedCriteria.forClass(clazz, alias);
		withMainAlias(alias);
	}

	public PriceValueFilterViewQueryBuilder withRoot(Boolean root) {
		this.criteria.add(Restrictions.eq("root", root));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withValue(PriceValueQueryBuilder query) {
		this.criteria.add(Subqueries.propertyIn("idValue", query.withIdProjection().getQuery()));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withPrice(Long id) {
		this.criteria.add(Restrictions.eq("idPrice", id));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withPrice(PriceQueryBuilder query) {
		this.criteria.add(Subqueries.propertyIn("idPrice", query.withIdProjection().getQuery()));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withFilter(PriceFilterDto f) {
		if (f instanceof CustomPriceFilterDto) {
			CustomPriceFilterDto filter = (CustomPriceFilterDto) f;
			if (!filter.isAnyOrder()) {
				withOrderType(filter.getOrderTypes());
			}
			if (!filter.isAnyProduct()) {
				withProducts(filter.getProducts());
			}
			if (!filter.isAnyRestaurant()) {
				withRestaurant(filter.getRestaurants());
			}
			if (filter.isOnlyCurrent()) {
				withOnlyCurrent(filter.isOnlyCurrent());
			}
			if (filter.getFrom() != null) {
				withDateFromOk(filter.getFrom());
			}
			if (filter.getTo() != null) {
				withDateToOk(filter.getTo());
			}
			return this;
		}
		return this;
	}

	public PriceValueFilterViewQueryBuilder withDateFromOk(Date d) {
		criteria.add(Restrictions.or(Restrictions.le("from", d), Restrictions.isNull("from")));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withDateToOk(Date d) {
		criteria.add(Restrictions.or(Restrictions.ge("to", d), Restrictions.isNull("to")));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withOnlyCurrent(Boolean onlyCurrent) {
		Date now = new Date();
		Conjunction and = Restrictions.and();
		and.add(Restrictions.or(Restrictions.le("from", now), Restrictions.isNull("from")));
		and.add(Restrictions.or(Restrictions.ge("to", now), Restrictions.isNull("to")));
		//
		if (onlyCurrent) {
			criteria.add(and);
		} else {
			criteria.add(Restrictions.not(and));
		}
		return this;
	}

	public PriceValueFilterViewQueryBuilder withSubject(PriceSubjectQueryBuilder query) {
		criteria.add(Subqueries.propertyIn("idSubject", query.getQuery()));
		return this;
	}

	@Override
	public DetachedCriteria getQuery() {
		return criteria;
	}

	public static PriceValueFilterViewQueryBuilder get() {
		return new PriceValueFilterViewQueryBuilder(PriceValueFiltersView.class);
	}

	public PriceValueFilterViewQueryBuilder withProducts(Collection<Long> ids) {
		if (ids.isEmpty()) {
			return this;
		}
		PriceSubjectQueryBuilder query = CustomPriceSubjectQueryBuilder.getProduct().withProducts(ids)
				.withIdProjection();
		criteria.add(Subqueries.propertyIn("idSubject", query.getQuery()));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withOrderType(Collection<OrderType> ids) {
		if (ids.isEmpty()) {
			return this;
		}
		criteria.add(Restrictions.in("orderType", ids));
		return this;
	}

	public PriceValueFilterViewQueryBuilder withRestaurant(Collection<Long> ids) {
		if (ids.isEmpty()) {
			return this;
		}
		criteria.add(Restrictions.in("idResto", ids));
		return this;
	}
}
