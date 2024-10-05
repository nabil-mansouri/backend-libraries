package com.rm.soa.discounts.computer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.rules.DiscountRule;
import com.rm.model.discounts.rules.DiscountRuleFree;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectAdditionnal;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectOrder;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectProduct;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectTax;
import com.rm.soa.discounts.computer.DiscountComputer;
import com.rm.soa.discounts.computer.DiscountComputerContext;
import com.rm.soa.discounts.subject.DiscountSubjectContext;
import com.rm.soa.discounts.subject.DiscountSubjectProcessor;
import com.rm.utils.graphs.AbstractGraph;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountComputer.DiscountComputerFree)
@Qualifier(DiscountComputer.DiscountComputer)
public class DiscountComputerFree implements DiscountComputer {
	@Autowired
	private DiscountSubjectProcessor discountSubjectProcessor;

	// TODO refactor du cartbean pour mieux g�rer les calculs
	public void compute(DiscountComputerContext context, CartBean cart, DiscountDefinition discount) {
		for (DiscountRule rule : discount.getRule().getRules()) {
			if (rule instanceof DiscountRuleFree) {
				DiscountRuleFree free = (DiscountRuleFree) rule;
				if (free.getSubject() instanceof DiscountRuleSubjectAdditionnal) {
					DiscountRuleSubjectAdditionnal add = (DiscountRuleSubjectAdditionnal) free.getSubject();
					DiscountSubjectContext founded = discountSubjectProcessor.find(cart, add);
					for (AbstractGraph node : founded.getFoundedNodes()) {
						if (node instanceof ProductInstanceDto) {
							ProductInstanceDto p = (ProductInstanceDto) node;
							p.getComputed().setInitialVal(p.getPrice());
							p.getComputed().setFinalVal(0d);
						} else if (node instanceof ProductPartInstanceDto) {
							// NOT AVAILABLE FOR PART
						}
					}
				} else if (free.getSubject() instanceof DiscountRuleSubjectOrder) {
					cart.getComputed().setInitialVal(cart.getTotal());
					cart.getComputed().setFinalVal(0d);
				} else if (free.getSubject() instanceof DiscountRuleSubjectProduct) {
					DiscountRuleSubjectProduct pri = (DiscountRuleSubjectProduct) free.getSubject();
					DiscountSubjectContext founded = discountSubjectProcessor.find(cart, pri);
					for (CartProductBean r : founded.getFoundedRows()) {
						r.getComputed().setInitialVal(r.getSubTotal());
						r.getComputed().setFinalVal(0d);
					}
				} else if (free.getSubject() instanceof DiscountRuleSubjectTax) {
					DiscountRuleSubjectTax pri = (DiscountRuleSubjectTax) free.getSubject();
					DiscountSubjectContext founded = discountSubjectProcessor.find(cart, pri);
					for (TaxDefFormBean r : founded.getFoundedTax()) {
						r.getComputed().setFinalVal(0d);
					}
				}
			}
		}
	}
}
