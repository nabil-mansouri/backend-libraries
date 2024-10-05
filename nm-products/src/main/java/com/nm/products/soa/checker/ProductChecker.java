package com.nm.products.soa.checker;

import com.nm.products.dtos.old.ProductCheckerContext;
import com.nm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
public interface ProductChecker {
	public void check(ProductCheckerContext context, AbstractGraph node, String path);
}
