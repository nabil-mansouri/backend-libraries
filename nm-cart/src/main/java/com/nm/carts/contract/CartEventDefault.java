package com.nm.carts.contract;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author nabilmansouri
 *
 */
public enum CartEventDefault implements CartEvent {
	NotReadyEvent, ReadyEvent, UnlockEvent, OperationEvent, EmptyEvent, ValidatingEvent;

	public static Set<CartState> all() {
		return new HashSet<CartState>(EnumSet.allOf(CartStateDefault.class));
	}

}