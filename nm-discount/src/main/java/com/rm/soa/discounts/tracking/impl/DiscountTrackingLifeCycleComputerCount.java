package com.rm.soa.discounts.tracking.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountTrackingLifeCycleRuleType;
import com.rm.dao.discounts.tracking.DiscountTrackingLifeCycleRuleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingStatViewQueryBuilder;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleComputer;
import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountTrackingLifeCycleComputer.DiscountLifeTrackingCycleComputerCount)
@Qualifier(DiscountTrackingLifeCycleComputer.DiscountLifeTrackingCycleComputer)
public class DiscountTrackingLifeCycleComputerCount implements DiscountTrackingLifeCycleComputer {

	public void findActive(DiscountTrackingLifeCycleContext context) {
		{
			DiscountTrackingQueryBuilder q0 = DiscountTrackingQueryBuilder.get();
			DiscountTrackingStatViewQueryBuilder q1 = DiscountTrackingStatViewQueryBuilder.get();
			DiscountTrackingLifeCycleRuleQueryBuilder q2 = DiscountTrackingLifeCycleRuleQueryBuilder.getNumber();
			q2.withJoin(q1).withMaxLe(q1);
			q1.withNotExists(q2).withTrackingIdProjection();
			DiscountTrackingLifeCycleRuleQueryBuilder q3 = DiscountTrackingLifeCycleRuleQueryBuilder.getNumber();
			//
			q0.withDisjunction();
			q0.withDisjunctionIdIn(q1).withDisjunctionNotExists(q3);
			q0.withIdProjection();
			context.put(DiscountTrackingLifeCycleRuleType.RelativeCount, q0);
		}
	}

	public void findInactive(DiscountTrackingLifeCycleContext context) {
		{
			DiscountTrackingQueryBuilder q0 = DiscountTrackingQueryBuilder.get();
			DiscountTrackingStatViewQueryBuilder q1 = DiscountTrackingStatViewQueryBuilder.get();
			DiscountTrackingLifeCycleRuleQueryBuilder q2 = DiscountTrackingLifeCycleRuleQueryBuilder.getNumber();
			q2.withJoin(q1).withMaxGt(q1);
			q1.withNotExists(q2).withTrackingIdProjection();
			DiscountTrackingLifeCycleRuleQueryBuilder q3 = DiscountTrackingLifeCycleRuleQueryBuilder.getNumber();
			//
			q0.withIdIn(q1).withExists(q3);
			q0.withIdProjection();
			context.put(DiscountTrackingLifeCycleRuleType.RelativeCount, q0);
		}
	}

}
