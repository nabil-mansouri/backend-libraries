package com.rm.soa.discounts.subject.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.rules.subject.DiscountRuleSubject;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectAdditionnal;
import com.rm.model.discounts.rules.subject.DiscountRuleSubjectAdditionnalNode;
import com.rm.soa.discounts.subject.DiscountSubjectContext;
import com.rm.soa.discounts.subject.DiscountSubjectFinder;
import com.rm.utils.graphs.AbstractGraph;

/***
 * 
 * @author Nabil
 * 
 */
@Component(DiscountSubjectFinder.DiscountSubjectFinderAdditionnal)
@Qualifier(DiscountSubjectFinder.DiscountSubjectFinder)
public class DiscountSubjectFinderAdditionnal implements DiscountSubjectFinder {
	@Autowired
	protected SoaProductDefinition soaProductDefinition;

	public void find(DiscountSubjectContext context, CartBean cart, DiscountRuleSubject subject) {
		if (subject instanceof DiscountRuleSubjectAdditionnal) {
			DiscountRuleSubjectAdditionnal pri = (DiscountRuleSubjectAdditionnal) subject;
			//
			ConcurrentMap<Long, List<CartProductBean>> productById = new ConcurrentHashMap<Long, List<CartProductBean>>();
			for (CartProductBean row : cart.getDetails()) {
				productById.putIfAbsent(row.getProduct().getId(), new ArrayList<CartProductBean>());
				productById.get(row.getProduct().getId()).add(row);
			}
			//
			final List<AbstractGraph> rows = new ArrayList<AbstractGraph>();
			if (pri.isAll()) {
				// for (CartProductBean cartRow : cart.getDetails()) {
				// final GraphPathBuilder builder =
				// soaProductDefinition.getPathBuilder();
				// GraphIteratorBuilder.buildDeep().iterate(cartRow.getProduct(),
				// new IteratorListener() {
				//
				// public boolean stop() {
				// return false;
				// }
				//
				// public boolean onFounded(AbstractGraph node) {
				// rows.add(node);
				// return true;
				// }
				//
				// public boolean doSetParent() {
				// return true;
				// }
				// }, builder);
				// }
			} else {
				for (final DiscountRuleSubjectAdditionnalNode addnode : pri.getAdditionnal()) {
					if (productById.containsKey(addnode.getProductRoot())) {
						// List<CartProductBean> cartRows =
						// productById.get(addnode.getProductRoot());
						// for (CartProductBean cartRow : cartRows) {
						// final GraphPathBuilder builder =
						// soaProductDefinition.getPathBuilder();
						// GraphIteratorBuilder.buildDeep().iterate(cartRow.getProduct(),
						// new IteratorListener() {
						//
						// public boolean stop() {
						// return false;
						// }
						//
						// public boolean onFounded(AbstractGraph node) {
						// if
						// (StringUtils.equalsIgnoreCase(addnode.getPath(),
						// builder.getPath())) {
						// rows.add(node);
						// }
						// return true;
						// }
						//
						// public boolean doSetParent() {
						// return true;
						// }
						// }, builder);
						// }
					}
				}
			}
			//
			context.getFoundedNodes().addAll(rows);
		}
	}

}
