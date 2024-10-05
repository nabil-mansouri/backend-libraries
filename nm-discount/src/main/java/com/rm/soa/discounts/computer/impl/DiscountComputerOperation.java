package com.rm.soa.discounts.computer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.rules.DiscountRule;
import com.rm.model.discounts.rules.DiscountRuleFree;
import com.rm.model.discounts.rules.DiscountRuleOperation;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectOrder;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectProduct;
import com.rm.soa.discounts.computer.DiscountComputer;
import com.rm.soa.discounts.computer.DiscountComputerContext;
import com.rm.soa.discounts.subject.DiscountSubjectContext;
import com.rm.soa.discounts.subject.DiscountSubjectProcessor;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountComputer.DiscountComputerOperation)
@Qualifier(DiscountComputer.DiscountComputer)
public class DiscountComputerOperation implements DiscountComputer {
	@Autowired
	private DiscountSubjectProcessor discountSubjectProcessor;

	public void compute(DiscountComputerContext context, CartBean cart, DiscountDefinition discount) {
		for (DiscountRule rule : discount.getRule().getRules()) {
			if (rule instanceof DiscountRuleFree) {
				DiscountRuleOperation op = (DiscountRuleOperation) rule;
				if (op.getSubject() instanceof DiscountRuleSubjectOrder) {
					cart.getComputed().setInitialVal(cart.getTotal());
					compute(cart.getComputed(), op);
				} else if (op.getSubject() instanceof DiscountRuleSubjectProduct) {
					DiscountSubjectContext founded = discountSubjectProcessor.find(cart, op.getSubject());
					for (CartProductBean r : founded.getFoundedRows()) {
						r.getComputed().setInitialVal(r.getSubTotal());
						compute(r.getComputed(), op);
					}
				}
			}
		}
	}

	protected void compute(ComputeDetailBean compute, DiscountRuleOperation rule) {
		switch (rule.getOperation()) {
		case Fixe: {
			Double total = compute.getInitialVal() - rule.getValue();
			ComputeDetailRowBean row = new ComputeDetailRowBean();
			row.setValue(rule.getValue());
			row.setOperation(PriceOperationType.Substract);
			compute.getDetails().add(row);
			//
			compute.setFinalVal(total);
			break;
		}
		case Percentage: {
			Double diff = compute.getInitialVal()* rule.getValue();
			ComputeDetailRowBean row = new ComputeDetailRowBean();
			row.setValue(diff);
			row.setOperation(PriceOperationType.Substract);
			compute.getDetails().add(row);
			//
			Double total = compute.getInitialVal() - diff;
			compute.setFinalVal(total);
			break;
		}
		}
	}
}
