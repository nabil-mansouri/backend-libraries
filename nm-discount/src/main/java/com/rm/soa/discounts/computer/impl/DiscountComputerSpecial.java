package com.rm.soa.discounts.computer.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.rules.DiscountRule;
import com.rm.model.discounts.rules.DiscountRuleSpecial;
import com.rm.soa.discounts.computer.DiscountComputer;
import com.rm.soa.discounts.computer.DiscountComputerContext;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountComputer.DiscountComputerSpecial)
@Qualifier(DiscountComputer.DiscountComputer)
public class DiscountComputerSpecial implements DiscountComputer {
	public void compute(DiscountComputerContext context, CartBean cart, DiscountDefinition discount) {
		for (DiscountRule rule : discount.getRule().getRules()) {
			if (rule instanceof DiscountRuleSpecial) {
				// DO NOTHING
			}
		}
	}
}
