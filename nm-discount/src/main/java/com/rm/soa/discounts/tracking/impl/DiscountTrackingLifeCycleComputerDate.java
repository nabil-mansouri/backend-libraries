package com.rm.soa.discounts.tracking.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.dao.discounts.tracking.DiscountTrackingLifeCycleRuleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleComputer;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountTrackingLifeCycleComputer.DiscountLifeTrackingCycleComputerDuration)
@Qualifier(DiscountTrackingLifeCycleComputer.DiscountLifeTrackingCycleComputer)
public class DiscountTrackingLifeCycleComputerDate implements DiscountTrackingLifeCycleComputer {

	public void findActive(DiscountTrackingLifeCycleContext context) {
		{
			DiscountTrackingQueryBuilder q0 = DiscountTrackingQueryBuilder.get();
			DiscountTrackingQueryBuilder q1 = DiscountTrackingQueryBuilder.get();
			q1.withStateType(DiscountLifeCycleStateType.ReadyToUse);
			DiscountTrackingLifeCycleRuleQueryBuilder q2 = DiscountTrackingLifeCycleRuleQueryBuilder.getPeriod();
			q2.withJoin(q1).withLimitGt(q1);
			q1.withNotExists(q2).withIdProjection();
			DiscountTrackingLifeCycleRuleQueryBuilder q3 = DiscountTrackingLifeCycleRuleQueryBuilder.getPeriod();
			//
			q0.withDisjunction();
			q0.withDisjunctionTracking(q1).withDisjunctionNotExists(q3);
			q0.withIdProjection();
			context.put(DiscountTrackingLifeCycleRuleType.Duration, q0);
		}
	}

	public void findInactive(DiscountTrackingLifeCycleContext context) {
		{
			DiscountTrackingQueryBuilder q0 = DiscountTrackingQueryBuilder.get();
			DiscountTrackingQueryBuilder q1 = DiscountTrackingQueryBuilder.get();
			q1.withStateType(DiscountLifeCycleStateType.ReadyToUse);
			DiscountTrackingLifeCycleRuleQueryBuilder q2 = DiscountTrackingLifeCycleRuleQueryBuilder.getPeriod();
			q2.withJoin(q1).withLimitLe(q1);
			q1.withNotExists(q2).withIdProjection();
			DiscountTrackingLifeCycleRuleQueryBuilder q3 = DiscountTrackingLifeCycleRuleQueryBuilder.getPeriod();
			//
			q0.withTracking(q1).withExists(q3);
			q0.withIdProjection();
			context.put(DiscountTrackingLifeCycleRuleType.Duration, q0);
		}
	}

}
