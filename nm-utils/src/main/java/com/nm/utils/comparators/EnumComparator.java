package com.nm.utils.comparators;

import java.util.Comparator;

/**
 * 
 * @author Nabil
 * 
 */
public class EnumComparator implements Comparator<Enum<?>> {

	public static final Comparator<Enum<?>> INSTANCE = new EnumComparator();

	public int compare(Enum<?> enum1, Enum<?> enum2) {
		return enum1.name().compareTo(enum2.name());
	}

}
