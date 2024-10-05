package com.rm.dao.discounts.lifecycle;

import java.util.Date;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRule;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRuleCount;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRuleDate;
import com.rm.model.discounts.lifecycle.DiscountLifeCycleRulePeriod;
import com.rm.utils.dao.impl.AbstractQueryBuilder;
import com.rm.utils.dates.DateUtilsMore;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountLifeCycleRuleQueryBuilder extends AbstractQueryBuilder<DiscountLifeCycleRuleQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static DiscountLifeCycleRuleQueryBuilder get() {
		return new DiscountLifeCycleRuleQueryBuilder(DiscountLifeCycleRule.class);
	}

	public static DiscountLifeCycleRuleQueryBuilder getDate() {
		return new DiscountLifeCycleRuleQueryBuilder(DiscountLifeCycleRuleDate.class);
	}

	public static DiscountLifeCycleRuleQueryBuilder getPeriod() {
		return new DiscountLifeCycleRuleQueryBuilder(DiscountLifeCycleRulePeriod.class);
	}

	public static DiscountLifeCycleRuleQueryBuilder getNumber() {
		return new DiscountLifeCycleRuleQueryBuilder(DiscountLifeCycleRuleCount.class);
	}

	private DetachedCriteria criteria;

	private DiscountLifeCycleRuleQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DiscountLifeCycleRuleQueryBuilder(DetachedCriteria c) {
		this.criteria = c;
	}

	public String getDiscountIdName() {
		createAlias(wrapMain("discount"), "discount");
		return "discount.id";
	}

	public DiscountLifeCycleRuleQueryBuilder withLimitGt(DiscountLifeCycleQueryBuilder query) {
		this.criteria.add(Property.forName(wrapMain("limit")).gtProperty(query.getStateDateName()));
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withLimitLe(DiscountLifeCycleQueryBuilder query) {
		this.criteria.add(Property.forName(wrapMain("limit")).leProperty(query.getStateDateName()));
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withLifeycleProjection() {
		createAlias(wrapMain("discount"), "discount");
		createAlias(("discount.lifecycle"), "lifecycle");
		this.criteria.setProjection(Projections.property("lifecycle.id"));
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withJoin(DiscountLifeCycleQueryBuilder query) {
		createAlias(wrapMain("discount"), "discount");
		createAlias(("discount.lifecycle"), "lifecycle");
		this.criteria.add(Property.forName("lifecycle.id").eqProperty(query.getIdName()));
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withDisjunctionIds(DiscountLifeCycleRuleQueryBuilder query) {
		this.disjunction.add(Subqueries.propertyIn(wrapMain("id"), query.withIdProjection().getQuery()));
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withDisjunctionDateBetween(Date date) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.le(wrapMain("fromDate"), date));
		conjunction.add(Restrictions.ge(wrapMain("toDate"), date));
		this.disjunction.add(conjunction);
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withDisjunctionDateFrom(Date date) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.le(wrapMain("fromDate"), date));
		conjunction.add(Restrictions.isNull(wrapMain("toDate")));
		this.disjunction.add(conjunction);
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withDisjunctionDateTo(Date date) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.isNull(wrapMain("fromDate")));
		conjunction.add(Restrictions.ge(wrapMain("toDate"), date));
		this.disjunction.add(conjunction);
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withDisjunctionDateEq(Date date) {
		Conjunction conjunction = Restrictions.and();
		Date begin = DateUtilsMore.startDays(date);
		Date end = DateUtilsMore.endDays(date);
		conjunction.add(Restrictions.between(wrapMain("fromDate"), begin, end));
		conjunction.add(Restrictions.eq("exact", true));
		this.disjunction.add(conjunction);
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withDateAfter(Date date) {
		this.criteria.add(Restrictions.lt(wrapMain("toDate"), date));
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withMaxLessThan(DiscountTrackingQueryBuilder query) {
		this.criteria.add(Subqueries.exists(query.withJoin(this).withCountByDiscountLt(this).getQuery()));
		return this;
	}

	public DiscountLifeCycleRuleQueryBuilder withMaxGeThan(DiscountTrackingQueryBuilder query) {
		this.criteria.add(Subqueries.exists(query.withJoin(this).withCountByDiscountGe(this).getQuery()));
		return this;
	}

	public String getMaxNameNative() {
		return wrapMainNative("max");
	}

	@Override
	public DetachedCriteria getQuery() {
		return this.criteria;
	}
}
