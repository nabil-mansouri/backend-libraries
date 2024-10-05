package com.nm.plannings.dao.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.SlotType;
import com.nm.plannings.dtos.DtoSlotFilter;
import com.nm.plannings.model.Planning;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotExceptionnal;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class TimeSlotQueryBuilder extends AbstractQueryBuilder<TimeSlotQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final DetachedCriteria criteria;

	public TimeSlotQueryBuilder(Class<? extends TimeSlot> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public static TimeSlotQueryBuilder get() {
		return new TimeSlotQueryBuilder(TimeSlot.class);
	}

	public static TimeSlotQueryBuilder getRecurrent() {
		return new TimeSlotQueryBuilder(TimeSlotRecurrent.class);
	}

	public static TimeSlotQueryBuilder getExceptional() {
		return new TimeSlotQueryBuilder(TimeSlotExceptionnal.class);
	}

	public TimeSlotQueryBuilder withAboutIdProjection() {
		createAlias("planning", "planning");
		createAlias("planning.about", "about");
		this.criteria.setProjection(Projections.property("about.id"));
		return this;
	}

	public TimeSlotQueryBuilder withAbout(BigInteger query) {
		createAlias("planning", "planning");
		createAlias("planning.about", "about");
		this.criteria.add(Restrictions.eq("about.id", query));
		return this;
	}

	public TimeSlotQueryBuilder withAbout(AbstractQueryBuilder<?> query) {
		createAlias("planning", "planning");
		createAlias("planning.about", "about");
		this.criteria.add(Subqueries.propertyIn("about.id", query.withNodeIdProjection().getQuery()));
		return this;
	}

	public TimeSlotQueryBuilder withEndBefore(Date end) {
		criteria.add(Restrictions.le("endPlan", end));
		return this;
	}

	public TimeSlotQueryBuilder withIntersectInterval(Date start, Date end) {
		criteria.add(Restrictions.and(Restrictions.le("beginPlan", end), Restrictions.ge("endPlan", start)));
		return this;
	}

	public TimeSlotQueryBuilder withIntersectHoraireOrExceptional(Date start, Date end) {
		Disjunction or = Restrictions.or();
		or.add(Restrictions.and(Restrictions.le("beginHoraire", end), Restrictions.ge("endHoraire", start)));
		or.add(Restrictions.eq("class", TimeSlotExceptionnal.class));
		criteria.add(or);
		return this;
	}

	public TimeSlotQueryBuilder withPlanning(Planning planning) {
		criteria.add(Restrictions.eq("planning", planning));
		return this;
	}

	public TimeSlotQueryBuilder withPlanning(Long planning) {
		createAlias("planning", "planning");
		criteria.add(Restrictions.eq("planning.id", planning));
		return this;
	}

	public TimeSlotQueryBuilder withStartAfter(Date start) {
		criteria.add(Restrictions.ge("beginPlan", start));
		return this;
	}

	public TimeSlotQueryBuilder withStartBefore(Date start) {
		criteria.add(Restrictions.le("beginPlan", start));
		return this;
	}

	public TimeSlotQueryBuilder withHoraireStartAfterOrExceptional(Date start) {
		Disjunction or = Restrictions.or();
		or.add(Restrictions.ge("beginHoraire", start));
		or.add(Restrictions.eq("class", TimeSlotExceptionnal.class));
		criteria.add(or);
		return this;
	}

	public TimeSlotQueryBuilder withHoraireStartBeforeOrExceptional(Date start) {
		Disjunction or = Restrictions.or();
		or.add(Restrictions.le("beginHoraire", start));
		or.add(Restrictions.eq("class", TimeSlotExceptionnal.class));
		criteria.add(or);
		return this;
	}

	public TimeSlotQueryBuilder withFilter(DtoSlotFilter filter) {
		if (filter.getFrom() != null && filter.getTo() != null) {
			withIntersectInterval(filter.getFrom(), filter.getTo());
			withIntersectHoraireOrExceptional(filter.getFrom(), filter.getTo());
		} else if (filter.getFrom() != null) {
			withStartAfter(filter.getFrom());
			withHoraireStartAfterOrExceptional(filter.getFrom());
		} else if (filter.getTo() != null) {
			withStartBefore(filter.getTo());
			withHoraireStartBeforeOrExceptional(filter.getTo());
		}
		// EXISTS
		if (filter.getExistsFrom() != null && filter.getExistsTo() != null) {
			withIntersectInterval(filter.getExistsFrom(), filter.getExistsTo());
		} else if (filter.getExistsFrom() != null) {
			withStartAfter(filter.getExistsFrom());
		} else if (filter.getExistsTo() != null) {
			withStartBefore(filter.getExistsTo());
		}
		// OTHER
		if (filter.getIds() != null) {
			withIds(filter.getIds());
		}
		return this;
	}

	public TimeSlotQueryBuilder withIds(Collection<Number> id) {
		this.getQuery().add(Restrictions.in("id", id.stream().map(u -> u.longValue()).collect(Collectors.toList())));
		return getThis();
	}

	public TimeSlotQueryBuilder withType(SlotRepeatKind type) {
		switch (type) {
		case Recurrent:
			criteria.add(Restrictions.eq("class", TimeSlotRecurrent.class));
			break;
		case Exceptionnal:
			criteria.add(Restrictions.eq("class", TimeSlotExceptionnal.class));
			break;
		}
		return this;
	}

	public TimeSlotQueryBuilder withType(SlotType type) {
		criteria.add(Restrictions.eq("type", type));
		return this;
	}

}
