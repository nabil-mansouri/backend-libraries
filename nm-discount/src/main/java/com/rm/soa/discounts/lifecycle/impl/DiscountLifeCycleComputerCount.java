package com.rm.soa.discounts.lifecycle.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleQueryBuilder;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleRuleQueryBuilder;
import com.rm.dao.discounts.tracking.DiscountTrackingQueryBuilder;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleComputer;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountLifeCycleComputer.DiscountLifeCycleComputerCount)
@Qualifier(DiscountLifeCycleComputer.DiscountLifeCycleComputer)
public class DiscountLifeCycleComputerCount implements DiscountLifeCycleComputer {

	public void findAvailable(DiscountLifeCycleContext context) {
		{
			DiscountTrackingQueryBuilder q0 = DiscountTrackingQueryBuilder.get();
			q0.withHistoryStateType(DiscountLifeCycleStateType.Used);
			DiscountTrackingQueryBuilder q0bis = (DiscountTrackingQueryBuilder) q0.clone();
			//
			DiscountLifeCycleQueryBuilder q1 = DiscountLifeCycleQueryBuilder.get();
			DiscountLifeCycleRuleQueryBuilder q2 = DiscountLifeCycleRuleQueryBuilder.getNumber();
			q2.withMaxLessThan(q0);
			DiscountLifeCycleRuleQueryBuilder q3 = DiscountLifeCycleRuleQueryBuilder.getNumber();
			q3.withJoin(q1);
			//
			q1.withDisjunction();
			q1.withDisjunctionIdRuleIn(q2).withDisjunctionNotExists(q3).withDisjunctionNotExists(q0bis);
			q1.withIdProjection();
			context.put(DiscountLifeCycleRuleType.AbsoluteCount, q1);
		}
	}

	public void findUnavailable(DiscountLifeCycleContext context) {
		{
			DiscountTrackingQueryBuilder q0 = DiscountTrackingQueryBuilder.get();
			q0.withHistoryStateType(DiscountLifeCycleStateType.Used);
			//
			DiscountLifeCycleQueryBuilder q1 = DiscountLifeCycleQueryBuilder.get();
			DiscountLifeCycleRuleQueryBuilder q2 = DiscountLifeCycleRuleQueryBuilder.getNumber();
			q2.withMaxGeThan(q0);
			//
			q1.withDisjunction();
			q1.withDisjunctionIdRuleIn(q2);
			q1.withIdProjection();
			context.put(DiscountLifeCycleRuleType.AbsoluteCount, q1);
		}
	}

}
