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
public class ProductCheckerProductBadIngredientImpl implements ProductChecker {

	public void check(ProductCheckerContext context, AbstractGraph node, String path) {
		if (node.nodetype().equals(ProductNodeType.PART.name())) {
			// ProductPartViewDto part = (ProductPartViewDto) node;
			// if (part.hasSelected()) {
			// ProductViewDto product = part.getSelected();
			// if (!product.getExcluded().isEmpty()) {
			// GraphPathBuilder builder = soaProductDefinition.getPathBuilder();
			// builder.parse(path);
			// builder.down(product);
			// Set<IngredientViewDto> toRemove = new
			// HashSet<IngredientViewDto>();
			// for (IngredientViewDto ing : product.getExcluded()) {
			// builder.down(ing);
			// if (!context.existsIngredient(builder.getPath())) {
			// context.getIngredientBadSelection().add(new
			// ProductCheckerContextIngredientSelection(ing, product));
			// toRemove.add(ing);
			// }
			// builder.up(1);
			// }
			// product.getExcluded().removeAll(toRemove);
			// }
			// }
		}
	}
}
