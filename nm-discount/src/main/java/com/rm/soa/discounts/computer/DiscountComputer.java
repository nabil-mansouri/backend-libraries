package com.rm.soa.discounts.computer;

import com.rm.model.discounts.DiscountDefinition;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountComputer {
	public static final String DiscountComputer = "DiscountComputer";
	public static final String DiscountComputerFree = "DiscountComputer.DiscountComputerFree";
	public static final String DiscountComputerGift = "DiscountComputer.DiscountComputerGift";
	public static final String DiscountComputerOperation = "DiscountComputer.DiscountComputerOperation";
	public static final String DiscountComputerSpecial = "DiscountComputer.DiscountComputerSpecial";
	public static final String DiscountComputerReplacePrice = "DiscountComputer.DiscountComputerReplacePrice";

	public void compute(DiscountComputerContext context, CartBean cart, DiscountDefinition discount);
}
