package com.rm.dao.discounts.tracking;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRule;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRuleCount;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRulePeriod;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleRulePeriodic;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountTrackingLifeCycleRuleQueryBuilder
		extends AbstractQueryBuilder<DiscountTrackingLifeCycleRuleQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static DiscountTrackingLifeCycleRuleQueryBuilder get() {
		return new DiscountTrackingLifeCycleRuleQueryBuilder(DiscountTrackingLifeCycleRule.class);
	}

	public static DiscountTrackingLifeCycleRuleQueryBuilder getPeriod() {
		return new DiscountTrackingLifeCycleRuleQueryBuilder(DiscountTrackingLifeCycleRulePeriod.class);
	}

	public static DiscountTrackingLifeCycleRuleQueryBuilder getPeriodic() {
		return new DiscountTrackingLifeCycleRuleQueryBuilder(DiscountTrackingLifeCycleRulePeriodic.class);
	}

	public static DiscountTrackingLifeCycleRuleQueryBuilder getNumber() {
		return new DiscountTrackingLifeCycleRuleQueryBuilder(DiscountTrackingLifeCycleRuleCount.class);
	}

	private DetachedCriteria criteria;

	private DiscountTrackingLifeCycleRuleQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DiscountTrackingLifeCycleRuleQueryBuilder(DetachedCriteria c) {
		this.criteria = c;
	}

	public String getDiscountIdName() {
		createAlias(wrapMain("discount"), "discount");
		return "discount.id";
	}

	public DiscountTrackingLifeCycleRuleQueryBuilder withJoin(DiscountTrackingStatViewQueryBuilder query) {
		this.criteria.add(Property.forName(getDiscountIdName()).eqProperty(query.getDiscountIdName()));
		return this;
	}

	public DiscountTrackingLifeCycleRuleQueryBuilder withMaxGt(DiscountTrackingStatViewQueryBuilder query) {
		this.criteria.add(Property.forName(wrapMain("max")).gtProperty(query.getNbUsedName()));
		return this;
	}

	public DiscountTrackingLifeCycleRuleQueryBuilder withMaxLe(DiscountTrackingStatViewQueryBuilder query) {
		this.criteria.add(Property.forName(wrapMain("max")).leProperty(query.getNbUsedName()));
		return this;
	}

	public DiscountTrackingLifeCycleRuleQueryBuilder withLimitGt(DiscountTrackingQueryBuilder query) {
		this.criteria.add(Property.forName(wrapMain("limit")).gtProperty(query.getStateDateName()));
		return this;
	}

	public DiscountTrackingLifeCycleRuleQueryBuilder withLimitLe(DiscountTrackingQueryBuilder query) {
		this.criteria.add(Property.forName(wrapMain("limit")).leProperty(query.getStateDateName()));
		return this;
	}

	public DiscountTrackingLifeCycleRuleQueryBuilder withLimitGe(DiscountTrackingQueryBuilder query) {
		this.criteria.add(Property.forName(wrapMain("limit")).geProperty(query.getStateDateName()));
		return this;
	}

	@Override
	public DetachedCriteria getQuery() {
		return this.criteria;
	}

	public DiscountTrackingLifeCycleRuleQueryBuilder withJoin(DiscountTrackingQueryBuilder query) {
		createAlias(wrapMain("discount"), "discount");
		this.criteria.add(Property.forName("discount.id").eqProperty(query.getDiscountIdName()));
		return this;
	}
}
