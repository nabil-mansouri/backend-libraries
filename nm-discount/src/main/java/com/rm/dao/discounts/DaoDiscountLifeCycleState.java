package com.rm.dao.discounts;

import java.util.Date;

import com.rm.model.discounts.lifecycle.DiscountLifeCycleState;
import com.rm.utils.dao.GenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoDiscountLifeCycleState extends GenericDao<DiscountLifeCycleState, Long> {

	void updateDate(Long id, Date date);

}
