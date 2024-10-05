package com.rm.soa.discounts.tracking.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.dao.discounts.tracking.DiscountTrackingLifeCycleRuleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleContext;
import com.rm.soa.discounts.tracking.DiscountTrackingRecurrentComputer;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountTrackingRecurrentComputer.DiscountTrackingRecurrentComputerPeriod)
@Qualifier(DiscountTrackingRecurrentComputer.DiscountTrackingRecurrentComputer)
public class DiscountTrackingRecurrentComputerPeriod implements DiscountTrackingRecurrentComputer {
	public void findRestart(DiscountTrackingLifeCycleContext context) {
		DiscountTrackingQueryBuilder q0 = DiscountTrackingQueryBuilder.get();
		DiscountTrackingQueryBuilder q1 = DiscountTrackingQueryBuilder.get();
		q1.withStateType(DiscountLifeCycleStateType.ReadyToUse);
		DiscountTrackingLifeCycleRuleQueryBuilder q2 = DiscountTrackingLifeCycleRuleQueryBuilder.getPeriodic();
		q2.withJoin(q1).withLimitGe(q1);
		q1.withExists(q2).withIdProjection();
		q0.withDisjunction();
		q0.withDisjunctionTracking(q1);
		q0.withIdProjection();
		context.put(DiscountTrackingLifeCycleRuleType.RecurrentPeriod, q0);
	}

}
