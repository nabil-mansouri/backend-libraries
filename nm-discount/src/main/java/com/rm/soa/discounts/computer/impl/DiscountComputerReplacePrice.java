package com.rm.soa.discounts.computer.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.rules.DiscountRule;
import com.rm.model.discounts.rules.DiscountRuleReplacePrice;
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
@Component(DiscountComputer.DiscountComputerReplacePrice)
@Qualifier(DiscountComputer.DiscountComputer)
public class DiscountComputerReplacePrice implements DiscountComputer {
	@Autowired
	private DiscountSubjectProcessor discountSubjectProcessor;
	@Autowired
	private OldPriceComputerProcessor priceComputer;
	@Autowired
	protected PriceSynchronizerProcessor synchronizerProcessor;

	public void compute(DiscountComputerContext context, CartBean cart, DiscountDefinition discount) {
		for (DiscountRule rule : discount.getRule().getRules()) {
			if (rule instanceof DiscountRuleReplacePrice) {
				DiscountRuleReplacePrice replacePrice = (DiscountRuleReplacePrice) rule;
				// Fake subject
				DiscountRuleSubjectProduct fakeSubject = new DiscountRuleSubjectProduct();
				Map<Long, Price> pricesByProduct = new HashMap<Long, Price>();
				for (Price price : replacePrice.getPrices()) {
					if (price.getSubject() instanceof PriceSubjectProductDiscount) {
						PriceSubjectProductDiscount sub = (PriceSubjectProductDiscount) price.getSubject();
						fakeSubject.getProducts().add(sub.getProduct());
						pricesByProduct.put(sub.getProduct().getId(), price);
					}
				}
				//
				DiscountSubjectContext founded = discountSubjectProcessor.find(cart, fakeSubject);
				for (CartProductBean row : founded.getFoundedRows()) {
					// Synchronize product with discount price
					if (pricesByProduct.containsKey(row.getProduct().getId())) {
						Price price = pricesByProduct.get(row.getProduct().getId());
						PriceComputeBean bean = new PriceComputeBean();
						bean.setPriceType(cart.getType());
						priceComputer.compute(bean, price);
						// synchronizerProcessor.process(bean,
						// row.getProduct());
					}
				}
			}
		}
	}
}
