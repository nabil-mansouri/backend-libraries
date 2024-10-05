package com.rm.soa.discounts.subject.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.rules.subject.DiscountRuleSubject;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectTax;
import com.rm.soa.discounts.subject.DiscountSubjectContext;
import com.rm.soa.discounts.subject.DiscountSubjectFinder;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountSubjectFinder.DiscountSubjectFinderTax)
@Qualifier(DiscountSubjectFinder.DiscountSubjectFinder)
public class DiscountSubjectFinderTax implements DiscountSubjectFinder {

	public void find(DiscountSubjectContext context, CartBean cart, DiscountRuleSubject subject) {
		if (subject instanceof DiscountRuleSubjectTax) {
			DiscountRuleSubjectTax pri = (DiscountRuleSubjectTax) subject;
			//
			ConcurrentMap<Long, List<TaxDefFormBean>> taxById = new ConcurrentHashMap<Long, List<TaxDefFormBean>>();
			for (TaxDefFormBean tax : cart.getTaxs()) {
				taxById.putIfAbsent(tax.getId(), new ArrayList<TaxDefFormBean>());
				taxById.get(tax.getId()).add(tax);
			}
			//
			Collection<TaxDefFormBean> taxs = cart.getTaxs();
			if (!pri.isAll()) {
				taxs.clear();
				for (TaxDefinition taxId : pri.getTaxs()) {
					if (taxById.containsKey(taxId.getId())) {
						taxs.addAll(taxById.get(taxId.getId()));
					}
				}
			}
			//
			context.getFoundedTax().addAll(taxs);
		}
	}

}
