package com.rm.soa.discounts.lifecycle;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.dao.discounts.DaoDiscount;
import com.rm.dao.discounts.impl.DiscountQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DiscountLifeCycleProcessor {

	@Autowired
	@Qualifier(DiscountLifeCycleComputer.DiscountLifeCycleComputer)
	private Map<String, DiscountLifeCycleComputer> strategies = new HashMap<String, DiscountLifeCycleComputer>();
	@Autowired
	private DaoDiscount daoDiscount;

	protected Collection<String> getStrategies() {
		return Arrays.asList(DiscountLifeCycleComputer.DiscountLifeCycleComputerDate,//
				DiscountLifeCycleComputer.DiscountLifeCycleComputerDuration,//
				DiscountLifeCycleComputer.DiscountLifeCycleComputerCount//
				);
	}

	public Collection<Long> findAvailable() {
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		return findAvailable(context);
	}

	public Collection<Long> findAvailable(DiscountLifeCycleContext context) {
		for (String s : getStrategies()) {
			strategies.get(s).findAvailable(context);
		}
		// AND between criteria
		DiscountQueryBuilder query = context.getBase();
		for (DiscountLifeCycleRuleType type : context.getQueries().keySet()) {
			query.withLifeCycle(context.getQueries().get(type));
		}
		return daoDiscount.findIds(query);
	}

	public Collection<Long> findUnAvailable() {
		DiscountLifeCycleContext context = new DiscountLifeCycleContext();
		return findUnAvailable(context);
	}

	public Collection<Long> findUnAvailable(DiscountLifeCycleContext context) {
		for (String s : getStrategies()) {
			strategies.get(s).findUnavailable(context);
		}
		// OR between criterias
		DiscountQueryBuilder query = context.getBase();
		query.withDisjunction();
		for (DiscountLifeCycleRuleType type : context.getQueries().keySet()) {
			query.withDisjunctionLifeCycle(context.getQueries().get(type));
		}
		return daoDiscount.findIds(query);
	}

}
