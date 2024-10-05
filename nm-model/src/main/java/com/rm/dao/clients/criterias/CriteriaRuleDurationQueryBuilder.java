package com.rm.dao.clients.criterias;

import java.util.Collection;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.rm.dao.clients.views.ClientAgeActionQueryBuilder;
import com.rm.dao.clients.views.ClientAgeOrderQueryBuilder;
import com.rm.model.clients.criterias.CriteriaRuleDuration;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CriteriaRuleDurationQueryBuilder extends AbstractQueryBuilder<CriteriaRuleDurationQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria;

	public static CriteriaRuleDurationQueryBuilder get() {
		return new CriteriaRuleDurationQueryBuilder(CriteriaRuleDuration.class);
	}

	private CriteriaRuleDurationQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	public CriteriaRuleDurationQueryBuilder withIds(Collection<Long> id) {
		if (!id.isEmpty()) {
			this.criteria.add(Restrictions.in("id", id));
		}
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withCriteria(Long id) {
		createAlias(wrapMain("criterias"), "criterias");
		this.criteria.add(Restrictions.eq("criterias.id", id));
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withDisjunctionPeriodToJoin(ClientAgeOrderQueryBuilder query) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.isNull(wrapMain("fromSeconds")));
		conjunction.add(Property.forName(wrapMain("toSeconds")).geProperty(query.getDurationName()));
		this.disjunction.add(conjunction);
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withDisjunctionPeriodFromJoin(ClientAgeOrderQueryBuilder query) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Property.forName(wrapMain("fromSeconds")).leProperty(query.getDurationName()));
		conjunction.add(Restrictions.isNull(wrapMain("toSeconds")));
		this.disjunction.add(conjunction);
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withDisjunctionPeriodRangeJoin(ClientAgeOrderQueryBuilder query) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Property.forName(wrapMain("fromSeconds")).leProperty(query.getDurationName()));
		conjunction.add(Property.forName(wrapMain("toSeconds")).geProperty(query.getDurationName()));
		this.disjunction.add(conjunction);
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withDisjunctionPeriodToJoin(ClientAgeActionQueryBuilder query) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.isNull(wrapMain("fromSeconds")));
		conjunction.add(Property.forName(wrapMain("toSeconds")).geProperty(query.getDurationName()));
		this.disjunction.add(conjunction);
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withDisjunctionPeriodFromJoin(ClientAgeActionQueryBuilder query) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Property.forName(wrapMain("fromSeconds")).leProperty(query.getDurationName()));
		conjunction.add(Restrictions.isNull(wrapMain("toSeconds")));
		this.disjunction.add(conjunction);
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withDisjunctionPeriodRangeJoin(ClientAgeActionQueryBuilder query) {
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Property.forName(wrapMain("fromSeconds")).leProperty(query.getDurationName()));
		conjunction.add(Property.forName(wrapMain("toSeconds")).geProperty(query.getDurationName()));
		this.disjunction.add(conjunction);
		return this;
	}

	public CriteriaRuleDurationQueryBuilder withProjectionId() {
		this.criteria.setProjection(Projections.id());
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
