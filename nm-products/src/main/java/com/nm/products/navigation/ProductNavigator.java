package com.nm.products.navigation;

import com.nm.products.dtos.navigation.NavigationBean;
import com.nm.products.dtos.navigation.NavigationException;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface ProductNavigator {
	public NavigationBean init(ProductDefinition product) throws NoDataFoundException;

	public void push(NavigationBean navigation, ProductDefinition product)
			throws NoDataFoundException, NavigationException;

	public void push(NavigationBean navigation, IngredientDefinition ing)
			throws NoDataFoundException, NavigationException;

	public void skip(NavigationBean navigation) throws NoDataFoundException, NavigationException;

	public boolean backTo(NavigationBean navigation, String idHead) throws NoDataFoundException;

}
