package com.rm.soa.discounts.lifecycle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.dao.discounts.impl.DiscountQueryBuilder;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountLifeCycleContext {
	private Map<DiscountLifeCycleRuleType, AbstractQueryBuilder<?>> queries = new HashMap<DiscountLifeCycleRuleType, AbstractQueryBuilder<?>>();
	private DiscountQueryBuilder base = DiscountQueryBuilder.get();
	/**
	 * Only for date rule
	 */
	private Date relativeTo = new Date();

	public Date getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(Date relativeTo) {
		this.relativeTo = relativeTo;
	}

	public DiscountQueryBuilder getBase() {
		return (DiscountQueryBuilder) base.clone();
	}

	public void setBase(DiscountQueryBuilder base) {
		this.base = base;
	}

	public Map<DiscountLifeCycleRuleType, AbstractQueryBuilder<?>> getQueries() {
		return queries;
	}

	public void setQueries(Map<DiscountLifeCycleRuleType, AbstractQueryBuilder<?>> queries) {
		this.queries = queries;
	}

	public DiscountLifeCycleContext() {
		super();
	}

	public void put(DiscountLifeCycleRuleType type, AbstractQueryBuilder<?> q2) {
		this.queries.put(type, q2);
	}

}
