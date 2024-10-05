package com.nm.orders.checkers.impl;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.checkers.OrderCriteriaChecker;
import com.nm.orders.checkers.OrderCriteriaContext;
import com.nm.orders.constants.OrderCriteriaType;
import com.nm.orders.models.criterias.OrderCriteriaRule;
import com.nm.orders.models.criterias.OrderCriteriaRuleCumulable;
import com.nm.orders.models.criterias.OrderCriteriaRules;
import com.nm.orders.models.criterias.OrderCriterias;

/***
 * 
 * @author Nabil
 * 
 */
public class OrderCriteriaCheckerCumulable implements OrderCriteriaChecker {
	// TODO
	public void check(OrderCriterias criteria, CartBean cart, OrderCriteriaContext context) {
		if (criteria.getRules().containsKey(OrderCriteriaType.Cumulable)) {
			OrderCriteriaRules rules = criteria.getRules().get(OrderCriteriaType.Cumulable);
			for (OrderCriteriaRule rule : rules.getRules()) {
				if (rule instanceof OrderCriteriaRuleCumulable) {
					OrderCriteriaRuleCumulable ruleT = (OrderCriteriaRuleCumulable) rule;
					switch (ruleT.getCumulable()) {
					case Cumulable:
						break;
					case NotCumulable:
						// if (cart.getDiscounts().size() > 1) {
						// context.getFailed().add(OrderCriteriaType.Cumulable);
						// }
						break;
					}
				}
			}
		}
	}
}
