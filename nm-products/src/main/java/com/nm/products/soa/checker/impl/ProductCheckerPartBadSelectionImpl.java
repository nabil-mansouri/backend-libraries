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
public class ProductCheckerPartBadSelectionImpl implements ProductChecker {

	public void check(ProductCheckerContext context, AbstractGraph node, String path) {
		if (node.nodetype().equals(ProductNodeType.PART.name())) {
			// Test only if part exists
			// if (context.existsPart(path)) {
			// ProductPartViewDto part = (ProductPartViewDto) node;
			// if (part.hasSelected()) {
			// GraphPathBuilder builder = soaProductDefinition.getPathBuilder();
			// builder.parse(path);
			// builder.down(part.getSelected());
			// if (!context.existsProduct(builder.getPath())) {
			// context.getProductBadSelection().add(new
			// ProductCheckerContextProductSelection(part, part.getSelected()));
			// part.setSelected(null);
			// }
			// }
			// }

		}
	}
}
