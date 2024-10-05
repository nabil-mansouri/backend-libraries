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
public class ProductCheckerMandatoryPartImpl implements ProductChecker {

	public void check(ProductCheckerContext context, AbstractGraph node, String path) {
		if (node.nodetype().equals(ProductNodeType.PART.name())) {
			// ProductPartViewDto part = (ProductPartViewDto) node;
			// if (part.isMandatory() && !part.hasSelected()) {
			// context.getPartsMandatory().put(path, part);
			// }
		}
	}
}
