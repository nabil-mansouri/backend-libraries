package com.rm.dao.discounts.tracking;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.model.discounts.tracking.DiscountTrackingLifeCycle;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountTrackingLifeCycleQueryBuilder extends AbstractQueryBuilder<DiscountTrackingLifeCycleQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static DiscountTrackingLifeCycleQueryBuilder get() {
		return new DiscountTrackingLifeCycleQueryBuilder(DiscountTrackingLifeCycle.class);
	}

	public static DiscountTrackingLifeCycleQueryBuilder get(int index) {
		return new DiscountTrackingLifeCycleQueryBuilder(DiscountTrackingLifeCycle.class, index);
	}

	private DiscountTrackingLifeCycleQueryBuilder(Class<?> clazz, int index) {
		mainAlias = mainAlias + index;
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DetachedCriteria criteria;

	private DiscountTrackingLifeCycleQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DiscountTrackingLifeCycleQueryBuilder(DetachedCriteria c) {
		this.criteria = c;
	}

	public String getDiscountIdName() {
		createAlias(wrapMain("tracking"), "tracking");
		createAlias(("tracking.discount"), "discount");
		return ("discount.id");
	}

	public String getIdName() {
		return wrapMain("id");
	}

	public DiscountTrackingLifeCycleQueryBuilder withStateType(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("states"), ("states"));
		this.criteria.add(Restrictions.eq("states.type", type));
		return this;
	}

	@Override
	public DetachedCriteria getQuery() {
		return this.criteria;
	}

	public DiscountTrackingLifeCycleQueryBuilder withNotLastStateType(DiscountLifeCycleStateType unplanned) {
		createAlias(wrapMain("lastState"), "lastState");
		this.criteria.add(Restrictions.ne("lastState.type", unplanned));
		return this;
	}

	public DiscountTrackingLifeCycleQueryBuilder withLastStateType(DiscountLifeCycleStateType unplanned) {
		createAlias(wrapMain("lastState"), "lastState");
		this.criteria.add(Restrictions.eq("lastState.type", unplanned));
		return this;
	}
}
