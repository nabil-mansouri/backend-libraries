package com.nm.products.soa.checker.impl;

import com.nm.products.constants.ProductNodeType;
import com.nm.products.dtos.old.ProductCheckerContext;
import com.nm.products.soa.checker.ProductChecker;
import com.nm.utils.graphs.AbstractGraph;

/**
 * Check if there are absent part
 * 
 * @author Nabil
 * 
 */
public class ProductCheckerPartCardinalityImpl implements ProductChecker {

	public void check(ProductCheckerContext context, AbstractGraph node, String path) {
		if (node.nodetype().equals(ProductNodeType.PART.name())) {
			// ProductPartViewDto part = (ProductPartViewDto) node;
			// int card = context.decrementCardinality(path);
			// if (card < 0) {
			// node.getParent().removeChild(part);
			// context.getBadPartCardinality().put(path, part);
			// }
		}
	}
}
