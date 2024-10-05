package com.rm.soa.discounts.subject;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.rules.subject.DiscountRuleSubject;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DiscountSubjectProcessor {

	@Autowired
	@Qualifier(DiscountSubjectFinder.DiscountSubjectFinder)
	private Map<String, DiscountSubjectFinder> strategies = new HashMap<String, DiscountSubjectFinder>();
	protected Collection<String> getStrategies() {
		return Arrays.asList(DiscountSubjectFinder.DiscountSubjectFinderProduct,//
				DiscountSubjectFinder.DiscountSubjectFinderTax,//
				DiscountSubjectFinder.DiscountSubjectFinderAdditionnal);
	}

	public DiscountSubjectContext find(CartBean cart, DiscountRuleSubject subject) {
		DiscountSubjectContext context = new DiscountSubjectContext();
		return find(context, cart, subject);
	}

	public DiscountSubjectContext find(DiscountSubjectContext context, CartBean cartBean, DiscountRuleSubject subject) {
		for (String s : getStrategies()) {
			strategies.get(s).find(context, cartBean, subject);
		}
		return context;
	}

}
