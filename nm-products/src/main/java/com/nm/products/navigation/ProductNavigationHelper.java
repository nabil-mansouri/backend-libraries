package com.nm.products.navigation;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.products.constants.NavigationBodyState;
import com.nm.products.constants.NavigationHeadState;
import com.nm.products.constants.NavigationState;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.DaoProductDefinitionPart;
import com.nm.products.dtos.navigation.NavigationBody;
import com.nm.products.dtos.navigation.NavigationHeadItem;
import com.nm.products.dtos.navigation.NavigationNode;
import com.nm.products.dtos.navigation.NavigationPath;
import com.nm.products.dtos.navigation.NavigationStack;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionIngredient;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ProductNavigationHelper {
	private final DaoProductDefinition daoProduct;
	private final DaoProductDefinitionPart daoPart;
	private final ProductDefinitionViewConverter viewConverter;

	public ProductNavigationHelper(DaoProductDefinition daoProduct, DaoProductDefinitionPart daoPart,
			ProductDefinitionViewConverter viewConverter) {
		super();
		this.daoProduct = daoProduct;
		this.daoPart = daoPart;
		this.viewConverter = viewConverter;
	}

	public void unvisited(NavigationHeadItem item) {
		item.setState(NavigationHeadState.PreviousNotVisiteable);
	}

	public void visited(NavigationHeadItem item) {
		item.setState(NavigationHeadState.Previous);
	}

	public Collection<NavigationHeadItem> down(NavigationNode node) throws NoDataFoundException {
		Collection<NavigationHeadItem> items = new ArrayList<NavigationHeadItem>();
		if (node.getBody().getProduct() != null) {
			ProductDefinition product = daoProduct.get(node.getBody().getProduct().getId());
			if (!product.getParts().isEmpty()) {
				NavigationStack stack = node.getHead().createStack();
				for (ProductDefinitionPart part : product.getParts()) {
					NavigationHeadItem newitem = stack.createItem();
					newitem.setPart(viewConverter.toDto(part, new OptionsList()));
					newitem.setParent(node.getBody().getProduct());
					items.add(newitem);
				}
				if (!stack.getItems().isEmpty()) {
					stack.getItems().iterator().next().setState(NavigationHeadState.Current);
				}
			}
		}
		return items;
	}

	public boolean hasParts(NavigationBody body) throws NoDataFoundException {
		if (body.getProduct() != null) {
			ProductDefinition product = daoProduct.get(body.getProduct().getId());
			return !product.getParts().isEmpty();
		}
		return false;
	}

	public boolean hasNotParts(NavigationBody body) throws NoDataFoundException {
		return !hasParts(body);
	}

	public boolean hasProducts(NavigationHeadItem item) throws NoDataFoundException {
		if (item.getPart() != null) {
			ProductDefinitionPart product = daoPart.get(item.getPart().getId());
			return !product.getProducts().isEmpty();
		}
		return false;
	}

	public boolean hasNotProducts(NavigationHeadItem item) throws NoDataFoundException {
		return !hasProducts(item);
	}

	public boolean hasIngredients(NavigationBody body) throws NoDataFoundException {
		if (body.getProduct() != null) {
			ProductDefinition product = daoProduct.get(body.getProduct().getId());
			for (ProductDefinitionIngredient ing : product.getIngredients()) {
				if (!ing.getMandatory()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasNotIngredients(NavigationBody body) throws NoDataFoundException {
		return !hasIngredients(body);
	}

	public void clear(NavigationBody body) {
		body.clearAll();
	}

	public NavigationHeadItem next(NavigationNode node) {
		NavigationPath path = node.find(NavigationHeadState.Current);
		if (path.founded()) {
			NavigationHeadItem current = path.getStack().getItems().get(path.getIndexItem() + 1);
			current.setState(NavigationHeadState.Current);
			return current;
		}
		return null;
	}

	public void commit(NavigationBody node) {
		node.setState(NavigationBodyState.Commit);
	}

	public void commit(NavigationNode node) {
		node.setState(NavigationState.Commit);
	}

	public boolean isFinished(NavigationNode node) {
		return node.getHead().getStacks().isEmpty();
	}

	public NavigationHeadItem up(NavigationNode node) {
		NavigationHeadItem founded = null;
		while (founded == null && !node.getHead().getStacks().isEmpty()) {
			node.getHead().getStacks().removeLast();
			if (!node.getHead().getStacks().isEmpty()) {
				NavigationStack lastStack = node.getHead().getStacks().getLast();
				int i = 0;
				while (founded == null && i < lastStack.getItems().size()) {
					if (lastStack.getItems().get(i).getState().equals(NavigationHeadState.Unknwon)) {
						founded = lastStack.getItems().get(i);
					}
					i++;
				}
			}
		}
		if (founded != null) {
			founded.setState(NavigationHeadState.Current);
		}
		return founded;
	}

	public boolean canUp(NavigationNode node) {
		NavigationPath path = node.find(NavigationHeadState.Current);
		if (path.founded()) {
			return path.getIndexStack() > 0;
		}
		return false;
	}

	public boolean hasNotNext(NavigationNode node) {
		return !this.hasNext(node);
	}

	public boolean hasNext(NavigationNode node) {
		NavigationPath path = node.find(NavigationHeadState.Current);
		if (path.founded()) {
			if ((path.getIndexItem() + 1) < path.getStack().getItems().size()) {
				return true;
			}
		}
		return false;
	}

	public void loadProducts(NavigationHeadItem item, NavigationBody body) throws NoDataFoundException {
		ProductDefinitionPart part = daoPart.get(item.getPart().getId());
		body.getProducts().clear();
		for (ProductDefinition product : part.getProducts()) {
			body.getProducts().add(viewConverter.toDto(product, new OptionsList()));
		}
		if (item.getPart() != null) {
			body.setRequired(item.getPart().isMandatory());
		}
	}

	public void loadIngredients(NavigationBody body) throws NoDataFoundException {
		ProductDefinition product = daoProduct.get(body.getProduct().getId());
		body.getIngredients().clear();
		for (ProductDefinitionIngredient ing : product.getIngredients()) {
			if (!ing.getMandatory()) {
				body.getIngredients().add(viewConverter.toDto(ing, new OptionsList()));
			}
		}
	}
}
