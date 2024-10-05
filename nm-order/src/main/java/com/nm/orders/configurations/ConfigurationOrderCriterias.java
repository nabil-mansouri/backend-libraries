package com.nm.orders.configurations;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.orders.checkers.OrderCriteriaChecker;
import com.nm.orders.checkers.OrderCriteriaCheckerProcessor;
import com.nm.orders.checkers.impl.OrderCriteriaCheckerAmount;
import com.nm.orders.checkers.impl.OrderCriteriaCheckerCount;
import com.nm.orders.checkers.impl.OrderCriteriaCheckerCumulable;
import com.nm.orders.checkers.impl.OrderCriteriaCheckerProduct;
import com.nm.orders.checkers.impl.OrderCriteriaCheckerRestaurant;
import com.nm.orders.checkers.impl.OrderCriteriaCheckerType;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationOrderCriterias {

	public static final String OrderCriteriaCheckerType = "OrderCriteriaChecker.OrderCriteriaCheckerType";
	public static final String OrderCriteriaCheckerProduct = "OrderCriteriaChecker.OrderCriteriaCheckerProduct";
	public static final String OrderCriteriaCheckerRestaurant = "OrderCriteriaChecker.OrderCriteriaCheckerRestaurant";
	public static final String OrderCriteriaCheckerAmount = "OrderCriteriaChecker.OrderCriteriaCheckerAmount";
	public static final String OrderCriteriaCheckerCount = "OrderCriteriaChecker.OrderCriteriaCheckerCount";
	public static final String OrderCriteriaCheckerCumulable = "OrderCriteriaChecker.OrderCriteriaCheckerCumulable";

	@Bean(name = OrderCriteriaCheckerAmount)
	public OrderCriteriaChecker orderCriteriaCheckerAmount() {
		return new OrderCriteriaCheckerAmount();
	}

	@Bean(name = OrderCriteriaCheckerCount)
	public OrderCriteriaChecker orderCriteriaCheckerCount() {
		return new OrderCriteriaCheckerCount();
	}

	@Bean(name = OrderCriteriaCheckerCumulable)
	public OrderCriteriaChecker orderCriteriaCheckerCumulable() {
		return new OrderCriteriaCheckerCumulable();
	}

	@Bean(name = OrderCriteriaCheckerProduct)
	public OrderCriteriaChecker orderCriteriaCheckerProduct() {
		return new OrderCriteriaCheckerProduct();
	}

	@Bean(name = OrderCriteriaCheckerRestaurant)
	public OrderCriteriaChecker orderCriteriaCheckerRestaurant() {
		return new OrderCriteriaCheckerRestaurant();
	}

	@Bean(name = OrderCriteriaCheckerType)
	public OrderCriteriaChecker orderCriteriaCheckerType() {
		return new OrderCriteriaCheckerType();
	}

	@Bean
	public OrderCriteriaCheckerProcessor OrderCriteriaCheckerProcessor(Map<String, OrderCriteriaChecker> strategies) {
		OrderCriteriaCheckerProcessor o = new OrderCriteriaCheckerProcessor();
		o.setStrategies(strategies);
		return o;
	}
}
