package com.rm.soa.discounts.subject.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.rules.subject.DiscountRuleSubject;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectProduct;
import com.rm.soa.discounts.subject.DiscountSubjectContext;
import com.rm.soa.discounts.subject.DiscountSubjectFinder;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountSubjectFinder.DiscountSubjectFinderProduct)
@Qualifier(DiscountSubjectFinder.DiscountSubjectFinder)
public class DiscountSubjectFinderProduct implements DiscountSubjectFinder {

	public void find(DiscountSubjectContext context, CartBean cart, DiscountRuleSubject subject) {
		if (subject instanceof DiscountRuleSubjectProduct) {
			DiscountRuleSubjectProduct pri = (DiscountRuleSubjectProduct) subject;
			//
			ConcurrentMap<Long, List<CartProductBean>> productById = new ConcurrentHashMap<Long, List<CartProductBean>>();
			for (CartProductBean row : cart.getDetails()) {
				productById.putIfAbsent(row.getProduct().getId(), new ArrayList<CartProductBean>());
				productById.get(row.getProduct().getId()).add(row);
			}
			//
			List<CartProductBean> rows = cart.getDetails();
			if (!pri.isAll()) {
				rows.clear();
				for (ProductDefinition product : pri.getProducts()) {
					if (productById.containsKey(product.getId())) {
						rows.addAll(productById.get(product.getId()));
					}
				}
			}
			//
			context.getFoundedRows().addAll(rows);
		}
	}

}
