package com.rm.dao.clients.criterias;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.rm.model.clients.criterias.CriteriaRuleRange;
import com.rm.utils.dao.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CriteriaRuleRangeQueryBuilder extends AbstractQueryBuilder<CriteriaRuleRangeQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetachedCriteria criteria;

	public static CriteriaRuleRangeQueryBuilder get() {
		return new CriteriaRuleRangeQueryBuilder(CriteriaRuleRange.class);
	}

	private CriteriaRuleRangeQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	public CriteriaRuleRangeQueryBuilder withIds(Collection<Long> id) {
		if (!id.isEmpty()) {
			this.criteria.add(Restrictions.in("id", id));
		}
		return this;
	}

	public CriteriaRuleRangeQueryBuilder withCriteria(Long id) {
		createAlias(wrapMain("criterias"), "criterias");
		this.criteria.add(Restrictions.eq("criterias.id", id));
		return this;
	}

	public CriteriaRuleRangeQueryBuilder withProjectionId() {
		this.criteria.setProjection(Projections.id());
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
