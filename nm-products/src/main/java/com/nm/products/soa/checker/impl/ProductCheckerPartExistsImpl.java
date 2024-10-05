package com.nm.products.soa.checker.impl;

import com.nm.products.constants.ProductNodeType;
import com.nm.products.dtos.old.ProductCheckerContext;
import com.nm.products.soa.checker.ProductChecker;
import com.nm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductCheckerPartExistsImpl implements ProductChecker {

	public void check(ProductCheckerContext context, AbstractGraph node, String path) {
		if (node.nodetype().equals(ProductNodeType.PART.name())) {
			if (!context.existsPart(path)) {
				node.getParent().removeChild(node);
				// context.getNotExistsPartAnyMore().add((ProductPartViewDto)
				// node);
			}
		}
	}
}
