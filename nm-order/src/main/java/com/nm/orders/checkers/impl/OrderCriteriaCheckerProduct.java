package com.nm.orders.checkers.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.checkers.OrderCriteriaChecker;
import com.nm.orders.checkers.OrderCriteriaContext;
import com.nm.orders.constants.OrderCriteriaType;
import com.nm.orders.models.criterias.OrderCriteriaRule;
import com.nm.orders.models.criterias.OrderCriteriaRuleProduct;
import com.nm.orders.models.criterias.OrderCriteriaRules;
import com.nm.orders.models.criterias.OrderCriterias;
import com.nm.utils.numbers.RangeUtils;

/***
 * 
 * @author Nabil
 * 
 */ 
public class OrderCriteriaCheckerProduct implements OrderCriteriaChecker {
	// TODO
	public void check(OrderCriterias criteria, CartBean cart, OrderCriteriaContext context) {
		if (criteria.getRules().containsKey(OrderCriteriaType.HavingProducts)) {
			OrderCriteriaRules rules = criteria.getRules().get(OrderCriteriaType.HavingProducts);
			// COUNT ALL
			ConcurrentMap<Long, Long> countProducts = new ConcurrentHashMap<Long, Long>();
			// for (CartProductBean c : cart.getDetails()) {
			// countProducts.putIfAbsent(c.getProduct().getId(), 0l);
			// Long count = countProducts.get(c.getProduct().getId());
			// countProducts.put(c.getProduct().getId(), count + 1);
			// }
			// TEST
			for (OrderCriteriaRule rule : rules.getRules()) {
				if (rule instanceof OrderCriteriaRuleProduct) {
					OrderCriteriaRuleProduct ruleT = (OrderCriteriaRuleProduct) rule;
					for (Long pro : ruleT.getProduct()) {
						if (!RangeUtils.safeTest(ruleT.getFrom(), ruleT.getTo(), countProducts.get(pro))) {
							context.getFailed().add(OrderCriteriaType.CountProducts);
						}
					}
				}
			}
		}
	}
}
