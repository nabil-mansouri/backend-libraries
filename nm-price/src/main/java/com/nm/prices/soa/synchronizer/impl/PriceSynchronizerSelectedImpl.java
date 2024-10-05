package com.nm.prices.soa.synchronizer.impl;

import com.nm.prices.dtos.forms.old.PriceSynchronizerContext;
import com.nm.prices.soa.synchronizer.PriceSynchronizer;
import com.nm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
@Deprecated
public class PriceSynchronizerSelectedImpl extends PriceSynchronizer {
	// @Autowired
	// protected SoaProductDefinition soaProductDefinition;

	public void synchronyze(PriceSynchronizerContext context, AbstractGraph node, String path) {
		// if (node.nodetype().equals(ProductNodeType.PART.name())) {
		// ProductPartViewDto part = (ProductPartViewDto) node;
		// if (part.hasSelected()) {
		// GraphPathBuilder pathBuilder =
		// soaProductDefinition.getPathBuilder();
		// ProductViewDto product = part.getSelected();
		// pathBuilder.parse(path);
		// pathBuilder.down(product);
		// String productPath = pathBuilder.getPath();
		// // Get product price
		// if (context.existsPrice(productPath)) {
		// PriceComputeBean price = context.getPrice(productPath);
		// setPrice(context, product, price, true);
		// }
		// // Get part price
		// else if (context.existsPrice(path)) {
		// PriceComputeBean price = context.getPrice(path);
		// setPrice(context, product, price, true);
		// } else {
		// setPrice(context, product);
		// }
		// }
		// } else if (node.nodetype().equals(ProductNodeType.PRODUCT.name())) {
		// if (context.existsPrice(path) && node.root()) {
		// PriceComputeBean price = context.getPrice(path);
		// setPrice(context, (ProductViewDto) node, price, true);
		// }
		// }
	}

}
