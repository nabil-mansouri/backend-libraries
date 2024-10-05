package com.rm.soa.discounts.lifecycle.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleQueryBuilder;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleRuleQueryBuilder;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleComputer;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountLifeCycleComputer.DiscountLifeCycleComputerDuration)
@Qualifier(DiscountLifeCycleComputer.DiscountLifeCycleComputer)
public class DiscountLifeCycleComputerDuration implements DiscountLifeCycleComputer {

	public void findAvailable(DiscountLifeCycleContext context) {
		{
			DiscountLifeCycleQueryBuilder q0 = DiscountLifeCycleQueryBuilder.get();
			//
			DiscountLifeCycleQueryBuilder q1 = DiscountLifeCycleQueryBuilder.get().withStateType(DiscountLifeCycleStateType.ReadyToUse);
			{
				DiscountLifeCycleRuleQueryBuilder q2 = DiscountLifeCycleRuleQueryBuilder.getPeriod();
				q2.withJoin(q1);
				q2.withLimitLe(q1);
				q1.withExists(q2);
			}
			//
			DiscountLifeCycleRuleQueryBuilder q3 = DiscountLifeCycleRuleQueryBuilder.getPeriod();
			q3.withJoin(q0);
			//
			DiscountLifeCycleQueryBuilder q4 = DiscountLifeCycleQueryBuilder.get().withStateType(DiscountLifeCycleStateType.ReadyToUse);
			//
			q0.withDisjunction();
			q0.withDisjunctionIdIn(q1).withDisjunctionNotExists(q3).withDisjunctionIdNotIn(q4);
			q0.withIdProjection();
			context.put(DiscountLifeCycleRuleType.Duration, q0);
		}
	}

	public void findUnavailable(DiscountLifeCycleContext context) {
		{
			DiscountLifeCycleQueryBuilder q0 = DiscountLifeCycleQueryBuilder.get();
			//
			DiscountLifeCycleQueryBuilder q1 = DiscountLifeCycleQueryBuilder.get().withStateType(DiscountLifeCycleStateType.ReadyToUse);
			{
				DiscountLifeCycleRuleQueryBuilder q2 = DiscountLifeCycleRuleQueryBuilder.getPeriod();
				q2.withJoin(q1);
				q2.withLimitGt(q1);
				q1.withExists(q2);
			}
			//
			q0.withDisjunction();
			q0.withDisjunctionIdIn(q1);
			q0.withIdProjection();
			context.put(DiscountLifeCycleRuleType.Duration, q0);
		}
	}

}
