package com.rm.soa.discounts;

import java.util.Collection;

import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.tracking.DiscountTracking;
import com.rm.soa.discounts.lifecycle.beans.DiscountLifeCyclePlanBean;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaDiscount {

	public DiscountFormBean save(DiscountFormBean discount, String lang) throws NoDataFoundException;

	public DiscountFormBean edit(Long id, String lang) throws NoDataFoundException;

	public DiscountDefinition delete(Long id) throws NoDataFoundException;

	public Collection<DiscountFormBean> fetch(String lang) throws NoDataFoundException;

	public DiscountTracking use(Long client, Long discount) throws NoDataFoundException;

	public void start(DiscountDefinition discount);

	public void stop(DiscountDefinition discount);

	public void plan(Collection<DiscountLifeCyclePlanBean> plans);

	public void start(DiscountTracking tracking);

	public void stop(DiscountTracking tracking);
	
	public boolean available(DiscountTracking tracking);
}
