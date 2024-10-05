package com.rm.dao.discounts.tracking;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;

import com.rm.model.discounts.tracking.views.DiscountTrackingStatView;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountTrackingStatViewQueryBuilder extends AbstractQueryBuilder<DiscountTrackingStatViewQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static DiscountTrackingStatViewQueryBuilder get() {
		return new DiscountTrackingStatViewQueryBuilder(DiscountTrackingStatView.class);
	}

	private DetachedCriteria criteria;

	private DiscountTrackingStatViewQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DiscountTrackingStatViewQueryBuilder(DetachedCriteria c) {
		this.criteria = c;
	}

	public DiscountTrackingStatViewQueryBuilder withTrackingIdProjection() {
		this.criteria.setProjection(Projections.property("id"));
		return this;
	}

	public String getNbUsedName() {
		return wrapMain("nbUsed");
	}

	public String getDiscountIdName() {
		return wrapMain("idDiscount");
	}

	public DiscountTrackingStatViewQueryBuilder withNotExists(DiscountTrackingLifeCycleRuleQueryBuilder query) {
		this.criteria.add(Subqueries.notExists(query.withIdProjection().getQuery()));
		return this;
	}

	@Override
	public DetachedCriteria getQuery() {
		return criteria;
	}

}
