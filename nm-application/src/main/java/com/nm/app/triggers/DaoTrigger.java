package com.nm.app.triggers;

import java.util.Date;

import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public interface DaoTrigger extends IGenericDao<Trigger, Long> {
	public Date leastScheduleDate();
}
