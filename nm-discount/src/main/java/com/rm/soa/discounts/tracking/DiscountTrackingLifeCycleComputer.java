package com.rm.soa.discounts.tracking;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountTrackingLifeCycleComputer {
	public static final String DiscountLifeTrackingCycleComputer = "DiscountTrackingLifeCycleComputer";
	public static final String DiscountLifeTrackingCycleComputerDuration = "DiscountTrackingLifeCycleComputer.DiscountLifeCycleComputerDuration";
	public static final String DiscountLifeTrackingCycleComputerCount = "DiscountTrackingLifeCycleComputer.DiscountLifeCycleComputerCount";

	public void findActive(DiscountTrackingLifeCycleContext context);

	public void findInactive(DiscountTrackingLifeCycleContext context);
}
