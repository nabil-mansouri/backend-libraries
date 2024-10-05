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
public class ProductCheckerPartAbsentImpl implements ProductChecker {

	public void check(ProductCheckerContext context, AbstractGraph node, String path) {
		if (node.nodetype().equals(ProductNodeType.PART.name())) {
			context.getPartsAbsent().remove(path);
		}
	}
}
