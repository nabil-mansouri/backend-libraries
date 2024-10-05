package com.nm.carts.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.nm.carts.contract.CartItemOptions;
import com.nm.carts.contract.CartState;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.dtos.impl.CartRowIdentifier;
import com.nm.carts.exceptions.CartManagementException;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.finder.GraphFinderPath;
import com.nm.utils.graphs.finder.GraphFinderPathList;
import com.nm.utils.graphs.finder.GraphInfoFinder;
import com.nm.utils.graphs.finder.GraphManager;
import com.nm.utils.graphs.finder.IGraphIdentifier;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class CartOperationChainDuplicate extends CartOperationChain {

	@Override
	public CartOperationChainContext onAfter(CartOperationChainContext context) throws CartManagementException {
		return after(context);
	}

	protected List<CartState> good() {
		return Arrays.asList((CartState) CartStateDefault.NonEmpty, (CartState) CartStateDefault.Ready);
	}

	@Override
	public CartOperationChainContext onBefore(CartOperationChainContext context) throws CartManagementException {
		if (context.getCart().getOptions().contains(CartItemOptions.Duplicate)) {
			switch (context.getRequest().getOperation()) {
			case Add:
			case Multiply:
			case Push:
			case Replace:
			case Substract:
				if (Collections.disjoint(good(), Arrays.asList(context.getCart().getState()))) {
					throw new CartManagementException("Could not execute operation because of locked");
				}
			default:
				break;
			}
		}
		return before(context);
	}

	protected GraphInfo getRowById(CartOperationChainContext context) throws CartManagementException {
		GraphInfoFinder finder = new GraphInfoFinder(GraphIteratorBuilder.buildDeep());
		GraphFinderPath<GraphInfo> path = finder.findFirstUUID(context.getCart(), context.getRequest().getUuid());
		if (path.founded()) {
			GraphInfo row = path.getFounded();
			return row;
		} else {
			throw new CartManagementException("Could not execute operation because item not found");
		}
	}

	protected Collection<GraphInfo> getRowsById(CartOperationChainContext context) throws CartManagementException {
		GraphInfo model = getRowById(context);
		GraphInfoFinder finder = new GraphInfoFinder(GraphIteratorBuilder.buildDeep());
		Collection<IGraphIdentifier> ids = new ArrayList<IGraphIdentifier>();
		ids.add(new CartRowIdentifier(null, (CartItemDto) model.getCurrent()));
		GraphFinderPathList<GraphInfo> path = finder.findAll(context.getCart(), ids);
		if (path.founded()) {
			return path.result();
		} else {
			throw new CartManagementException("Could not execute operation because item not found");
		}
	}

	protected int add(CartOperationChainContext context, Collection<GraphInfo> all, int toAdd)
			throws CartManagementException {
		GraphInfo first = all.iterator().next();
		for (int i = 0; i < toAdd; i++) {
			CartItemDto clone = first.getCurrent(CartItemDto.class).clone();
			CartOperationChainHelper.push(context, first, clone);
		}
		return toAdd;
	}

	protected int del(CartOperationChainContext context, Collection<GraphInfo> all, int toDel) {
		GraphManager manager = new GraphManager(GraphIteratorBuilder.buildDeep());
		Iterator<GraphInfo> it = all.iterator();
		for (int i = 0; i < toDel && it.hasNext(); i++) {
			manager.remove(it.next());
		}
		return toDel;
	}

	@Override
	public CartOperationChainContext onExecute(CartOperationChainContext context) throws CartManagementException {
		if (context.getCart().getOptions().contains(CartItemOptions.Duplicate)) {
			switch (context.getRequest().getOperation()) {
			case Add: {
				Collection<GraphInfo> all = getRowsById(context);
				int toAdd = context.getRequest().getNumber().intValue();
				add(context, all, toAdd);
				break;
			}
			case Clear: {
				context.getCart().childrens().clear();
				break;
			}
			case Multiply: {
				Collection<GraphInfo> all = getRowsById(context);
				int qty = all.size() * context.getRequest().getNumber().intValue();
				if (qty > all.size()) {
					int toAdd = qty - all.size();
					add(context, all, toAdd);
				} else if (qty < all.size()) {
					int toDel = all.size() - qty;
					del(context, all, toDel);
				}
				break;
			}
			case Push: {
				CartItemDto row = context.getItem();
				row.setQuantity(1);
				CartOperationChainHelper.push(context);
				break;
			}
			case Replace: {
				Collection<GraphInfo> all = getRowsById(context);
				int qty = context.getRequest().getNumber().intValue();
				int toDel = Math.max(0, (all.size() - qty));
				int toAdd = Math.max(0, (qty - all.size()));
				add(context, all, toAdd);
				del(context, all, toDel);
				break;
			}
			case Substract: {
				Collection<GraphInfo> all = getRowsById(context);
				int toDel = context.getRequest().getNumber().intValue();
				del(context, all, toDel);
				break;
			}
			default:
				break;
			}
		}
		return execute(context);
	}
}
