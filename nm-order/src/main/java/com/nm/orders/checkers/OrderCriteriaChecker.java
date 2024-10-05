package com.nm.orders.checkers;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.orders.models.criterias.OrderCriterias;

/**
 * 
 * @author Nabil
 * 
 */
public interface OrderCriteriaChecker {
	public void check(OrderCriterias criteria, CartBean cart, OrderCriteriaContext context);
}
