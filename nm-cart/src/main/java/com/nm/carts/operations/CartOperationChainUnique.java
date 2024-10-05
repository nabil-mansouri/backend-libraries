package com.nm.carts.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.nm.carts.contract.CartItemOptions;
import com.nm.carts.contract.CartState;
import com.nm.carts.contract.CartStateDefault;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.dtos.impl.CartRowIdentifier;
import com.nm.carts.exceptions.CartManagementException;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.GraphFinder;
import com.nm.utils.graphs.finder.GraphFinderPath;
import com.nm.utils.graphs.finder.GraphManager;
import com.nm.utils.graphs.finder.IGraphIdentifier;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class CartOperationChainUnique extends CartOperationChain {

	@Override
	public CartOperationChainContext onAfter(CartOperationChainContext context) throws CartManagementException {
		if (context.getCart().getOptions().contains(CartItemOptions.Unique)) {
			switch (context.getRequest().getOperation()) {
			case Replace:
			case Multiply:
			case Substract: {
				GraphFinder finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
				GraphFinderPath<IGraph> path = finder.findFirstUUID(context.getCart(), context.getRequest().getUuid());
				if (path.founded()) {
					CartItemDto row = path.getFounded(CartItemDto.class);
					if (row.getQuantity() <= 0) {
						GraphManager manager = new GraphManager(GraphIteratorBuilder.buildDeep());
						manager.removeAllUUID(context.getCart(), row.uuid());
					}
				}
				break;
			}
			default:
				break;
			}
		}
		return after(context);
	}

	protected List<CartState> good() {
		return Arrays.asList((CartState) CartStateDefault.NonEmpty, (CartState) CartStateDefault.Ready);
	}

	@Override
	public CartOperationChainContext onBefore(CartOperationChainContext context) throws CartManagementException {
		if (context.getCart().getOptions().contains(CartItemOptions.Unique)) {
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

	protected CartItemDto getRow(CartOperationChainContext context) throws CartManagementException {
		GraphFinder finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
		GraphFinderPath<IGraph> path = finder.findFirstUUID(context.getCart(), context.getRequest().getUuid());
		if (path.founded()) {
			CartItemDto row = path.getFounded(CartItemDto.class);
			return row;
		} else {
			throw new CartManagementException("Could not execute operation because item not found");
		}
	}

	@Override
	public CartOperationChainContext onExecute(CartOperationChainContext context) throws CartManagementException {
		if (context.getCart().getOptions().contains(CartItemOptions.Unique)) {
			switch (context.getRequest().getOperation()) {
			case Add: {
				CartItemDto row = getRow(context);
				row.setQuantity(row.getQuantity() + context.getRequest().getNumber().intValue());
				break;
			}
			case Clear: {
				context.getCart().childrens().clear();
				break;
			}
			case Multiply: {
				CartItemDto row = getRow(context);
				row.setQuantity(row.getQuantity() * context.getRequest().getNumber().intValue());
				break;
			}
			case Push: {
				CartItemDto root = context.getItem();
				GraphFinder finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
				Collection<IGraphIdentifier> ids = new ArrayList<IGraphIdentifier>();
				ids.add(new CartRowIdentifier(null, root));
				GraphFinderPath<IGraph> path = finder.findLast(context.getCart(), ids);
				if (path.founded()) {
					CartItemDto row = path.getFounded(CartItemDto.class);
					row.setQuantity(row.getQuantity() + 1);
				} else {
					context.getItem().setQuantity(1);
					CartOperationChainHelper.push(context);
				}
				break;
			}
			case Replace: {
				CartItemDto row = getRow(context);
				row.setQuantity(context.getRequest().getNumber().intValue());
				break;
			}
			case Substract: {
				CartItemDto row = getRow(context);
				int qyty = row.getQuantity() - context.getRequest().getNumber().intValue();
				row.setQuantity(qyty);
				break;
			}
			default:
				break;
			}
		}
		return execute(context);
	}
}
