package com.nm.carts.operations;

import java.util.HashMap;
import java.util.Map;

import com.nm.carts.contract.CartAdapter;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemCompatibleDto;
import com.nm.carts.dtos.CartItemDto;
import com.nm.carts.exceptions.CartManagementException;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class CartOperationChainConvert extends CartOperationChain {

	@Override
	public CartOperationChainContext onAfter(CartOperationChainContext context) throws CartManagementException {
		return after(context);
	}

	@Override
	public CartOperationChainContext onBefore(CartOperationChainContext context) throws CartManagementException {
		switch (context.getRequest().getOperation()) {
		case Push: {
			context.setItem(buildItem(context.getCart(), context.getAdapter(), context.getToPush()));
			break;
		}
		default:
			break;
		}
		return before(context);
	}

	protected CartItemDto buildItem(CartDto dto, final CartAdapter adapter, CartItemCompatibleDto item) {
		final Map<String, CartItemDto> dtoByUuid = new HashMap<String, CartItemDto>();
		//
		final CartItemDto root = dto.createItem(true);
		GraphIteratorBuilder.buildDeep().iterate(item, new IteratorListenerGraphInfo() {

			public void onFounded(GraphInfo node) {
				CartItemCompatibleDto compatible = ((CartItemCompatibleDto) node.getCurrent());
				if (node.getCurrent().root()) {
					dtoByUuid.put(node.getCurrent().uuid(), root);
					compatible.toItem(root);
				} else {
					String uuid = node.getParent().getCurrent().uuid();
					if (dtoByUuid.containsKey(uuid)) {
						CartItemDto parent = dtoByUuid.get(uuid);
						CartItemDto child = parent.createChild(false);
						compatible.toItem(child);
						dtoByUuid.put(node.getCurrent().uuid(), child);
					} else {
						throw new IllegalStateException(
								"Could not found parent cartrow" + node.getParent().getCurrent());
					}
				}
			}
		});
		return root;
	}

	@Override
	public CartOperationChainContext onExecute(CartOperationChainContext context) throws CartManagementException {

		return execute(context);
	}
}
