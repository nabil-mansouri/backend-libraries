package com.rm.dao.discounts;

import java.util.Date;

import com.rm.model.discounts.tracking.DiscountTrackingLifeCycleState;
import com.rm.utils.dao.GenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoDiscountTrackingLifeCycleState extends GenericDao<DiscountTrackingLifeCycleState, Long> {

	void updateDate(Long id, Date date);

}
