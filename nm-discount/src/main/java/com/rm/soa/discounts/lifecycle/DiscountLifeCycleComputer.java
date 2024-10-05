package com.rm.soa.discounts.lifecycle;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountLifeCycleComputer {
	public static final String DiscountLifeCycleComputer = "DiscountLifeCycleComputer";
	public static final String DiscountLifeCycleComputerDate = "DiscountLifeCycleComputer.DiscountLifeCycleComputerDate";
	public static final String DiscountLifeCycleComputerDuration = "DiscountLifeCycleComputer.DiscountLifeCycleComputerDuration";
	public static final String DiscountLifeCycleComputerCount = "DiscountLifeCycleComputer.DiscountLifeCycleComputerCount";

	public void findAvailable(DiscountLifeCycleContext context);

	public void findUnavailable(DiscountLifeCycleContext context);
}
