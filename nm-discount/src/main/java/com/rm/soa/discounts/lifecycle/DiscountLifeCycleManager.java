package com.rm.soa.discounts.lifecycle;

import com.rm.soa.discounts.tracking.DiscountTrackingLifeCycleContext;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountLifeCycleManager {
	public void checkForStart();

	public void checkForStop();

	public void checkForRestart();

	public void checkForRestart(DiscountTrackingLifeCycleContext context);
}
