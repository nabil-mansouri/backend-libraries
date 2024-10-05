package com.nm.orders.checkers.impl;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.checkers.OrderCriteriaChecker;
import com.nm.orders.checkers.OrderCriteriaContext;
import com.nm.orders.constants.OrderCriteriaType;
import com.nm.orders.models.criterias.OrderCriteriaRule;
import com.nm.orders.models.criterias.OrderCriteriaRuleType;
import com.nm.orders.models.criterias.OrderCriteriaRules;
import com.nm.orders.models.criterias.OrderCriterias;

/***
 * 
 * @author Nabil
 * 
 */
public class OrderCriteriaCheckerType implements OrderCriteriaChecker {
	// TODO
	public void check(OrderCriterias criteria, CartBean cart, OrderCriteriaContext context) {
		if (criteria.getRules().containsKey(OrderCriteriaType.OrderType)) {
			OrderCriteriaRules rules = criteria.getRules().get(OrderCriteriaType.OrderType);
			for (OrderCriteriaRule rule : rules.getRules()) {
				if (rule instanceof OrderCriteriaRuleType) {
					OrderCriteriaRuleType ruleT = (OrderCriteriaRuleType) rule;
					switch (ruleT.getOperator()) {
					case In:
						// if (!ruleT.getTypes().contains(cart.getType())) {
						// context.getFailed().add(OrderCriteriaType.OrderType);
						// }
						break;
					case NotIn:
						// if (ruleT.getTypes().contains(cart.getType())) {
						// context.getFailed().add(OrderCriteriaType.OrderType);
						// }
						break;
					}
				}
			}
		}
	}
}
