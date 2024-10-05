package com.nm.orders.checkers.impl;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.checkers.OrderCriteriaChecker;
import com.nm.orders.checkers.OrderCriteriaContext;
import com.nm.orders.constants.OrderCriteriaType;
import com.nm.orders.models.criterias.OrderCriteriaRule;
import com.nm.orders.models.criterias.OrderCriteriaRuleRange;
import com.nm.orders.models.criterias.OrderCriteriaRules;
import com.nm.orders.models.criterias.OrderCriterias;

/***
 * 
 * @author Nabil
 * 
 */
public class OrderCriteriaCheckerAmount implements OrderCriteriaChecker {
	// TODO
	public void check(OrderCriterias criteria, CartBean cart, OrderCriteriaContext context) {
		if (criteria.getRules().containsKey(OrderCriteriaType.TotalAmount)) {
			OrderCriteriaRules rules = criteria.getRules().get(OrderCriteriaType.TotalAmount);
			for (OrderCriteriaRule rule : rules.getRules()) {
				if (rule instanceof OrderCriteriaRuleRange) {
					// OrderCriteriaRuleRange ruleT = (OrderCriteriaRuleRange)
					// rule;
					// if (!RangeUtils.safeTest(ruleT.getFrom(), ruleT.getTo(),
					// cart.getTotal())) {
					// context.getFailed().add(OrderCriteriaType.TotalAmount);
					// }
				}
			}
		}
	}
}
