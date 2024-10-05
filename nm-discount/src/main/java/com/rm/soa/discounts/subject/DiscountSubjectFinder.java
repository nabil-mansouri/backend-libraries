package com.rm.soa.discounts.subject;

import com.rm.model.discounts.rules.subject.DiscountRuleSubject;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountSubjectFinder {
	public static final String DiscountSubjectFinder = "DiscountSubjectFinder";
	public static final String DiscountSubjectFinderTax = "DiscountSubjectFinder.DiscountSubjectFinderTax";
	public static final String DiscountSubjectFinderProduct = "DiscountSubjectFinder.DiscountSubjectFinderProduct";
	public static final String DiscountSubjectFinderAdditionnal = "DiscountSubjectFinder.DiscountSubjectFinderAdditionnal";

	public void find(DiscountSubjectContext context, CartBean cart, DiscountRuleSubject subject);
}
