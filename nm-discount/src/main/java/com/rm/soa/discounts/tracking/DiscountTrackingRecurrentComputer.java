package com.rm.soa.discounts.tracking;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountTrackingRecurrentComputer {
	public static final String DiscountTrackingRecurrentComputer = "DiscountTrackingLifeCycleComputer";
	public static final String DiscountTrackingRecurrentComputerPeriod = "DiscountTrackingRecurrentComputerPeriod";

	public void findRestart(DiscountTrackingLifeCycleContext context);
}
