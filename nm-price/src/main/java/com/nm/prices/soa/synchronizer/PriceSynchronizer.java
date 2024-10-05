package com.nm.prices.soa.synchronizer;

import com.nm.prices.dtos.forms.old.PriceSynchronizerContext;
import com.nm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
@Deprecated
public abstract class PriceSynchronizer {

	public static final String PriceSynchronizer = "PriceSynchronizer";
	public static final String PriceSynchronizerSelected = "PriceSynchronizer.PriceSynchronizerSelected";
	public static final String PriceSynchronizerDefinition = "PriceSynchronizer.PriceSynchronizerDefinition";

	public abstract void synchronyze(PriceSynchronizerContext context, AbstractGraph node, String path);

	// protected void setPrice(PriceSynchronizerContext context, ProductViewDto
	// product) {
	// setPrice(context, product, new PriceComputeBean(),false);
	// }
	//
	// protected void setPrice(PriceSynchronizerContext context, ProductViewDto
	// product, PriceComputeBean price, boolean addTotal) {
	// if (!Objects.equals(product.getPrice(), price.getValue())) {
	// context.getChanged().add(product);
	// }
	// product.setPrice(price.getValue());
	// if (addTotal) {
	// context.addTotal(price.getValue());
	// }
	// }
}
