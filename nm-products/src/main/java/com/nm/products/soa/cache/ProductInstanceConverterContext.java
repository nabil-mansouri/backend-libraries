package com.nm.products.soa.cache;

import java.util.HashMap;
import java.util.Map;

import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.products.model.ProductInstance;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductInstanceConverterContext {
	private final ProductDefinitionCache cache;
	private Map<String, ProductInstance> productByPath = new HashMap<String, ProductInstance>();
	private ProductInstance root;

	public ProductInstanceConverterContext(ProductDefinitionCache cache) {
		this.cache = cache;
	}

	public ProductInstance getRoot() {
		return root;
	}

	public void setRoot(ProductInstance root) {
		this.root = root;
	}

	public ProductDefinitionCache getCache() {
		return cache;
	}

	public Map<String, ProductInstance> getProductByPath() {
		return productByPath;
	}

	public void setProductByPath(Map<String, ProductInstance> productByPath) {
		this.productByPath = productByPath;
	}

	public ProductDefinition getProductsById(Long id) {
		return cache.getProductsById().get(id);
	}

	public ProductInstance getProductByPath(String path) {
		return this.productByPath.get(path);
	}

	public ProductDefinitionPart getPartsById(Long id) {
		return this.cache.getPartsById().get(id);
	}

	public void put(String path, ProductInstance instance) {
		this.productByPath.put(path, instance);
	}

	public IngredientDefinition getIngById(Long id) {
		return this.cache.getIngById().get(id);
	}
}
