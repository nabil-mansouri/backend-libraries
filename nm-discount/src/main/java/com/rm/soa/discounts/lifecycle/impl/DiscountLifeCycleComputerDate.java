package com.rm.soa.discounts.lifecycle.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.contract.discounts.constants.DiscountLifeCycleRuleType;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleQueryBuilder;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleRuleQueryBuilder;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleComputer;
import com.rm.soa.discounts.lifecycle.DiscountLifeCycleContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountLifeCycleComputer.DiscountLifeCycleComputerDate)
@Qualifier(DiscountLifeCycleComputer.DiscountLifeCycleComputer)
public class DiscountLifeCycleComputerDate implements DiscountLifeCycleComputer {

	public void findAvailable(DiscountLifeCycleContext context) {
		{
			DiscountLifeCycleQueryBuilder q1 = DiscountLifeCycleQueryBuilder.get();
			DiscountLifeCycleRuleQueryBuilder q2 = DiscountLifeCycleRuleQueryBuilder.getDate();
			q2.withDisjunction();
			Date relative = context.getRelativeTo();
			q2.withDisjunctionDateBetween(relative)//
					.withDisjunctionDateEq(relative)//
					.withDisjunctionDateFrom(relative)//
					.withDisjunctionDateTo(relative);
			DiscountLifeCycleRuleQueryBuilder q3 = DiscountLifeCycleRuleQueryBuilder.getDate();
			q3.withJoin(q1);
			//
			q1.withDisjunction();
			q1.withDisjunctionIdRuleIn(q2).withDisjunctionNotExists(q3);
			q1.withIdProjection();
			context.put(DiscountLifeCycleRuleType.Date, q1);
		}
	}

	public void findUnavailable(DiscountLifeCycleContext context) {
		{
			DiscountLifeCycleQueryBuilder q1 = DiscountLifeCycleQueryBuilder.get();
			DiscountLifeCycleRuleQueryBuilder q2 = DiscountLifeCycleRuleQueryBuilder.getDate();

			Date relative = context.getRelativeTo();
			q2.withDateAfter(relative);
			//
			q1.withDisjunction();
			q1.withDisjunctionIdRuleIn(q2);
			q1.withIdProjection();
			context.put(DiscountLifeCycleRuleType.Date, q1);
		}
	}

}
