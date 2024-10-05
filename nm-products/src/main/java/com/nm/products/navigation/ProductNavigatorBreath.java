package com.nm.products.navigation;

import com.google.common.base.Objects;
import com.nm.products.constants.NavigationBodyState;
import com.nm.products.constants.NavigationHeadState;
import com.nm.products.constants.NavigationState;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dtos.navigation.NavigationBean;
import com.nm.products.dtos.navigation.NavigationException;
import com.nm.products.dtos.navigation.NavigationHeadItem;
import com.nm.products.dtos.navigation.NavigationNode;
import com.nm.products.dtos.navigation.NavigationPath;
import com.nm.products.dtos.navigation.NavigationStack;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ProductNavigatorBreath implements ProductNavigator {
	private ProductDefinitionViewConverter viewConverter;
	private ProductNavigationProcessor processor;

	public void setProcessor(ProductNavigationProcessor processor) {
		this.processor = processor;
	}

	public void setViewConverter(ProductDefinitionViewConverter viewConverter) {
		this.viewConverter = viewConverter;
	}

	public NavigationBean init(ProductDefinition product) throws NoDataFoundException {
		NavigationBean nav = new NavigationBean();
		nav.setState(NavigationState.Begin);
		nav.setRoot(viewConverter.toDto(product, new OptionsList()));
		//
		NavigationNode node = nav.createNode();
		NavigationStack stack = node.getHead().createStack();
		NavigationHeadItem item = stack.createItem();
		item.setRoot(true);
		item.setState(NavigationHeadState.Current);
		item.setProduct(nav.getRoot());
		node.getBody().setProduct(item.getProduct());
		node.getBody().setRequired(true);
		processor.process(node);
		return nav;
	}

	public void push(NavigationBean navigation, ProductDefinition product)
			throws NoDataFoundException, NavigationException {
		if (!navigation.getCurrent().getBody().containsProduct(product.getId())) {
			throw new NavigationException("Could not found product :" + product);
		}
		NavigationNode current = navigation.getCurrent();
		current.getBody().setProduct(viewConverter.toDto(product, new OptionsList()));
		if (!processor.helper().hasIngredients(current.getBody())) {
			current.getBody().setState(NavigationBodyState.Commit);
		}
		updateHistory(navigation, current);
	}

	public void push(NavigationBean navigation, IngredientDefinition ing)
			throws NoDataFoundException, NavigationException {
		if (!navigation.getCurrent().getBody().containsIngredient(ing.getId())) {
			throw new NavigationException("Could not found ingredient :" + ing);
		}
		NavigationNode current = navigation.getCurrent();
		current.getBody().setIngredient(viewConverter.toDto(ing, new OptionsList()));
		current.getBody().setState(NavigationBodyState.Commit);
		updateHistory(navigation, current);
	}

	public void skip(NavigationBean navigation) throws NoDataFoundException, NavigationException {
		if (navigation.getCurrent().getBody().getProduct() == null && navigation.getCurrent().getBody().isRequired()) {
			throw new NavigationException("Could not skip required item");
		}
		NavigationNode current = navigation.getCurrent();
		current.getBody().setState(NavigationBodyState.Commit);
		updateHistory(navigation, current);
	}

	protected void updateHistory(NavigationBean navigation, NavigationNode current) throws NoDataFoundException {
		{
			NavigationPath currentP = navigation.getCurrent().find(NavigationHeadState.Current);
			NavigationPath last = navigation.getLast().find(NavigationHeadState.Current);
			if (currentP.same(last)) {
			} else {
				// Remove all node after this current
				boolean founded = false;
				while (!navigation.getHistory().isEmpty() && !founded) {
					NavigationPath temp = navigation.getHistory().getLast().find(NavigationHeadState.Current);
					if (currentP.same(temp)) {
						founded = true;
					} else {
						navigation.getHistory().removeLast();
					}
				}
			}
		}
		// Update body or head
		NavigationNode clone = current.clone();
		processor.process(clone);
		// If navigation not ended-> push new or replace updated
		if (Objects.equal(clone.getState(), NavigationState.Commit)) {
			navigation.push(clone);
		} else {
			NavigationPath before = navigation.getCurrent().find(NavigationHeadState.Current);
			NavigationPath after = clone.find(NavigationHeadState.Current);
			if (before.same(after)) {
				navigation.replace(clone);
			} else {
				navigation.push(clone);
			}
		}
	}

	public boolean backTo(NavigationBean navigation, String idHead) {
		for (NavigationNode node : navigation.getHistory()) {
			NavigationPath path = node.find(NavigationHeadState.Current);
			if (path.founded()) {
				if (path.getItem().getId().equals(idHead)) {
					navigation.setCurrent(node);
					return true;
				}
			}
		}
		return false;
	}

}
