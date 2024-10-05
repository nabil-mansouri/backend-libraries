package com.rm.soa.discounts.computer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.rules.DiscountRule;
import com.rm.model.discounts.rules.DiscountRuleGift;
import com.rm.soa.discounts.computer.DiscountComputer;
import com.rm.soa.discounts.computer.DiscountComputerContext;
import com.rm.soa.discounts.subject.DiscountSubjectContext;
import com.rm.soa.discounts.subject.DiscountSubjectProcessor;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountComputer.DiscountComputerGift)
@Qualifier(DiscountComputer.DiscountComputer)
public class DiscountComputerGift implements DiscountComputer {
	@Autowired
	private DiscountSubjectProcessor discountSubjectProcessor;

	public void compute(DiscountComputerContext context, CartBean cart, DiscountDefinition discount) {
		for (DiscountRule rule : discount.getRule().getRules()) {
			if (rule instanceof DiscountRuleGift) {
				DiscountRuleGift gift = (DiscountRuleGift) rule;
				DiscountSubjectContext founded = discountSubjectProcessor.find(cart, gift.getSubject());
				for (CartProductBean r : founded.getFoundedRows()) {
					r.getComputed().setInitialVal(r.getSubTotal());
					r.getComputed().setFinalVal(0d);
				}
			}
		}
	}
}
