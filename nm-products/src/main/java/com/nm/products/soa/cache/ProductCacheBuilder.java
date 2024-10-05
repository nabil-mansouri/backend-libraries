package com.nm.products.soa.cache;

import com.nm.products.dtos.old.ProductDefCache;
import com.nm.products.dtos.views.ProductViewDto;
/**
 * 
 * @author Nabil
 *
 */
public interface ProductCacheBuilder {

	public ProductDefinitionCache buildFromId(ProductViewDto node);

	public ProductDefCache buildFromPath(ProductViewDto reference);

}