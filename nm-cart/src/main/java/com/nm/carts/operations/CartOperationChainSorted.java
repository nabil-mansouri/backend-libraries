package com.nm.carts.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.nm.carts.constants.CartRowTypeDefault;
import com.nm.carts.contract.CartItemOptions;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.dtos.impl.CartRowIdentifier;
import com.nm.carts.exceptions.CartManagementException;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.GraphFinder;
import com.nm.utils.graphs.finder.GraphFinderPath;
import com.nm.utils.graphs.finder.IGraphIdentifier;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class CartOperationChainSorted extends CartOperationChain {

	protected static class HistoryPath {
		GraphFinderPath<IGraph> path;
		CartItemDto item;

		public HistoryPath(GraphFinderPath<IGraph> path, CartItemDto item) {
			super();
			this.path = path;
			this.item = item;
		}

	}

	@Override
	public CartOperationChainContext onAfter(CartOperationChainContext context) throws CartManagementException {
		return after(context);
	}

	@Override
	public CartOperationChainContext onBefore(CartOperationChainContext context) throws CartManagementException {
		if (context.getCart().getOptions().contains(CartItemOptions.Sort)) {
			CartDto cart = context.getCart();
			// CREATE GROUPS
			LinkedList<CartItemDto> groups = context.getAdapter().toGroups(context.getItem());
			if (!groups.isEmpty()) {
				// Keep only unknwon with last knwon
				GraphFinder finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
				Collection<IGraphIdentifier> ids = new ArrayList<IGraphIdentifier>();
				LinkedList<HistoryPath> unknwon = new LinkedList<HistoryPath>();
				for (CartItemDto c : groups) {
					c.setType(CartRowTypeDefault.Group);
					ids.clear();
					ids.add(new CartRowIdentifier(CartRowTypeDefault.Group, c));
					GraphFinderPath<IGraph> path = finder.findFirst(cart, ids);
					if (path.founded()) {
						unknwon.clear();
					}
					unknwon.addLast(new HistoryPath(path, c));
				}
				// Add all unknown to hierarchy
				CartItemDto prev = null;
				while (!unknwon.isEmpty()) {
					HistoryPath current = unknwon.removeFirst();
					if (current.path.founded()) {
						prev = (CartItemDto) current.path.getFounded();
					} else {
						if (prev == null) {
							prev = cart.createChild(current.item);
						} else {
							prev = prev.createChild(current.item);
						}
					}
				}
				// Add item
				if (prev != null) {
					context.getExtra().put(CartOperationChainContext.PARENT, prev);
				}
			}
		}
		return before(context);
	}

	@Override
	public CartOperationChainContext onExecute(CartOperationChainContext context) throws CartManagementException {
		return execute(context);
	}
}
