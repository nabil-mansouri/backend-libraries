package com.nm.products.soa.checker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nm.products.configuration.ConfigurationProductsChecker;
import com.nm.products.dtos.old.ProductCheckerContext;
import com.nm.products.dtos.old.ProductDefCache;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.soa.cache.ProductCacheBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductCheckerProcessor {
	private ProductCacheBuilder productCacheBuilder;
	protected Map<String, ProductChecker> strategies = new HashMap<String, ProductChecker>();

	public void setProductCacheBuilder(ProductCacheBuilder productCacheBuilder) {
		this.productCacheBuilder = productCacheBuilder;
	}

	public void setStrategies(Map<String, ProductChecker> strategies) {
		this.strategies = strategies;
	}

	public ProductCheckerContext process(ProductViewDto reference, ProductViewDto test) {
		// MandatoryPart MUST BE BEFORE PartBadSelction
		return process(reference, test,
				Arrays.asList(ConfigurationProductsChecker.ProductCheckerPartExists,
						ConfigurationProductsChecker.ProductCheckerPartAbsent,
						ConfigurationProductsChecker.ProductCheckerMandatoryPart,
						ConfigurationProductsChecker.ProductCheckerPartBadSelection,
						ConfigurationProductsChecker.ProductCheckerProductBadIngredients,
						ConfigurationProductsChecker.ProductCheckerPartCardinality));
	}

	public ProductCheckerContext process(ProductViewDto reference, ProductViewDto test, final List<String> strategies) {
		ProductDefCache cache = productCacheBuilder.buildFromPath(reference);
		final ProductCheckerContext context = new ProductCheckerContext(cache);
		//
		// final GraphPathBuilder builder =
		// soaProductDefinition.getPathBuilder();
		// GraphIteratorBuilder.buildDeep().iterate(test, new IteratorListener()
		// {
		//
		// public boolean stop() {
		// return false;
		// }
		//
		// public boolean onFounded(AbstractGraph node) {
		// for (String strategy : strategies) {
		// ProductChecker checker =
		// ProductCheckerProcessor.this.strategies.get(strategy);
		// checker.check(context, node, builder.getPath());
		// }
		// return true;
		// }
		//
		// public boolean doSetParent() {
		// return true;
		// }
		// }, builder);
		context.computeFlag();
		return context;
	}
}
