package com.rm.dao.discounts.lifecycle;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.model.discounts.lifecycle.DiscountLifeCycle;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountLifeCycleQueryBuilder extends AbstractQueryBuilder<DiscountLifeCycleQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static DiscountLifeCycleQueryBuilder get() {
		return new DiscountLifeCycleQueryBuilder(DiscountLifeCycle.class);
	}

	private DetachedCriteria criteria;

	private DiscountLifeCycleQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DiscountLifeCycleQueryBuilder(DetachedCriteria c) {
		this.criteria = c;
	}

	public String getIdName() {
		return wrapMain("id");
	}

	public DiscountLifeCycleQueryBuilder withDisjunctionNotExists(DiscountLifeCycleRuleQueryBuilder query) {
		this.disjunction.add(Subqueries.notExists(query.withIdProjection().getQuery()));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withDisjunctionNotExists(DiscountTrackingQueryBuilder query) {
		this.disjunction.add(Subqueries.notExists(query.withJoin(this).withIdProjection().getQuery()));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withDisjunctionIdRuleIn(DiscountLifeCycleRuleQueryBuilder query) {
		this.disjunction.add(Subqueries.propertyIn(wrapMain("id"), query.withLifeycleProjection().getQuery()));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withStateType(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("states"), ("states"));
		this.criteria.add(Restrictions.eq("states.type", type));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withNotStateType(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("states"), ("states"));
		this.criteria.add(Restrictions.ne("states.type", type));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withLastStateType(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("lastState"), ("lastState"));
		this.criteria.add(Restrictions.eq("lastState.type", type));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withExists(DiscountLifeCycleRuleQueryBuilder q) {
		this.criteria.add(Subqueries.exists(q.withIdProjection().getQuery()));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withNotLastStateType(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("lastState"), ("lastState"));
		this.criteria.add(Restrictions.ne("lastState.type", type));
		return this;
	}

	@Override
	public DetachedCriteria getQuery() {
		return this.criteria;
	}

	public String getDiscountIdName() {
		createAlias(wrapMain("discount"), "discount");
		return "discount.id";
	}

	public String getStateDateName() {
		createAlias(wrapMain("states"), "states");
		return "states.created";
	}

	public DiscountLifeCycleQueryBuilder withDisjunctionIdIn(DiscountLifeCycleQueryBuilder q1) {
		this.disjunction.add(Subqueries.propertyIn("id", q1.withIdProjection().getQuery()));
		return this;
	}

	public DiscountLifeCycleQueryBuilder withDisjunctionIdNotIn(DiscountLifeCycleQueryBuilder q1) {
		this.disjunction.add(Subqueries.propertyNotIn("id", q1.withIdProjection().getQuery()));
		return this;
	}

}
