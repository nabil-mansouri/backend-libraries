package com.nm.orders.checkers.impl;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.checkers.OrderCriteriaChecker;
import com.nm.orders.checkers.OrderCriteriaContext;
import com.nm.orders.constants.OrderCriteriaType;
import com.nm.orders.models.criterias.OrderCriterias;

/***
 * 
 * @author Nabil
 * 
 */
public class OrderCriteriaCheckerCount implements OrderCriteriaChecker {
	// TODO
	public void check(OrderCriterias criteria, CartBean cart, OrderCriteriaContext context) {
		if (criteria.getRules().containsKey(OrderCriteriaType.CountProducts)) {
			// OrderCriteriaRules rules =
			// criteria.getRules().get(OrderCriteriaType.CountProducts);
			// long total = cart.getDetails().size();
			// for (OrderCriteriaRule rule : rules.getRules()) {
			// if (rule instanceof OrderCriteriaRuleRange) {
			// OrderCriteriaRuleRange ruleT = (OrderCriteriaRuleRange) rule;
			// if (!RangeUtils.safeTest(ruleT.getFrom(), ruleT.getTo(), total))
			// {
			// context.getFailed().add(OrderCriteriaType.CountProducts);
			// }
			// }
			// }
		}
	}
}
