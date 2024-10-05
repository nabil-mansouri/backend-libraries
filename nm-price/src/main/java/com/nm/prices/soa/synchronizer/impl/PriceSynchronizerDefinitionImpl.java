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
public class PriceSynchronizerDefinitionImpl extends PriceSynchronizer {

	@Override
	public void synchronyze(PriceSynchronizerContext context, AbstractGraph node, String path) {
		// ProductNodeBean nodePrice = (ProductNodeBean) node;
		// TODO
		// if (context.existsPrice(path)) {
		// PriceComputeBean price = context.getPrice(path);
		// if (nodePrice.nodetype().equals(ProductNodeType.PRODUCT.name())) {
		// setPrice(context, (ProductViewDto) nodePrice, price,false);
		// } else if (nodePrice.nodetype().equals(ProductNodeType.PART.name()))
		// {
		// for (AbstractGraph child : nodePrice.children()) {
		// setPrice(context, (ProductViewDto) child, price,false);
		// }
		// }
		// } else if
		// (nodePrice.nodetype().equals(ProductNodeType.PRODUCT.name())) {
		// setPrice(context, (ProductViewDto) nodePrice);
		// }
	}

}
