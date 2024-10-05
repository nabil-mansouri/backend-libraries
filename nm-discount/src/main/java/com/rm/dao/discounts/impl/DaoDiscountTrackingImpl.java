package com.rm.dao.discounts.impl;

import org.springframework.stereotype.Repository;

import com.rm.dao.discounts.DaoDiscountTracking;
import com.rm.model.discounts.tracking.DiscountTracking;
import com.rm.utils.dao.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoDiscountTrackingImpl extends AbstractGenericDao<DiscountTracking, Long> implements DaoDiscountTracking {

	@Override
	protected Class<DiscountTracking> getClassName() {
		return DiscountTracking.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
