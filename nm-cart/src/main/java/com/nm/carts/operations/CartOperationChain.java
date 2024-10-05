package com.nm.carts.operations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nm.carts.exceptions.CartManagementException;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public abstract class CartOperationChain {

	Log log = LogFactory.getLog(getClass());
	private CartOperationChain next;

	public abstract CartOperationChainContext onBefore(CartOperationChainContext context)
			throws CartManagementException;

	public abstract CartOperationChainContext onExecute(CartOperationChainContext context)
			throws CartManagementException;

	public abstract CartOperationChainContext onAfter(CartOperationChainContext context) throws CartManagementException;

	static List<CartOperationChain> all = new ArrayList<CartOperationChain>();

	static {
		all.add(new CartOperationChainState());
		all.add(new CartOperationChainConvert());
		all.add(new CartOperationChainSorted());
		all.add(new CartOperationChainUnique());
		all.add(new CartOperationChainDuplicate());
		all.add(new CartOperationChainFinally());
	}

	public void setNext(CartOperationChain next) {
		this.next = next;
	}

	public boolean hasNext() {
		return next != null;
	}

	public CartOperationChainContext before(CartOperationChainContext context) throws CartManagementException {
		if (hasNext()) {
			return next.onBefore(context);
		} else {
			return context;
		}
	}

	public CartOperationChainContext execute(CartOperationChainContext context) throws CartManagementException {
		if (hasNext()) {
			return next.onExecute(context);
		} else {
			return context;
		}
	}

	public CartOperationChainContext doAll(CartOperationChainContext context) throws CartManagementException {
		this.onBefore(context);
		this.onExecute(context);
		return this.onAfter(context);
	}

	public CartOperationChainContext after(CartOperationChainContext context) throws CartManagementException {
		if (hasNext()) {
			return next.onAfter(context);
		} else {
			return context;
		}
	}

	public static void process(CartOperationChainContext context) throws CartManagementException {
		for (int i = 0; i < (all.size() - 1); i++) {
			all.get(i).setNext(all.get(i + 1));
		}
		all.get(all.size() - 1).setNext(null);
		//
		all.get(0).onBefore(context);
		all.get(0).execute(context);
		all.get(0).onAfter(context);
	}

}
