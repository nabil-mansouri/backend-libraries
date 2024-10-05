package com.rm.soa.discounts.tracking;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.dao.discounts.DaoDiscountTracking;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DiscountTrackingLifeCycleProcessor {

	@Autowired
	@Qualifier(DiscountTrackingLifeCycleComputer.DiscountLifeTrackingCycleComputer)
	private Map<String, DiscountTrackingLifeCycleComputer> strategies = new HashMap<String, DiscountTrackingLifeCycleComputer>();
	@Autowired
	@Qualifier(DiscountTrackingRecurrentComputer.DiscountTrackingRecurrentComputer)
	private Map<String, DiscountTrackingRecurrentComputer> strategiesRecurrent = new HashMap<String, DiscountTrackingRecurrentComputer>();
	@Autowired
	private DaoDiscountTracking daoDiscountTracking;

	protected Collection<String> getStrategies() {
		return Arrays.asList(
				//
				DiscountTrackingLifeCycleComputer.DiscountLifeTrackingCycleComputerDuration,
				DiscountTrackingLifeCycleComputer.DiscountLifeTrackingCycleComputerCount//
				);
	}

	protected Collection<String> getStrategiesRecurrent() {
		return Arrays.asList(
		//
				DiscountTrackingRecurrentComputer.DiscountTrackingRecurrentComputerPeriod);
	}

	public Collection<Long> findRestart() {
		DiscountTrackingLifeCycleContext context = new DiscountTrackingLifeCycleContext();
		return findRestart(context);
	}

	public Collection<Long> findRestart(DiscountTrackingLifeCycleContext context) {
		for (String s : getStrategiesRecurrent()) {
			strategiesRecurrent.get(s).findRestart(context);
		}
		// AND between criteria
		DiscountTrackingQueryBuilder query = context.getBase();
		for (DiscountTrackingLifeCycleRuleType type : context.getQueries().keySet()) {
			query.withTracking(context.getQueries().get(type));
		}
		return daoDiscountTracking.findIds(query);
	}

	public Collection<Long> findActive() {
		DiscountTrackingLifeCycleContext context = new DiscountTrackingLifeCycleContext();
		return findActive(context);
	}

	public Collection<Long> findActive(DiscountTrackingLifeCycleContext context) {
		for (String s : getStrategies()) {
			strategies.get(s).findActive(context);
		}
		// AND between criteria
		DiscountTrackingQueryBuilder query = context.getBase();
		for (DiscountTrackingLifeCycleRuleType type : context.getQueries().keySet()) {
			query.withTracking(context.getQueries().get(type));
		}
		return daoDiscountTracking.findIds(query);
	}

	public Collection<Long> findInactive() {
		DiscountTrackingLifeCycleContext context = new DiscountTrackingLifeCycleContext();
		return findInactive(context);
	}

	public Collection<Long> findInactive(DiscountTrackingLifeCycleContext context) {
		for (String s : getStrategies()) {
			strategies.get(s).findInactive(context);
		}
		// OR between criterias
		DiscountTrackingQueryBuilder query = context.getBase();
		query.withDisjunction();
		for (DiscountTrackingLifeCycleRuleType type : context.getQueries().keySet()) {
			query.withDisjunctionTracking(context.getQueries().get(type));
		}
		return daoDiscountTracking.findIds(query);
	}

}
