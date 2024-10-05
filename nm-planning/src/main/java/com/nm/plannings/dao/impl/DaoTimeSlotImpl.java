package com.nm.plannings.dao.impl;

import java.util.Collection;

import com.nm.plannings.dao.DaoTimeSlot;
import com.nm.plannings.model.Planning;
import com.nm.plannings.model.TimeSlot;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoTimeSlotImpl extends AbstractGenericDao<TimeSlot, Long>implements DaoTimeSlot {

	@Override
	protected Class<TimeSlot> getClassName() {
		return TimeSlot.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	@Override
	public void delete(TimeSlot bean) {
		Planning p = bean.getPlanning();
		p.getSlots().remove(bean);
		getHibernateTemplate().saveOrUpdate(p);
		super.delete(bean);
	}

	@Override
	public void deleteAll(Collection<TimeSlot> beans) {
		for (TimeSlot t : beans) {
			this.delete(t);
		}
	}
}
