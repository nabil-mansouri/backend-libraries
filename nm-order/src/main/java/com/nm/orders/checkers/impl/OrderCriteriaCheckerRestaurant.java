package com.nm.orders.checkers.impl;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.checkers.OrderCriteriaChecker;
import com.nm.orders.checkers.OrderCriteriaContext;
import com.nm.orders.constants.OrderCriteriaType;
import com.nm.orders.models.criterias.OrderCriteriaRule;
import com.nm.orders.models.criterias.OrderCriteriaRuleShop;
import com.nm.orders.models.criterias.OrderCriteriaRules;
import com.nm.orders.models.criterias.OrderCriterias;

/***
 * 
 * @author Nabil
 * 
 */
public class OrderCriteriaCheckerRestaurant implements OrderCriteriaChecker {
	// TODO
	public void check(OrderCriterias criteria, CartBean cart, OrderCriteriaContext context) {
		if (criteria.getRules().containsKey(OrderCriteriaType.Restaurant)) {
			OrderCriteriaRules rules = criteria.getRules().get(OrderCriteriaType.Restaurant);
			for (OrderCriteriaRule rule : rules.getRules()) {
				if (rule instanceof OrderCriteriaRuleShop) {
					OrderCriteriaRuleShop ruleT = (OrderCriteriaRuleShop) rule;
					switch (ruleT.getOperator()) {
					case In:
						// if
						// (!ruleT.getRestaurant().contains(cart.getRestaurant().getId()))
						// {
						// context.getFailed().add(OrderCriteriaType.Restaurant);
						// }
						break;
					case NotIn:
						// if
						// (ruleT.getRestaurant().contains(cart.getRestaurant().getId()))
						// {
						// context.getFailed().add(OrderCriteriaType.Restaurant);
						// }
						break;
					}
				}
			}
		}
	}
}
