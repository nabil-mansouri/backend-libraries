package com.nm.orders.checkers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.configurations.ConfigurationOrderCriterias;
import com.nm.orders.models.criterias.OrderCriterias;

/**
 * 
 * @author Nabil
 * 
 */
public class OrderCriteriaCheckerProcessor {

	private Map<String, OrderCriteriaChecker> strategies = new HashMap<String, OrderCriteriaChecker>();

	public void setStrategies(Map<String, OrderCriteriaChecker> strategies) {
		this.strategies = strategies;
	}

	protected Collection<String> getStrategies() {
		return Arrays.asList(ConfigurationOrderCriterias.OrderCriteriaCheckerAmount, //
				ConfigurationOrderCriterias.OrderCriteriaCheckerCount, //
				ConfigurationOrderCriterias.OrderCriteriaCheckerProduct, //
				ConfigurationOrderCriterias.OrderCriteriaCheckerRestaurant, //
				ConfigurationOrderCriterias.OrderCriteriaCheckerType, //
				ConfigurationOrderCriterias.OrderCriteriaCheckerCumulable//
		);
	}

	public OrderCriteriaContext check(OrderCriterias criterias, CartBean cart) {
		OrderCriteriaContext context = new OrderCriteriaContext();
		for (String s : getStrategies()) {
			strategies.get(s).check(criterias, cart, context);
		}
		return context;
	}

}
