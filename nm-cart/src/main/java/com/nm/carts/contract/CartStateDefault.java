package com.nm.carts.contract;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author nabilmansouri
 *
 */
public enum CartStateDefault implements CartState {
	/** Initial state */
	NotReady, //
	/** Configured and ready */
	Ready, //
	/** Contains items */
	NonEmpty, //
	/** Validating content */
	Validating, //
	/** Validate successfully */
	Validated, //
	/** Validate successfully */
	Complete;

	public static Set<CartState> all() {
		return new HashSet<CartState>(EnumSet.allOf(CartStateDefault.class));
	}

}