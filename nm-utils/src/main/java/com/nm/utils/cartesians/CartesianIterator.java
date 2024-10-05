package com.nm.utils.cartesians;

import java.util.List;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CartesianIterator {
	private CartesianGenerator gen = new CartesianGenerator();

	public <T> void iterate(CartesianArguments<T> args, CartesianIteratorListener<T> listener) {
		CartesianResults<T> all = gen.generate(args);
		for (List<T> a : all.getRows()) {
			listener.onRow(a);
		}
	}
}
