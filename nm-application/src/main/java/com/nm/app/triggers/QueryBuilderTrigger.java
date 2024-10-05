package com.nm.app.triggers;

import java.util.Date;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryBuilderTrigger extends AbstractQueryBuilder<QueryBuilderTrigger> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryBuilderTrigger(Class<? extends Trigger> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static QueryBuilderTrigger get() {
		return new QueryBuilderTrigger(Trigger.class);
	}

	public static QueryBuilderTrigger getEvent() {
		return new QueryBuilderTrigger(TriggerEvent.class);
	}

	public static QueryBuilderTrigger getCron() {
		return new QueryBuilderTrigger(TriggerCron.class);
	}

	public QueryBuilderTrigger withId(Long id) {
		this.criteria.add(Restrictions.idEq(id));
		return this;
	}

	public QueryBuilderTrigger withScheduleAtLe(Date schedule) {
		this.criteria.add(Restrictions.le("scheduledAt", schedule));
		return this;
	}

	public QueryBuilderTrigger withScheduleAtGtLastExecution() {
		Disjunction or = Restrictions.or();
		or.add(Restrictions.sqlRestriction("{alias}.scheduledAt > {alias}.lastExecution"));
		//
		{
			Conjunction and = Restrictions.and();
			and.add(Restrictions.isNotNull("scheduledAt"));
			and.add(Restrictions.isNull("lastExecution"));
			or.add(and);
		}
		this.criteria.add(or);
		return this;
	}

	public QueryBuilderTrigger withEvent(TriggerEventEnum event) {
		this.criteria.add(Restrictions.eq("event", event));
		return this;
	}

	public QueryBuilderTrigger withScheduleAtMin() {
		this.criteria.setProjection(Projections.min("scheduledAt"));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
