package com.rm.soa.discounts.tracking;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountTrackingLifeCycleContext {
	private Date relativeTo = new Date();
	private DiscountTrackingQueryBuilder base = DiscountTrackingQueryBuilder.get();
	private Map<DiscountTrackingLifeCycleRuleType, AbstractQueryBuilder<?>> queries = new HashMap<DiscountTrackingLifeCycleRuleType, AbstractQueryBuilder<?>>();

	public DiscountTrackingQueryBuilder getBase() {
		return (DiscountTrackingQueryBuilder) base.clone();
	}

	public void setBase(DiscountTrackingQueryBuilder query) {
		this.base = query;
	}

	public Map<DiscountTrackingLifeCycleRuleType, AbstractQueryBuilder<?>> getQueries() {
		return queries;
	}

	public void setQueries(Map<DiscountTrackingLifeCycleRuleType, AbstractQueryBuilder<?>> queries) {
		this.queries = queries;
	}

	public DiscountTrackingLifeCycleContext() {
		super();
	}

	public Date getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(Date relativeTo) {
		this.relativeTo = relativeTo;
	}

	public void put(DiscountTrackingLifeCycleRuleType type, AbstractQueryBuilder<?> q2) {
		this.queries.put(type, q2);
	}

}
