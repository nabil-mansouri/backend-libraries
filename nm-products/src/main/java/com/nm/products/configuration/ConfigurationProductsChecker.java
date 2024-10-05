package com.nm.products.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.products.soa.cache.ProductCacheBuilder;
import com.nm.products.soa.checker.ProductChecker;
import com.nm.products.soa.checker.ProductCheckerProcessor;
import com.nm.products.soa.checker.impl.ProductCheckerMandatoryPartImpl;
import com.nm.products.soa.checker.impl.ProductCheckerPartAbsentImpl;
import com.nm.products.soa.checker.impl.ProductCheckerPartBadSelectionImpl;
import com.nm.products.soa.checker.impl.ProductCheckerPartCardinalityImpl;
import com.nm.products.soa.checker.impl.ProductCheckerPartExistsImpl;
import com.nm.products.soa.checker.impl.ProductCheckerProductBadIngredientImpl;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationProductsChecker {

	public static final String ProductCheckerPartCardinality = "ProductChecker.ProductCheckerPartCardinality";
	public static final String ProductCheckerPartAbsent = "ProductChecker.ProductCheckerPartAbsent";
	public static final String ProductCheckerPartExists = "ProductChecker.ProductCheckerPartExists";
	public static final String ProductCheckerPartBadSelection = "ProductChecker.ProductCheckerPartBadSelection";
	public static final String ProductCheckerMandatoryPart = "ProductChecker.ProductCheckerMandatoryPart";
	public static final String ProductCheckerProductBadIngredients = "ProductChecker.ProductCheckerProductBadIngredients";

	@Bean(name = ProductCheckerMandatoryPart)
	public ProductChecker productCheckerMandatoryPartImpl() {
		return new ProductCheckerMandatoryPartImpl();
	}

	@Bean(name = ProductCheckerPartAbsent)
	public ProductChecker productCheckerPartAbsentImpl() {
		return new ProductCheckerPartAbsentImpl();
	}

	@Bean(name = ProductCheckerPartBadSelection)
	public ProductChecker productCheckerPartBadSelectionImpl() {
		return new ProductCheckerPartBadSelectionImpl();
	}

	@Bean(name = ProductCheckerPartCardinality)
	public ProductChecker productCheckerPartCardinalityImpl() {
		return new ProductCheckerPartCardinalityImpl();
	}

	@Bean(name = ProductCheckerPartExists)
	public ProductChecker productCheckerPartExistsImpl() {
		return new ProductCheckerPartExistsImpl();
	}

	@Bean(name = ProductCheckerProductBadIngredients)
	public ProductChecker productCheckerProductBadIngredientImpl() {
		return new ProductCheckerProductBadIngredientImpl();
	}

	@Bean()
	public ProductCheckerProcessor productCheckerProcessor(ProductCacheBuilder cache, //
			Map<String, ProductChecker> strategies) {
		ProductCheckerProcessor p = new ProductCheckerProcessor();
		p.setProductCacheBuilder(cache);
		p.setStrategies(strategies);
		return p;
	}

}
