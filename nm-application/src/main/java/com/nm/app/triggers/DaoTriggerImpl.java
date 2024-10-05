package com.nm.app.triggers;

import java.util.Date;
import java.util.List;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoTriggerImpl extends AbstractGenericDao<Trigger, Long>implements DaoTrigger {

	@Override
	protected Class<Trigger> getClassName() {
		return Trigger.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public Date leastScheduleDate() {
		QueryBuilderTrigger query = QueryBuilderTrigger.get().withScheduleAtGtLastExecution().withScheduleAtMin();
		@SuppressWarnings("unchecked")
		List<Date> dates = (List<Date>) getHibernateTemplate().findByCriteria(query.getQuery());
		return dates.iterator().next();
	}

}
